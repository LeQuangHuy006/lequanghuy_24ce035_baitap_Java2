package GUI.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LibraryManager extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTitle, txtAuthor, txtYear, txtPublisher, txtPages, txtGenre, txtPrice, txtISBN;
    private static final String FILE_NAME = "books.xml";

    public LibraryManager() {
        setTitle("Quản Lý Thư Viện Sách");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bảng hiển thị danh sách sách
        String[] columnNames = {"Tên sách", "Tác giả", "Năm", "NXB", "Số trang", "Thể loại", "Giá", "ISBN"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        loadBooksFromXML();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form nhập thông tin sách
        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtYear = new JTextField();
        txtPublisher = new JTextField();
        txtPages = new JTextField();
        txtGenre = new JTextField();
        txtPrice = new JTextField();
        txtISBN = new JTextField();

        inputPanel.add(new JLabel("Tên sách:")); inputPanel.add(txtTitle);
        inputPanel.add(new JLabel("Tác giả:")); inputPanel.add(txtAuthor);
        inputPanel.add(new JLabel("Năm XB:")); inputPanel.add(txtYear);
        inputPanel.add(new JLabel("NXB:")); inputPanel.add(txtPublisher);
        inputPanel.add(new JLabel("Số trang:")); inputPanel.add(txtPages);
        inputPanel.add(new JLabel("Thể loại:")); inputPanel.add(txtGenre);
        inputPanel.add(new JLabel("Giá:")); inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("ISBN:")); inputPanel.add(txtISBN);

        add(inputPanel, BorderLayout.NORTH);

        // Nút chức năng
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện nút
        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnRefresh.addActionListener(e -> loadBooksFromXML());

        setVisible(true);
    }

    // Hàm đọc dữ liệu từ XML và hiển thị lên bảng
    private void loadBooksFromXML() {
        tableModel.setRowCount(0);
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList bookList = doc.getElementsByTagName("book");

            for (int i = 0; i < bookList.getLength(); i++) {
                Element book = (Element) bookList.item(i);
                String title = book.getElementsByTagName("title").item(0).getTextContent();
                String author = book.getElementsByTagName("author").item(0).getTextContent();
                String year = book.getElementsByTagName("year").item(0).getTextContent();
                String publisher = book.getElementsByTagName("publisher").item(0).getTextContent();
                String pages = book.getElementsByTagName("pages").item(0).getTextContent();
                String genre = book.getElementsByTagName("genre").item(0).getTextContent();
                String price = book.getElementsByTagName("price").item(0).getTextContent();
                String isbn = book.getElementsByTagName("isbn").item(0).getTextContent();

                tableModel.addRow(new Object[]{title, author, year, publisher, pages, genre, price, isbn});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm thêm sách vào XML
    private void addBook() {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;

            if (file.exists()) {
                doc = builder.parse(file);
            } else {
                doc = builder.newDocument();
                Element root = doc.createElement("books");
                doc.appendChild(root);
            }

            Element root = doc.getDocumentElement();
            Element newBook = doc.createElement("book");

            newBook.appendChild(createElement(doc, "title", txtTitle.getText()));
            newBook.appendChild(createElement(doc, "author", txtAuthor.getText()));
            newBook.appendChild(createElement(doc, "year", txtYear.getText()));
            newBook.appendChild(createElement(doc, "publisher", txtPublisher.getText()));
            newBook.appendChild(createElement(doc, "pages", txtPages.getText()));
            newBook.appendChild(createElement(doc, "genre", txtGenre.getText()));
            newBook.appendChild(createElement(doc, "price", txtPrice.getText()));
            newBook.appendChild(createElement(doc, "isbn", txtISBN.getText()));

            root.appendChild(newBook);
            saveXML(doc);
            loadBooksFromXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm hỗ trợ tạo phần tử XML
    private Element createElement(Document doc, String tag, String value) {
        Element element = doc.createElement(tag);
        element.appendChild(doc.createTextNode(value));
        return element;
    }

    // Hàm lưu XML
    private void saveXML(Document doc) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FILE_NAME));
        transformer.transform(source, result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryManager::new);
    }
}
