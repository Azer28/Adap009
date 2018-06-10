package stream.api.adapter.com.unibank;

import com.signature.RSASignature;
import stream.api.*;
import stream.api.Detail;
import stream.api.ValidationException;
import stream.api.adapter.com.unibank.mhm.BillMap;
import stream.api.adapter.com.unibank.mhm.Loan;
import stream.api.adapter.com.unibank.mhm.parser.MHMResponseMarshaller;
import stream.api.adapter.com.unibank.request.*;
import stream.api.adapter.com.unibank.mhm.exception.MHMResponseException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.bind.*;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by AzarM on 4/16/2018.
 */
public class ADP0009 implements Adapter {

    Configurator configurator;
    RSASignature signatureResolver = new RSASignature();
    @Override
    public void initialize(Configurator configurator) throws IOException {
        this.configurator = configurator;
    }

    @Override
    public void validate(Service service, List<Detail> list) throws IOException, ValidationException {
        List<String> requiredDetails = Arrays.asList("loanNo", "birthDate", "contractNo");

        for (String requiredDetail : requiredDetails) {
            long num  = list.stream().filter(detail -> detail.getName().equals(requiredDetail)).count();
            if (num == 0) {
                throw new ValidationException(String.format("Required detail %s is not sent", requiredDetail));
            }
        }

    }

    @Override
    public List<stream.api.Bill> getBillList(Service service, List<Detail> list) throws IOException, ValidationException {

        Map<String, String> detailsMap = new HashMap<>();
        for(stream.api.Detail detail : list) {
            detailsMap.put(detail.getName(), detail.getValue());
        }

        if (!detailsMap.containsKey("loanNo") || !detailsMap.containsKey("birthDate")) {
            throw new ValidationException("parameter loanNo or birthDate is empty");
        }

        String template = "<request point=\"%s\"><advanced service=\"%s\" function=\"contracts\"><attribute name=\"id1\" value=\"%s\"></attribute></advanced></request>";
        String serviceCode = configurator.getSetting("/adapter/serviceCode");
        String pointNumber = configurator.getSetting("/adapter/pointNumber");
        String message = String.format(template, pointNumber, serviceCode, detailsMap.get("loanNo"));
        String base64SignedData = signatureResolver.sign(message);
        System.out.println(message);
        System.out.println(base64SignedData);
        MHMResponse response = sendRequest(message, base64SignedData);
        try {
            response.validate();
        } catch (MHMResponseException e) {
            e.printStackTrace();
            throw new ValidationException(e.getMessage());
        }

        BillMap billMap = new BillMap();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Loan.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Loan loan = (Loan) unmarshaller.unmarshal(new StringReader(response.getTextResponse()));

            System.out.println(loan.getAccount());
            Map<String, String> outerMap =  loan.getOuterMap();//loan.result.loanData.detailsMap;
            Map<String, String> innerMap = loan.getInnerMap();// loan.result.loanData.nested.loanData.detailsMap;
            billMap = new BillMap().with("inn", "taxNumber")
                                            .with("fio", "subscriberName")
                                            .with("Amount", "amountDue")
                                            .with("OriginalAmount", "loanOriginalAmount")
                                            .with("OustandingBalancePrincipal", "overallAmount")
                                            .with("Contract_Ref_No", "contractNo");

            billMap.buildBillMap(innerMap).buildBillMap(outerMap);

            billMap.convertToCents("amountDue", "overallAmount", "loanOriginalAmount");



        } catch (JAXBException e) {
            e.printStackTrace();
            throw new ValidationException("Jaxb exception is thrown");
        }
        return new LoanBillingInfo().convertToBillingInfo(billMap);
    }

    @Override
    public void pay(Integer id, Service service, Integer amount, Integer fee, Integer currency, List<Detail> details) throws IOException, PaymentException {
        Map<String, String> detailsMap = new HashMap<>();
        for(stream.api.Detail detail : details) {
            detailsMap.put(detail.getName(), detail.getValue());
        }

        DateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        timestamp.setTimeZone(TimeZone.getTimeZone("GMT+0400"));

        String template = "<request point=\"%s\"><payment account=\"%s\" check=\"%s\" date=\"%s\" id=\"%s\" service=\"%s\" sum=\"%s\"><attribute name=\"id2\" value=\"%s\"/><attribute name=\"Contract_Ref_No\" value=\"%s\"/><attribute name=\"Currency\" value=\"AZN\"/><attribute name=\"Rate\" value=\"1.0\"/></payment></request>";
        String serviceCode = configurator.getSetting("/adapter/serviceCode");
        String pointNumber = configurator.getSetting("/adapter/pointNumber");

        String message = String.format(template, pointNumber, detailsMap.get("loanNo"), id, timestamp.format(new Date()), id, serviceCode, amount, detailsMap.get("birthDate"), detailsMap.get("contractNo"));
        System.out.println("Message ... " + message);

        MHMResponse response = sendRequest(message);

        try {
            response.validate();
        } catch (MHMResponseException e) {
            e.printStackTrace();
            throw new PaymentException(e.getMessage());
        }

        ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

        MHMStatus mhmStatus = new MHMStatus();
        Runnable statusChecker = new Runnable() {
            String template = "<request point =\"%s\"><status id = \"%s\"/></request>";
            @Override
            public void run() {
                try {
                    MHMResponse response = sendRequest(String.format(template, pointNumber, id));
                    response.validate();
                    Loan loan = MHMResponseMarshaller.unmarshalMHMLoanPayResult(response);
                    mhmStatus.setLoan(loan);
                    System.out.println("FinalCode " +loan.getResult().getFinalCode());
                    if (loan.isSuccessfulPayment()) {
                        System.out.println("successful result ...");
                        scheduledService.shutdown();
                    }
                } catch (InvalidResponseException e) {
                    e.printStackTrace();
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (MHMResponseException e) {
                    e.printStackTrace();
                }
            }

        };
        scheduledService.scheduleAtFixedRate(statusChecker,2,3, TimeUnit.SECONDS);

        try {
            scheduledService.awaitTermination(90, TimeUnit.SECONDS);
            System.out.println("changed to 10 to 20 ... " + scheduledService.isShutdown() + " "+scheduledService.isTerminated());
            //if (!scheduledService.isShutdown()) {
            Loan loan = mhmStatus.getLoan();
            System.out.println(loan);
            if (loan.isSuccessfulPayment()) {
                loan.addPaymentDetails(details);
            } else {
                System.out.println("Payment is not processed successfully");
                throw new PaymentException("Payment is not processed successfully");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, ValidationException {
        ADP0009 adapter = new ADP0009();
        List<stream.api.Detail> details = new ArrayList<>();
        //adapter.getBillList(new ConfiguratorImpl(), details);

        //adapter.sendRequest();
        //adapter.send();
        //adapter.sendHttpRequest();
        //adapter.verify("", "");
    }

    public MHMResponse sendRequest(String requestBody) throws InvalidResponseException {
        String base64SignedData = signatureResolver.sign(requestBody);
        System.out.println("Signature ..." + base64SignedData);
        return sendRequest(requestBody, base64SignedData);
    }

    public MHMResponse sendRequest(String requestBody, String headerSignature) throws InvalidResponseException {
        //String s = "";
        //try {
            //https://system.goldenpay.az:44488/web/service/BankWebService
            //https://e-govpay.az/external/extended
            //URL url = new URL("https://e-govpay.az/external/extended");


            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(configurator.getSetting("/adapter/proxyHost"), Integer.valueOf(configurator.getSetting("/adapter/proxyPort"))));
            Requester request = new Requester.RequestBuilder()
                    .endpoint(configurator.getSetting("/adapter/url"))
                    .header("PayLogic-Signature", headerSignature)
                    .payload(requestBody)
                    .method("POST")
                    .build();
            request.setProxy(proxy);
            Response response = request.execute();
            System.out.println(response);
            return new MHMResponse(response, signatureResolver);

    }

    public void sendHttpRequest() {
        try {
            URL url = new URL("http://adnsu.unibook.az/adnsuEducation/login.jsp");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.unibank.lan", 3128));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            System.out.println(connection.getResponseCode());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            //String url = "https://api.genderize.io/?name=peter";
            //String url = "https://cdn.statuspage.io/se-v2.js";
            //String url = "https://company.clearbit.com/v1/domains/find?name=google";
            String url = "https://api.genderize.io/?name[0]=peter&name[1]=lois&name[2]=stevie";
            //String url = "https://system.goldenpay.az:44488/web/service/BankWebService";
            URL urlObje = new URL(url);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.unibank.lan", 3128));
            HttpsURLConnection connection = (HttpsURLConnection) urlObje.openConnection(proxy);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            System.out.println("verifying ...");
            connection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    System.out.println(s);
                    return true;
                }
            });
            System.out.println(connection.getResponseCode());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
