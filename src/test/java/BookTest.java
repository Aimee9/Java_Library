import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


//1 means available and 0 means checked out
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTitlesAreTheSame() {
    Book firstBook = new Book("The Jungle Book", "Fantasy");
    Book secondBook = new Book("The Jungle Book", "Fantasy");
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    assertTrue(Book.all().get(0).equals(myBook));
  }

  @Test
  public void save_assignsIdToObject() {
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    Book savedBook = Book.all().get(0);
    assertEquals(myBook.getId(), savedBook.getId());
  }

  @Test
  public void find_findsBookInDatabase_true() {
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void addAuthor_addsAuthorToBook() {
    Author myAuthor = new Author("Rudyard Kipling");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myBook.addAuthor(myAuthor);
    Author savedAuthor = myBook.getAuthors().get(0);
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void getAuthors_returnAllAuthors_ArrayList() {
    Author myAuthor = new Author("Rudyard Kipling");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myBook.addAuthor(myAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(savedAuthors.size(), 1);
  }

  @Test
  public void delete_deletesAllBooksAndListAssociations() {
    Author myAuthor = new Author("Rudyard Kipling");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    myBook.addAuthor(myAuthor);
    myBook.delete();
    assertEquals(myAuthor.getBooks().size(), 0);
  }

  @Test
  public void edit_newBookTitle() {
    Author myAuthor = new Author("Rudyard Kipling");
    myAuthor.save();
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    Book editBookTitle = new Book("The Jungle Book: Second Edition", "Fantasy");
    myBook.editBookTitle(editBookTitle.getId(), editBookTitle.getTitle());
    assertTrue(editBookTitle.getTitle() == "The Jungle Book: Second Edition");
  }

  @Test
  public void search_filtersBooksByTitle() {
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    List searchResult = Book.search("jUngle");
    assertTrue(myBook.equals(searchResult.get(0)));
  }

  @Test
  public void addCopies_AddsMultipleCopiesToCopiesTable() {
    Book myBook = new Book("The Jungle Book", "Fantasy");
    myBook.save();
    Integer numberOfCopies = 3;
    myBook.addCopies(numberOfCopies);
    assertEquals(Copy.all().size(), 3);
  }

}
