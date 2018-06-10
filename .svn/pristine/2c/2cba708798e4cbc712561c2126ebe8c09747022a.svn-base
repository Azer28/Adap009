package stream.api.adapter.com.unibank.request;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AzarM on 4/30/2018.
 */
public class Requester {

    private RequestBuilder builder;
    private Proxy proxy;

    private String endpoint;
    private Map<String, String> headers = new HashMap<>();
    private String payload;
    private String method;


    public static class RequestBuilder {
        private String endpoint;
        private Map<String, String> headers = new HashMap<>();
        private String payload;
        private String method;

        public RequestBuilder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public RequestBuilder header(String headerName, String headerValue) {
            headers.put(headerName, headerValue);
            return this;
        }

        public RequestBuilder payload(String s) {
            this.payload = s;
            return this;
        }

        public RequestBuilder method(String m) {
            this.method = m;
            return this;
        }

        public Requester build() {
            Requester requester = new Requester();
            requester.endpoint = this.endpoint;
            requester.headers = this.headers;
            requester.payload = this.payload;
            requester.method = this.method;
            return requester;
        }

    }



    private Requester() {
        //this.builder = builder;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public Response execute() {
        Response response = new Response();
        try {
            URL url = new URL(endpoint);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(this.proxy);
            connection.setRequestMethod(this.method);
            connection.setDoInput(true);

            for (Map.Entry<String, String> entry: this.headers.entrySet()) {
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }

            if (this.method.equals("POST")) {
                connection.setDoOutput(true);
                BufferedWriter bw =
                    new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                System.out.println("writing ...");
                bw.write(this.payload);
                // if does not close then premature end of file
                bw.flush();
                bw.close();
            }



            try (InputStream is = connection.getInputStream()) {
                int responseCode = connection.getResponseCode();
                //approach WORKS
                byte[] target = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int readNum;
                //is available does not return exact
                //long start = System.nanoTime();
                Map<String, List<String>> headers = connection.getHeaderFields();
                while ((readNum = is.read(target, 0, target.length)) != -1) {
                    baos.write(target, 0, readNum);
                }
                response = new Response(baos.toByteArray(), responseCode, headers);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


}
