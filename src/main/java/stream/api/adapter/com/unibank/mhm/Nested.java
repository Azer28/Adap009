package stream.api.adapter.com.unibank.mhm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by AzarM on 4/30/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Nested {
    @XmlAttribute(name = "id")
    public String id;


    @XmlJavaTypeAdapter(MapAdapter.class)
    @XmlElement(name = "data")
    private LoanData loanData;

    public LoanData getLoanData() {
        return loanData;
    }

    public void setLoanData(LoanData loanData) {
        this.loanData = loanData;
    }
}
