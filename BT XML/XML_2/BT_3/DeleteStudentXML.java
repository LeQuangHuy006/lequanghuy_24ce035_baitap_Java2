package XML_2.BT_3;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.io.File;
import java.util.Scanner;

public class DeleteStudentXML {
    public static void main(String[] args) {
        try {
            File file = new File("sinhvien.xml");
            if (!file.exists()) {
                System.out.println("File XML khong ton tai!");
                return;
            }

           
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            document.getDocumentElement().normalize();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhap ID sinh vien can xoa: ");
            String idXoa = scanner.nextLine();
            scanner.close();

          
            NodeList danhSachSV = document.getElementsByTagName("student");
            boolean daXoa = false;

            for (int i = 0; i < danhSachSV.getLength(); i++) {
                Element sv = (Element) danhSachSV.item(i);
                if (sv.getAttribute("id").equals(idXoa)) {
                    sv.getParentNode().removeChild(sv);
                    daXoa = true;
                    break;
                }
            }

            if (daXoa) {
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);

                System.out.println("Da xoa sinh vien co ID: " + idXoa);
            } else {
                System.out.println("Khong tim thay sinh vien co ID: " + idXoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}