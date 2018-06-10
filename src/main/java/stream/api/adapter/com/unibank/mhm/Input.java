package stream.api.adapter.com.unibank.mhm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.StringReader;

/**
 * Created by AzarM on 4/28/2018.
 */
public class Input {
    @XmlAttribute
    public String key;
    @XmlAttribute
    public String value;

    public static void main(String[] args) {
        JAXBContext jaxbContext = null;
        try {
//            String convertedMessage = "<response title=\"dfdf\" account=\"all\">\n" +
//                    "    <result code=\"0\" service=\"0\">\n" +
//                    "        <data>\n" +
//                    "            <input key=\"fio\" keyTitle=\"M&#252;&#351;t&#601;ri\" value=\"&#399;HM&#399;**** AYT**** &#350;&#304;*** QI**\" valueTitle=\"&#399;HM&#399;**** AYT**** &#350;&#304;*** QI**\" />\n" +
//                    "            <input key=\"inn\" keyTitle=\"S&#601;n&#601;d\" value=\"AZE15679222\" valueTitle=\"AZE15679222\" />\n" +
//                    "            <nested id=\"contracts\">\n" +
//                    "                <data account=\"all\">\n" +
//                    "                    <input key=\"title\" keyTitle=\"title\" value=\"BirKart(12-07-2017). M&#601;bl&#601;&#287; 2000.00 AZN\" valueTitle=\"BirKart(12-07-2017). M&#601;bl&#601;&#287; 2000.00 AZN\" />\n" +
//                    "                    <input key=\"Account\" keyTitle=\"Mu&#351;t&#601;rinin hesab n&#246;mr&#601;si\" value=\"\" valueTitle=\"\" />\n" +
//                    "                    <input key=\"Book_Date\" keyTitle=\"Verilm&#601; tarixi\" value=\"12-07-2017\" valueTitle=\"12-07-2017\" />\n" +
//                    "                    <input key=\"Contract_Ref_No\" keyTitle=\"Muqavil&#601;\" value=\"266563918701944105\" valueTitle=\"266563918701944105\" />\n" +
//                    "                    <input key=\"Currency\" keyTitle=\"Valyuta\" value=\"AZN\" valueTitle=\"AZN\" />\n" +
//                    "                    <input key=\"Description\" keyTitle=\"Kredit n&#246;v&#252;\" value=\"BirKart\" valueTitle=\"BirKart\" />\n" +
//                    "                    <input key=\"Rate\" keyTitle=\"Valyuta kursu\" value=\"1.0\" valueTitle=\"1.0\" />\n" +
//                    "                    <input key=\"Accrual\" keyTitle=\"&#220;mumi qal&#305;q borc\" value=\"0.00\" valueTitle=\"0.00 AZN\" />\n" +
//                    "                    <input key=\"Amount\" keyTitle=\"&#214;d&#601;nilm&#601;li m&#601;bl&#601;&#287;\" value=\"136.50\" valueTitle=\"136.50 AZN\" />\n" +
//                    "                    <input key=\"Amount_cr\" keyTitle=\"C&#601;rim&#601; m&#601;bl&#601;&#287;i\" value=\"0.00\" valueTitle=\"0.00 AZN\" />\n" +
//                    "                    <input key=\"OriginalAmount\" keyTitle=\"Kredit m&#601;bl&#601;&#287;i\" value=\"2000.00\" valueTitle=\"2000.00 AZN\" />\n" +
//                    "                    <input key=\"OustandingBalanceInterest\" keyTitle=\"Dig&#601;r borc (faiz, komissiya v&#601; c&#601;rim&#601;l&#601;r)\" value=\"0.00\" valueTitle=\"0.00 AZN\" />\n" +
//                    "                    <input key=\"OustandingBalancePrincipal\" keyTitle=\"Bu g&#252;n&#601; qalan tam borc\" value=\"2028.70\" valueTitle=\"2028.70 AZN\" />\n" +
//                    "                </data>\n" +
//                    "            </nested>\n" +
//                    "        </data>\n" +
//                    "    </result>\n" +
//                    "</response>";
            String convertedMessage = "<response title=\"dfdf\" account=\"all\">\n" +
                    "    <result code=\"0\" service=\"0\">\n" +
                    "        <data>\n" +
                    "            <input key=\"fio\" keyTitle=\"M&#252;&#351;t&#601;ri\" value=\"&#399;HM&#399;**** AYT**** &#350;&#304;*** QI**\" valueTitle=\"&#399;HM&#399;**** AYT**** &#350;&#304;*** QI**\" />\n" +
                    "            <input key=\"inn\" keyTitle=\"S&#601;n&#601;d\" value=\"AZE15679222\" valueTitle=\"AZE15679222\" />\n" +
                    "            <nested id=\"contracts\">\n" +
                    "                <data account=\"all\">\n" +
                    "                    <input key=\"title\" keyTitle=\"title\" value=\"BirKart(12-07-2017). M&#601;bl&#601;&#287; 2000.00 AZN\" valueTitle=\"BirKart(12-07-2017). M&#601;bl&#601;&#287; 2000.00 AZN\" />\n" +
                    "                    <input key=\"Account\" keyTitle=\"Mu&#351;t&#601;rinin hesab n&#246;mr&#601;si\" value=\"\" valueTitle=\"\" />\n" +
                    "                    <input key=\"Book_Date\" keyTitle=\"Verilm&#601; tarixi\" value=\"12-07-2017\" valueTitle=\"12-07-2017\" />\n" +
                    "                    <input key=\"Contract_Ref_No\" keyTitle=\"Muqavil&#601;\" value=\"266563918701944105\" valueTitle=\"266563918701944105\" />\n" +
                    "                    <input key=\"Currency\" keyTitle=\"Valyuta\" value=\"AZN\" valueTitle=\"AZN\" />\n" +
                    "                    <input key=\"Description\" keyTitle=\"Kredit n&#246;v&#252;\" value=\"BirKart\" valueTitle=\"BirKart\" />\n" +
                    "                    <input key=\"Rate\" keyTitle=\"Valyuta kursu\" value=\"1.0\" valueTitle=\"1.0\" />\n" +
                    "                    <input key=\"Accrual\" keyTitle=\"&#220;mumi qal&#305;q borc\" value=\"0.00\" valueTitle=\"0.00 AZN\" />\n" +
                    "                    <input key=\"Amount\" keyTitle=\"&#214;d&#601;nilm&#601;li m&#601;bl&#601;&#287;\" value=\"136.50\" valueTitle=\"136.50 AZN\" />\n" +
                    "                    <input key=\"Amount_cr\" keyTitle=\"C&#601;rim&#601; m&#601;bl&#601;&#287;i\" value=\"0.00\" valueTitle=\"0.00 AZN\" />\n" +
                    "                    <input key=\"OriginalAmount\" keyTitle=\"Kredit m&#601;bl&#601;&#287;i\" value=\"2000.00\" valueTitle=\"2000.00 AZN\" />\n" +
                    "                    <input key=\"OustandingBalanceInterest\" keyTitle=\"Dig&#601;r borc (faiz, komissiya v&#601; c&#601;rim&#601;l&#601;r)\" value=\"0.00\" valueTitle=\"0.00 AZN\" />\n" +
                    "                    <input key=\"OustandingBalancePrincipal\" keyTitle=\"Bu g&#252;n&#601; qalan tam borc\" value=\"2028.70\" valueTitle=\"2028.70 AZN\" />\n" +
                    "                </data>\n" +
                    "            </nested>\n" +
                    "        </data>\n" +
                    "    </result>\n" +
                    "</response>";
            jaxbContext = JAXBContext.newInstance(Loan.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            //unmarshaller.setAdapter(new MapAdapter());
            Loan loan = (Loan) unmarshaller.unmarshal(new StringReader(convertedMessage));

            System.out.println(loan.getAccount());
//            System.out.println(loan.result.loanData.map);
//            System.out.println(loan.result.loanData.nested.loanData.map);
//            System.out.println(loan.result.code);

            System.out.println(loan);
            //System.out.println(loan.result.data.inputs.get(0).key);
            //System.out.println(loan.result.data.inputs.get(0).value);
            //System.out.println(loan.result.data.nested.innerData.inputs.size());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
