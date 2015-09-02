import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Copy {
  public static int id;
  private int book_id;

  public int getId() {
    return id;
  }

  public int getBookId() {
    return book_id;
  }

  public Copy(int book_id) {
    this.book_id = book_id;
  }

  @Override
  public boolean equals(Object otherCopy) {
    if(!(otherCopy instanceof Copy)) {
      return false;
    } else {
      Copy newCopy = (Copy) otherCopy;
      return this.getBookId() == newCopy.getBookId();
    }
  }

  public static List<Copy> all() {
    String sql = "SELECT id, book_id FROM copies;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }

  public static Copy find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies WHERE id=:id";
      Copy copy = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Copy.class);
      return copy;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM copies WHERE id=:id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
