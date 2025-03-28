package XML_1.BT_1;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class DOMParserExample {
    public static void main(String[] args) {
        try {
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();


            Document document = builder.parse("products.xml");

            NodeList productList = document.getElementsByTagName("product");

            
            for (int i = 0; i < productList.getLength(); i++) {
                Element product = (Element) productList.item(i);
                String name = product.getElementsByTagName("name").item(0).getTextContent();
                String price = product.getElementsByTagName("price").item(0).getTextContent();
                System.out.println("Product: " + name + ", Price: " + price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
