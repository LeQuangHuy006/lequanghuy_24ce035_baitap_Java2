package XML_2.BT_2;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;

public class ReadComplexXML {
    public static void main(String[] args) {
        try {
            File file = new File("company.xml");
            if (!file.exists()) {
                System.out.println("File XML khong ton tai!");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            document.getDocumentElement().normalize();
            NodeList employeeList = document.getElementsByTagName("employee");

            System.out.println("Danh sach nhan vien:");

            for (int i = 0; i < employeeList.getLength(); i++) {
                Node employeeNode = employeeList.item(i);

            if (employeeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element employeeElement = (Element) employeeNode;

                    String id = employeeElement.getAttribute("id");

                    String name = employeeElement.getElementsByTagName("name").item(0).getTextContent();

                    Element contactElement = (Element) employeeElement.getElementsByTagName("contact").item(0);
                    String email = contactElement.getElementsByTagName("email").item(0).getTextContent();
                    String phone = contactElement.getElementsByTagName("phone").item(0).getTextContent();

                    Element departmentElement = (Element) employeeElement.getElementsByTagName("department").item(0);
                    String departmentName = departmentElement.getElementsByTagName("name").item(0).getTextContent();
                    String location = departmentElement.getElementsByTagName("location").item(0).getTextContent();

                    System.out.println("=======================================");
                    System.out.println("ID: " + id);
                    System.out.println("Ten: " + name);
                    System.out.println("Email: " + email);
                    System.out.println("So dien thoai: " + phone);
                    System.out.println("Phong ban: " + departmentName);
                   System.out.println("Vi tri: " + location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
