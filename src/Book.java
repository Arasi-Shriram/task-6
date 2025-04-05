import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
class Book {
    private String title;
    private String category;
    private int stock;
    public Book(String title, String category, int stock) {
        this.title = title;
        this.category = category;
        this.stock = stock;
    }
    public String getTitle() {
        return title;
    }
    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void borrowBook() {
        if (stock > 0) {
            stock--;
        }
    }
    @Override
    public String toString() {
        return title;
    }
}
class LibrarySwingApp extends JFrame {
    private JTextField searchField, nameField, idField, stockField, defaultValueField;
    private JComboBox<String> categoryBox;
    private JComboBox<Book> bookBox;
    private JLabel borrowDateLabel, dueDateLabel;
    private ArrayList<Book> books;
    public LibrarySwingApp() {
        setTitle("Borrow Books");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        books = new ArrayList<>();
        books.add(new Book("Physics", "Science", 5));
        books.add(new Book("Algebra", "Math", 3));
        books.add(new Book("World War", "History", 2));
        searchField = new JTextField("Search StudentID, Name, LastName", 40);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(searchField);
        add(topPanel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridLayout(6, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nameField = new JTextField();
        idField = new JTextField();
        categoryBox = new JComboBox<>();
        bookBox = new JComboBox<>();
        stockField = new JTextField();
        stockField.setEditable(false);
        for (Book b : books) {
            if (((DefaultComboBoxModel<String>) categoryBox.getModel()).getIndexOf(b.getCategory()) == -1) {
                categoryBox.addItem(b.getCategory());
            }
        }
        categoryBox.addActionListener(e -> {
            bookBox.removeAllItems();
            String selectedCategory = (String) categoryBox.getSelectedItem();
            for (Book b : books) {
                if (b.getCategory().equals(selectedCategory)) {
                    bookBox.addItem(b);
                }
            }
        });

        // When book is selected, show stock
        bookBox.addActionListener(e -> {
            Book selected = (Book) bookBox.getSelectedItem();
            if (selected != null) {
                stockField.setText(String.valueOf(selected.getStock()));
            }
        });

        // Date labels
        borrowDateLabel = new JLabel(LocalDate.now().toString());
        dueDateLabel = new JLabel(LocalDate.now().plusDays(14).toString());

        defaultValueField = new JTextField("2.00");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Date Borrowed:"));
        formPanel.add(borrowDateLabel);

        formPanel.add(new JLabel("Borrower ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Date Due:"));
        formPanel.add(dueDateLabel);

        formPanel.add(new JLabel("Choose Category:"));
        formPanel.add(categoryBox);
        formPanel.add(new JLabel("Default Value:"));
        formPanel.add(defaultValueField);

        formPanel.add(new JLabel("Choose Book:"));
        formPanel.add(bookBox);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        formPanel.add(new JLabel("Book Stock:"));
        formPanel.add(stockField);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        add(formPanel, BorderLayout.CENTER);

        // Borrow button
        JButton borrowButton = new JButton("Borrow");
        borrowButton.addActionListener(e -> {
            Book selected = (Book) bookBox.getSelectedItem();
            if (selected != null && selected.getStock() > 0) {
                selected.borrowBook();
                stockField.setText(String.valueOf(selected.getStock()));
                JOptionPane.showMessageDialog(this, "✅ Book borrowed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Book is out of stock.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(borrowButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new LibrarySwingApp();
    }
}
