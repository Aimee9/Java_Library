import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  // @Test
  // public void save_savesObjectIntoDatabase() {
  //   Copy myCopy = new Copy(1);
  //   myCopy.save();
  //   assertTrue(Copy.all().get(0).equals(myCopy));
  // }

  // @Test
  // public void save_assignsIdToObject() {
  //   Copy myCopy = new Copy("The Jungle Copy", "Fantasy");
  //   myCopy.save();
  //   Copy savedBook = Copy.all().get(0);
  //   assertEquals(myBook.getId(), savedBook.getId());
  // }
  //
  // @Test
  // public void find_findsBookInDatabase_true() {
  //   Copy myBook = new Copy("The Jungle Copy", "Fantasy");
  //   myBook.save();
  //   Copy savedBook = Copy.find(myBook.getId());
  //   assertTrue(myBook.equals(savedBook));
  // }
  //
  // @Test
  // public void addAuthor_addsAuthorToBook() {
  //   Author myAuthor = new Author("Rudyard Kipling");
  //   myAuthor.save();
  //   Copy myBook = new Copy("The Jungle Copy", "Fantasy");
  //   myBook.save();
  //   myBook.addAuthor(myAuthor);
  //   Author savedAuthor = myBook.getAuthors().get(0);
  //   assertTrue(myAuthor.equals(savedAuthor));
  // }
  //
  // @Test
  // public void getAuthors_returnAllAuthors_ArrayList() {
  //   Author myAuthor = new Author("Rudyard Kipling");
  //   myAuthor.save();
  //   Copy myBook = new Copy("The Jungle Copy", "Fantasy");
  //   myBook.save();
  //   myBook.addAuthor(myAuthor);
  //   List savedAuthors = myBook.getAuthors();
  //   assertEquals(savedAuthors.size(), 1);
  // }
  //
  // @Test
  // public void delete_deletesAllBooksAndListAssociations() {
  //   Author myAuthor = new Author("Rudyard Kipling");
  //   myAuthor.save();
  //   Copy myBook = new Copy("The Jungle Copy", "Fantasy");
  //   myBook.save();
  //   myBook.addAuthor(myAuthor);
  //   myBook.delete();
  //   assertEquals(myAuthor.getBooks().size(), 0);
  // }
  //
  // @Test
  // public void edit_newBookTitle() {
  //   Author myAuthor = new Author("Rudyard Kipling");
  //   myAuthor.save();
  //   Copy myBook = new Copy("The Jungle Copy", "Fantasy");
  //   myBook.save();
  //   Copy editBookTitle = new Copy("The Jungle Copy: Second Edition", "Fantasy");
  //   myBook.editBookTitle(editBookTitle.getId(), editBookTitle.getTitle());
  //   assertTrue(editBookTitle.getTitle() == "The Jungle Copy: Second Edition");
  // }
  //
  // @Test
  // public void search_filtersBooksByTitle() {
  //   Copy myBook = new Copy("The Jungle Copy", "Fantasy");
  //   myBook.save();
  //   List searchResult = Copy.search("jUngle");
  //   assertTrue(myBook.equals(searchResult.get(0)));
  // }

}
