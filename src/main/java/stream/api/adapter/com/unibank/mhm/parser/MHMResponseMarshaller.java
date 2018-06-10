package stream.api.adapter.com.unibank.mhm.parser;

import stream.api.adapter.com.unibank.mhm.Loan;
import stream.api.adapter.com.unibank.request.MHMResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * Created by AzarM on 5/4/2018.
 */
public class MHMResponseMarshaller {
    public static Loan unmarshalMHMLoanPayResult(MHMResponse mhmResponse) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Loan.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Loan loan = (Loan) unmarshaller.unmarshal(new StringReader(mhmResponse.getTextResponse()));
        return loan;
    }
}
