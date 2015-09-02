import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Book {
  private int id;
  private String title;
  private String genre;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getGenre() {
    return genre;
  }

  public Book(String title, String genre) {
    this.title = title;
    this.genre = genre;
  }

  @Override
  public boolean equals(Object otherBook) {
    if(!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getId() == newBook.getId();
    }
  }

  public static List<Book> all() {
    String sql = "SELECT id, title, genre FROM books;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public static void editBookTitle(int id, String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title = :title WHERE id = :id";
      con.createQuery(sql)
      .addParameter("title", title)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title, genre) VALUES (:title, :genre);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("genre", this.genre)
      .executeUpdate()
      .getKey();
    }
  }

  public void addCopies(int numcopies) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies (book_id) VALUES (:book_id)";
      for (int i = 0; i < numcopies; i ++) {
        //create unique copy key for each iteration
        con.createQuery(sql)
          .addParameter("book_id", this.getId())
          .executeUpdate();
      }
    }
  }


  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void addAuthor(Author author) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
        .addParameter("author_id", author.getId())
        .addParameter("book_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Author> getAuthors() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT author_id FROM authors_books WHERE book_id = :book_id";
      List<Integer> authorIds = con.createQuery(sql)
        .addParameter("book_id", this.getId())
        .executeAndFetch(Integer.class);
      ArrayList<Author> authors = new ArrayList<Author>();
      for (Integer authorId : authorIds) {
        String bookQuery = "SELECT * FROM authors WHERE id = :authorId";
        Author author = con.createQuery(bookQuery)
          .addParameter("authorId", authorId)
          .executeAndFetchFirst(Author.class);
        authors.add(author);
      }
      return authors;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM books WHERE id=:id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM authors_books WHERE book_id=:bookId";
      con.createQuery(joinDeleteQuery)
        .addParameter("bookId", this.getId())
        .executeUpdate();
    }
  }


//COLLATE UTF8_GENERAL_CI (before LIKE)
  public static List<Book> search(String searchTitle) {
    String lowerCaseTitle = searchTitle.toLowerCase();
    String sql = "SELECT * FROM books WHERE LOWER (books.title) LIKE '%" + lowerCaseTitle + "%'";
    List<Book> bookResults;
    try (Connection con = DB.sql2o.open()) {
      bookResults = con.createQuery(sql)
        .executeAndFetch(Book.class);
    }
    return bookResults;
  }
}
