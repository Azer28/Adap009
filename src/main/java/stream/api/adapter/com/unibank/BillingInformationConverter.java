package stream.api.adapter.com.unibank;

import stream.api.BillingInformation;
import stream.api.ValidationException;


/**
 * Created by AzarM on 5/1/2018.
 */
public interface BillingInformationConverter<T> {

    BillingInformation convertToBillingInfo(T rawData) throws ValidationException;
}
