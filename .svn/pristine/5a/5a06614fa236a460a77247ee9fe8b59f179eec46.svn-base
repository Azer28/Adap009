package stream.api.adapter.com.unibank.mhm;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by AzarM on 5/1/2018.
 */
public class BillMap extends HashMap<String, String> {

    Map<String, String> keyReplacerMap = new HashMap<>();

    public BillMap() {
        super();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return super.entrySet();
    }

    public BillMap with(String oldKeyValue, String newKeyName) {
        keyReplacerMap.put(oldKeyValue, newKeyName);
        return this;
    }

    public BillMap buildBillMap(Map<String, String> loanValues) {
        System.out.println(" keyReplacermap .... ");
        System.out.println(this.keyReplacerMap);

        for (Entry<String, String> entry : loanValues.entrySet()) {
            //replace keys in loanValues if they exist in keyReplacerMap
            if (this.keyReplacerMap.containsKey(entry.getKey())) {
                this.put(this.keyReplacerMap.get(entry.getKey()) ,entry.getValue());
            } else {
                this.put(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public void convertToCents(String... keysToCent) {
        System.out.println("convertToCents ...");
        for (String key: keysToCent) {
            this.replace(key, String.valueOf(new BigDecimal(this.get(key)).multiply(new BigDecimal(100)).longValue()));
        }
    }
}
