package stream.api.adapter.com.unibank.mhm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by AzarM on 5/1/2018.
 */
public class BillMapTest {

    private BillMap billMap;
    @Before
    public void init() {
        billMap = new BillMap();
    }

    @Test
    public void convertToCents() throws Exception {
        Map<String, String> initialMap = new HashMap<>();
        initialMap.put("overallAmount", "1.01");
        initialMap.put("amountDue", "136.50");
        initialMap.put("loanOriginalAmount", "2000.00");
        billMap.buildBillMap(initialMap);
        billMap.convertToCents("overallAmount", "amountDue", "loanOriginalAmount");
        Assert.assertEquals(billMap.get("overallAmount"), "101");
        Assert.assertEquals(billMap.get("loanOriginalAmount"), "200000");
        Assert.assertEquals(billMap.get("amountDue"), "13650");
        System.out.println(billMap);
    }

}