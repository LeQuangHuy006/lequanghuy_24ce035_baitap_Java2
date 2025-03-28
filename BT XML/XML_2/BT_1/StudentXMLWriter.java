package XML_2.BT_1;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;

public class StudentXMLWriter {
    private static final String FILE_NAME = "students.xml";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhap ID sinh vien: ");
            String id = scanner.nextLine();

            System.out.print("Nhap ten sinh vien: ");
            String name = scanner.nextLine();

            System.out.print("Nhap tuoi sinh vien: ");
            String age = scanner.nextLine();

            System.out.print("Nhap chuyen nganh: ");
            String major = scanner.nextLine();   File file = new File(FILE_NAME);
            Document document;
            Element root;

            if (file.exists()) {
               
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(file);
                root = document.getDocumentElement();
            } else {
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.newDocument();

                root = document.createElement("students");
                document.appendChild(root);
            }

          
            Element student = document.createElement("student");
            student.setAttribute("id", id);

            Element nameElement = document.createElement("name");
            nameElement.appendChild(document.createTextNode(name));

            Element ageElement = document.createElement("age");
            ageElement.appendChild(document.createTextNode(age));

            Element majorElement = document.createElement("major");
            majorElement.appendChild(document.createTextNode(major));

           
            student.appendChild(nameElement);
            student.appendChild(ageElement);
            student.appendChild(majorElement);

            root.appendChild(student);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            System.out.println("Sinh vien đa đuoc ghi vao file XML thanh cong!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
