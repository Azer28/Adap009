package stream.api.adapter.com.unibank;

import com.signature.RSASignature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import stream.api.PaymentException;
import stream.api.Service;
import stream.api.ValidationException;
import stream.api.adapter.implementation.ConfiguratorImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


/**
 * Created by AzarM on 4/28/2018.
 */
public class ADP0009Test {

    private  ADP0009 adapterKapital;
    private Service service = new ServiceImpl();

    @Before
    public void setUp() throws Exception {
        adapterKapital = new ADP0009();
        adapterKapital.initialize(new ConfiguratorImpl("ADP0009.configuration.xml"));
    }

    @Test
    public void initialize() throws Exception {

    }

    @Test
    public void getBillList() throws Exception {
        List<stream.api.Detail> details = new ArrayList<>();
        details.add(new Detail("loanNo", "0219266"));
        details.add(new Detail("birthDate", "02.11.1990"));
        List<stream.api.Bill> bills = adapterKapital.getBillList(new ServiceImpl(), details);
        Assert.assertEquals(bills.get(0).getDetailList().size(), 15);
    }

    @Test
    //@Ignore
    public void pay() throws IOException, PaymentException {
        List<stream.api.Detail> details = new ArrayList<>();
        details.add(new Detail("loanNo", "0219266"));
        details.add(new Detail("birthDate", "02.11.1990"));
        details.add(new Detail("contractNo", "266563918701944105"));
        adapterKapital.pay(3494, service , 100, 0, 944, details);
        Assert.assertEquals(details.size(), 9);
    }

    @Test
    public void validate() throws IOException, ValidationException {
        List<stream.api.Detail> details = new ArrayList<>();
        details.add(new Detail("loanNo", "0219266"));
        details.add(new Detail("birthDate", "02.11.1990"));
        details.add(new Detail("contractNo", "266563918701944105"));
        adapterKapital.validate(service, details);
    }

    @Test
    public void testSignature() {
        RSASignature signatureResolver = new RSASignature();
        String signeData = signatureResolver.sign("<request point =\"273\"><status id = \"3494\"/></request>");
        System.out.println(signeData);
    }
    @Ignore
    @Test
    public void executeService() {
        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //System.out.println("runnable");
         int i = 0;
        Runnable runnableTask = () -> {
            try {
                System.out.println("start ...");
                //executorService.shutdown();
               // if ()
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable r = new Runnable() {
            private int  k = 0;
            @Override
            public void run() {
                k++;
                System.out.println("running "+ k);
                if (k > 5) {
                    System.out.println(k);
                    executorService.shutdown();
                }
            }
        };
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(3);
            return "kill the light";
        };
        ScheduledFuture scheduledFuture = executorService.scheduleAtFixedRate(r, 1, 2, TimeUnit.SECONDS);

        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
            System.out.println("changed to 10 to 20 ... ");
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception thrown ... ");
            e.printStackTrace();
        }
        System.out.println("getting the object");
//        try {
//            System.out.println("getting the object");
//            Object obj = scheduledFuture.get();
//            System.out.println("got the object");
//            System.out.println(obj);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


//        executorService.submit(runnableTask);
//        Future<String> future = executorService.submit(callable);
//        try {
//            System.out.println("getting the future ...");
//            String val = future.get();
//            System.out.println("got the future ...");
//            System.out.println(val);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("under fears");


    }
    Date date;
    @Test
    public void testNull() {
        System.out.println(date);
    }
}