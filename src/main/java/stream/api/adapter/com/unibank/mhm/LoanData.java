package stream.api.adapter.com.unibank.mhm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;

/**
 * Created by AzarM on 4/30/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LoanData {

    private Map<String, String> loanParameteres;

    @XmlElement(name = "nested")
    private Nested nested;


    public Map<String, String> getLoanParameteres() {
        return loanParameteres;
    }

    public Nested getNested() {
        return nested;
    }

    public void setNested(Nested nested) {
        this.nested = nested;
    }

    public void setLoanParameteres(Map<String, String> loanParameteres) {
        this.loanParameteres = loanParameteres;
    }
}
