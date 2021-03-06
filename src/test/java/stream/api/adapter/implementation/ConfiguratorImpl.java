package stream.api.adapter.implementation;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import stream.api.Configurator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by AzarM on 4/16/2018.
 */
public class ConfiguratorImpl implements Configurator{

    private final String configFilename;

    public ConfiguratorImpl(String configFilename) {
        this.configFilename = configFilename;
    }

    public String getSetting(String fullPathName) {
        BufferedReader br = null;
        try {
            File file = new File(getClass().getClassLoader().getResource(configFilename).getFile());
            //File file = new File("./src/main/java/stream/api/adapter/adp0004/"+ configFilename);
//        for (String fname : file.list()) {
//            System.out.println(fname);
//        }
            //DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            //System.out.println(dis.readUTF());
            String configuration = sb.toString();
            //System.out.println(configuration);
            DocumentBuilder db = null;
            try {
                db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                System.out.println("ParserConfig Exceptions " +ex);
            }
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(configuration));
            Document doc;
            doc = db.parse(is);
            org.w3c.dom.Element element = doc.getDocumentElement();
            System.out.println("Element text content " + element.getTextContent());
            NodeList nodes = element.getElementsByTagName("setting");
            String[] tagName = fullPathName.split("/");

            String targetParameter =  (tagName.length == 1) ? tagName[0] : tagName[2];


            //System.out.println(tagName[1]);
            //System.out.println(tagName[2]);
            for (int i = 0; i < nodes.getLength(); i++) {
                //System.out.println(tagName[i]);
                org.w3c.dom.Element settingElement = (org.w3c.dom.Element)nodes.item(i);
                //System.out.println(settingElement.getAttribute("name"));
                if (settingElement.getAttribute("name").equals(targetParameter)) {
                    //System.out.println(settingElement.getTextContent());
                    return settingElement.getTextContent();
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfiguratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ConfiguratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfiguratorImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public <T> T getSetting(String s, Class<T> aClass) {
        return null;
    }
}
