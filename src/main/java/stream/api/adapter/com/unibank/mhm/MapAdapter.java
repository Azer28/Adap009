package stream.api.adapter.com.unibank.mhm;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AzarM on 4/30/2018.
 */
public class MapAdapter extends XmlAdapter<MapAdapter.AdaptedMap, LoanData> {

    @Override
    public LoanData unmarshal(AdaptedMap v) throws Exception {
        LoanData loanData = new LoanData();

        Map<String, String> map = new HashMap<>();
        System.out.println("Elements ... ");
        System.out.println(v.elements.size());
        for (MapEntry element : v.elements) {
            map.put(element.key, element.value);
        }

        loanData.setNested(v.nested);
        loanData.setLoanParameteres(map);
        System.out.println("size ... and nester ");
        System.out.println(v.nested);

        return loanData;
    }

    @Override
    public AdaptedMap marshal(LoanData v) throws Exception {
        return new AdaptedMap();
    }


    public static class AdaptedMap {
        @XmlElement(name = "input")
        //public List<Element> elements = new ArrayList<>();
        public List<MapEntry> elements = new ArrayList<>();

        @XmlElement(name = "nested")
        public Nested nested;
    }

    public static class MapEntry {
        @XmlAttribute(name = "key")
         public String key;
        @XmlAttribute(name = "value")
         public String value;
    }


}
