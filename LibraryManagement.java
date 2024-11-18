import java.util.*;

class Book {
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private int quantity;

    public Book(String title, String author, String ISBN, String genre, int quantity) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.quantity = quantity;
    }


    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return ISBN; }
    public String getGenre() { return genre; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void displayBookInfo() {
        System.out.println("Title: " + title + ", Author: " + author + ", ISBN: " + ISBN +
                ", Genre: " + genre + ", Quantity: " + quantity);
    }
}

class Borrower {
    private String name;
    private String contactDetails;
    private String membershipID;

    public Borrower(String name, String contactDetails, String membershipID) {
        this.name = name;
        this.contactDetails = contactDetails;
        this.membershipID = membershipID;
    }

    public String getName() { return name; }
    public String getMembershipID() { return membershipID; }
    public String getContactDetails() { return contactDetails; }
    public void setContactDetails(String contactDetails) { this.contactDetails = contactDetails; }

    public void displayBorrowerInfo() {
        System.out.println("Name: " + name + ", Contact: " + contactDetails + ", Membership ID: " + membershipID);
    }
}

class LibrarySystem {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Borrower> borrowers = new HashMap<>();
    private Map<String, String> borrowedBooks = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getISBN(), book);
        System.out.println("Book added successfully.");
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book book : books.values()) {
            book.displayBookInfo();
        }
    }

    public Book getBook(String ISBN) {
        return books.getOrDefault(ISBN, null);
    }

    public void removeBook(String ISBN) {
        if (books.remove(ISBN) != null) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    // Borrower Management
    public void addBorrower(Borrower borrower) {
        borrowers.put(borrower.getMembershipID(), borrower);
        System.out.println("Borrower added successfully.");
    }

    public void displayAllBorrowers() {
        if (borrowers.isEmpty()) {
            System.out.println("No borrowers available.");
            return;
        }
        for (Borrower borrower : borrowers.values()) {
            borrower.displayBorrowerInfo();
        }
    }

    public Borrower getBorrower(String membershipID) {
        return borrowers.getOrDefault(membershipID, null);
    }

    public void removeBorrower(String membershipID) {
        if (borrowers.remove(membershipID) != null) {
            System.out.println("Borrower removed successfully.");
        } else {
            System.out.println("Borrower not found.");
        }
    }

    // Book Borrowing and Returning
    public void borrowBook(String membershipID, String ISBN) {
        Book book = getBook(ISBN);
        Borrower borrower = getBorrower(membershipID);

        if (book == null || borrower == null) {
            System.out.println("Invalid borrower or book.");
            return;
        }

        if (book.getQuantity() > 0) {
            borrowedBooks.put(membershipID, ISBN);
            book.setQuantity(book.getQuantity() - 1);
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(String membershipID) {
        String ISBN = borrowedBooks.get(membershipID);
        if (ISBN == null) {
            System.out.println("No record of borrowed book for this member.");
            return;
        }

        Book book = getBook(ISBN);
        book.setQuantity(book.getQuantity() + 1);
        borrowedBooks.remove(membershipID);
        System.out.println("Book returned successfully.");
    }

    // Book Search
    public void searchBookByTitle(String title) {
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.displayBookInfo();
                return;
            }
        }
        System.out.println("Book not found.");
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Borrower");
            System.out.println("5. Display All Borrowers");
            System.out.println("6. Remove Borrower");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("9. Search Book by Title");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String ISBN = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    library.addBook(new Book(title, author, ISBN, genre, quantity));
                    break;
                case 2:
                    library.displayAllBooks();
                    break;
                case 3:
                    System.out.print("Enter ISBN of the book to remove: ");
                    ISBN = scanner.nextLine();
                    library.removeBook(ISBN);
                    break;
                case 4:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter contact details: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter membership ID: ");
                    String membershipID = scanner.nextLine();
                    library.addBorrower(new Borrower(name, contact, membershipID));
                    break;
                case 5:
                    library.displayAllBorrowers();
                    break;
                case 6:
                    System.out.print("Enter membership ID to remove: ");
                    membershipID = scanner.nextLine();
                    library.removeBorrower(membershipID);
                    break;
                case 7:
                    System.out.print("Enter membership ID: ");
                    membershipID = scanner.nextLine();
                    System.out.print("Enter ISBN of the book to borrow: ");
                    ISBN = scanner.nextLine();
                    library.borrowBook(membershipID, ISBN);
                    break;
                case 8:
                    System.out.print("Enter membership ID: ");
                    membershipID = scanner.nextLine();
                    library.returnBook(membershipID);
                    break;
                case 9:
                    System.out.print("Enter book title to search: ");
                    title = scanner.nextLine();
                    library.searchBookByTitle(title);
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
