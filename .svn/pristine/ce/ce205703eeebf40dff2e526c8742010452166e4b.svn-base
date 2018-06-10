package stream.api.adapter.com.unibank.mhm;



import stream.api.adapter.com.unibank.Detail;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Created by AzarM on 4/28/2018.
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.NONE)
public class Loan {

    @XmlAttribute
    public String title;
    @XmlAttribute
    private String account;
    @XmlAttribute
    public String bookDate;
    @XmlAttribute
    public Double debtToPay;
    @XmlElement
    private Result result;

    @XmlAnyElement(lax = true)
    public List<Input> input;

    public String getAccount() {
        return account;
    }

    public Map<String, String> getOuterMap() {
        return result.getLoanData().getLoanParameteres();
    }
    public Map<String, String> getInnerMap() {
        return result.getLoanData().getNested().getLoanData().getLoanParameteres();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void addPaymentDetails(List<stream.api.Detail> details) {
        details.add(new Detail("id", result.getId()));
        details.add(new Detail("state", result.getState()));
        details.add(new Detail("substate", result.getSubstate()));
        details.add(new Detail("code", result.getCode()));
        details.add(new Detail("final", result.getFinalCode()));
        details.add(new Detail("trans", result.getTrans()));
    }

    public boolean isSuccessfulPayment() {
        return this.getResult().getFinalCode().equals("1") && this.getResult().getState().equals("60");
    }
}
