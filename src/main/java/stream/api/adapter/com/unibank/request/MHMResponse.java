package stream.api.adapter.com.unibank.request;

import stream.api.ValidationException;
import stream.api.adapter.com.unibank.mhm.exception.MHMResponseException;
import com.signature.RSASignature;

/**
 * Created by AzarM on 5/3/2018.
 */
public class MHMResponse<T extends Response> extends Response {

    private final Response response;
    private final RSASignature signatureResolver;

    public MHMResponse(T response, RSASignature signatureResolver) {
        this.response = response;
        this.signatureResolver = signatureResolver;
    }

    public void validate() throws MHMResponseException {
        if (response.getResponseCode() == 200) {

            if (!signatureResolver.verify(response.getByteResponse(), response.getHeader("PayLogic-Signature").get(0))) {
                throw new MHMResponseException("Response is not digitally signed");
            }
        } else {
            throw new MHMResponseException(String.format("Response code is not success %s",
                    response.getResponseCode()));
        }
    }

    @Override
    public String getTextResponse() {
        return this.response.getTextResponse();
    }
}
