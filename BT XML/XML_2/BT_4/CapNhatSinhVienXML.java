package XML_2.BT_4;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.io.File;
import java.util.Scanner;

public class CapNhatSinhVienXML {
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
            System.out.print("Nhap ID sinh vien can cap nhat: ");
            String idCapNhat = scanner.nextLine();

          
            NodeList danhSachSV = document.getElementsByTagName("student");
            boolean timThay = false;

          
            for (int i = 0; i < danhSachSV.getLength(); i++) {
                Element sv = (Element) danhSachSV.item(i);
                if (sv.getAttribute("id").trim().equals(idCapNhat)) {
                 
                    System.out.print("Nhap ten moi: ");
                    String tenMoi = scanner.nextLine();
                    System.out.print("Nhap msv moi: ");
                    String msvMoi = scanner.nextLine();
                    System.out.print("Nhap lop moi: ");
                    String lopMoi = scanner.nextLine();

                   
                    sv.getElementsByTagName("ten").item(0).setTextContent(tenMoi);
                    sv.getElementsByTagName("msv").item(0).setTextContent(msvMoi);
                    sv.getElementsByTagName("lop").item(0).setTextContent(lopMoi);

                    timThay = true;
                    break;
                }
            }
            scanner.close();

            if (timThay) {
               
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);

                System.out.println("Da cap nhat sinh vien co ID: " + idCapNhat);
            } else {
                System.out.println("Khong tim thay sinh vien co ID: " + idCapNhat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
