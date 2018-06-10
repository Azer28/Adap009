package stream.api.adapter.com.unibank.mhm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by AzarM on 4/28/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
    @XmlAttribute(name = "code")
    public String code;


    @XmlJavaTypeAdapter(MapAdapter.class)
    @XmlElement(name = "data")
    private LoanData loanData;

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "state")
    private String state;

    @XmlAttribute(name = "substate")
    private String substate;

    @XmlAttribute(name = "final")
    private String finalCode;

    @XmlAttribute(name = "trans")
    private String trans;

    public LoanData getLoanData() {
        return loanData;
    }

    public void setLoanData(LoanData loanData) {
        this.loanData = loanData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubstate() {
        return substate;
    }

    public void setSubstate(String substate) {
        this.substate = substate;
    }

    public String getFinalCode() {
        return finalCode;
    }

    public void setFinalCode(String finalCode) {
        this.finalCode = finalCode;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }
}
