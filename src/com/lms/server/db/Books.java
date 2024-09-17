    package com.lms.server.db;

    import com.lms.server.models.Book;

    import java.lang.reflect.Method;
    import java.util.List;
    import java.util.Map;
    import java.util.concurrent.ConcurrentHashMap;
    import java.util.stream.Collectors;

    public class Books {
        private final static Map<String, Book> books = new ConcurrentHashMap<>();
        static {
            // Adding dummy data
            addBook("1234567890", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction");
            addBook("0987654321", "To Kill a Mockingbird", "Harper Lee", "Fiction");
            addBook("1122334455", "1984", "George Orwell", "Dystopian");
            addBook("2233445566", "Pride and Prejudice", "Jane Austen", "Romance");
            addBook("3344556677", "The Catcher in the Rye", "J.D. Salinger", "Fiction");
            addBook("4455667788", "Moby-Dick", "Herman Melville", "Adventure");
            addBook("5566778899", "The Hobbit", "J.R.R. Tolkien", "Fantasy");
            addBook("6677889900", "War and Peace", "Leo Tolstoy", "Historical Fiction");
            addBook("7788990011", "The Odyssey", "Homer", "Epic");
            addBook("8899001122", "Crime and Punishment", "Fyodor Dostoevsky", "Psychological Fiction");
        }

        public static boolean checkIfISBNExist(String ISBN){
            return books.containsKey(ISBN);
        }

        public static void removeBook(String ISBN){
            books.remove(ISBN);
        }

        public static void addBook(String ISBN,String name,String author,String genre)
        {
            books.put(ISBN,new Book(ISBN,name,author,genre));
        }

        public static List<Book> getAllBooks(){
            return books.values().stream().toList();
        }
        public static Map<String,List<Book>> viewGenreWiseBooks(){

            return books.values().stream().collect(Collectors.groupingBy(Book::getGenre));

        }
        public static List<Book> searchBook(String searchKey, String val) {
            return books.values().stream()
                    .filter(book -> {
                        try {
                            Method method = Book.class.getMethod("get" + searchKey.substring(0, 1).toUpperCase() + searchKey.substring(1));
                            String fieldValue = (String) method.invoke(book);
                            return fieldValue != null && fieldValue.toLowerCase().contains(val.toLowerCase());
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());
        }

        public static Book getBookByISBN(String ISBN){
            return books.get(ISBN);
        }


    }
