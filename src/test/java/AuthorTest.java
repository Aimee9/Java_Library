import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.sql2o.*;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTitlesAreTheSame() {
    Author firstAuthor = new Author("Neil Gaiman");
    Author secondAuthor = new Author("Neil Gaiman");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Author myAuthor = new Author("Neil Gaiman");
    myAuthor.save();
    assertTrue(Author.all().get(0).equals(myAuthor));
  }

  @Test
  public void find_findAuthorInDatabase_true() {
    Author myAuthor = new Author("Neil Gaiman");
    myAuthor.save();
    Author savedAuthor = Author.find(myAuthor.getId());
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void addBook_addsBookToAuthor() {
    Author myAuthor = new Author("Neil Gaiman");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myAuthor.addBook(myBook);
    Book savedBook = myAuthor.getBooks().get(0);
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void getBooks_returnsAllBooks_ArrayList() {
    Author myAuthor = new Author("Neil Gaiman");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myAuthor.addBook(myBook);
    List savedBooks = myAuthor.getBooks();
    assertEquals(savedBooks.size(), 1);
  }

  @Test
  public void delete_deletesAllBookAndListAssociations() {
    Author myAuthor = new Author("Neil Gaiman");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myAuthor.addBook(myBook);
    myAuthor.delete();
    assertEquals(myBook.getAuthors().size(), 0);
  }

  @Test
  public void removeBook_removesBookFromJoinTable() {
    Author myAuthor = new Author("Neil Gaiman");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myAuthor.addBook(myBook);
    myAuthor.removeBook(myBook.getId());
    assertFalse(myAuthor.getBooks().contains("The Jungle Book"));
  }

}
