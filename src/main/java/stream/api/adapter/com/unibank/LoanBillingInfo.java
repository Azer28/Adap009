package stream.api.adapter.com.unibank;

import stream.api.BillingInformation;
import stream.api.ValidationException;


import java.util.Map;

/**
 * Created by AzarM on 5/1/2018.
 */
public class LoanBillingInfo implements BillingInformationConverter<Map<String, String>> {
    @Override
    public BillingInformation convertToBillingInfo(Map<String, String> billMap) throws ValidationException {
        if (billMap.isEmpty())
            throw new ValidationException("Kapital bank loan parameters are not returned");
        BillingInformation resultBillingInfo = new BillingInformation();
        Bill bill = new Bill();
        for (Map.Entry<String, String> loanParameter: billMap.entrySet()) {
            bill.addDetail(loanParameter.getKey(), loanParameter.getValue());
        }
        resultBillingInfo.add(bill);
        return resultBillingInfo;
    }
}
