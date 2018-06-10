package stream.api.adapter.com.unibank.request;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by AzarM on 4/30/2018.
 */
public class Response {

    private byte[] byteResponse;
    private int responseCode;
    private Map<String, List<String>> headers;

    public Response() {

    }

    public Response(byte[] byteResponse, int responseCode, Map<String, List<String>> headers) {
        this.byteResponse = byteResponse;
        this.responseCode = responseCode;
        this.headers = headers;
    }

    public Response(byte[] byteResponse, int responseCode) {
        this(byteResponse, responseCode, null);
    }

    public byte[] getByteResponse() {
        return byteResponse;
    }

    public String getTextResponse() {
        try {
            return new String(byteResponse, "UTF8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException - encoding exception", e);
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public List<String> getHeader(String headerName) {
        return headers.get(headerName);
    }

    @Override
    public String toString() {
        StringBuilder output =  new StringBuilder();
        output.append("Response{\n headers=\n");
        headers.keySet().stream().forEach( key -> output.append(key+": "+ headers.get(key) +"\n"));
        output.append(
                "byteResponse=" + new String(byteResponse));
        output.append("\n, responseCode=" + responseCode+"}");
        return output.toString();
    }
}
