package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class describes the table operations to Create the Table ENOWA and to delte the Content.
 * @author Marvin
 */
public class TableOperations {

  private Connection connection;

  public TableOperations(Connection conn) {
    this.connection = conn;
  }

  /**
   * This method deletes the Content of the Table ENOWA.
   */
  public void deleteTableContent() {
    try {
      String sql = "DELETE FROM ENOWA;";
      PreparedStatement pstmnt = connection.prepareStatement(sql);
      pstmnt.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * This method creates a new Table named ENOWA.
   */
  public void createTable() {
    try {
      String sql = "CREATE TABLE ENOWA (\n"
          + "    attribute1 VARCHAR(100),\n"
          + "    attribute2 VARCHAR(100),\n"
          + "    attribute3 VARCHAR(100),\n"
          + "    attribute4 VARCHAR(100),\n"
          + "    attribute5 VARCHAR(100),\n"
          + "    attribute6 VARCHAR(100),\n"
          + "    attribute7 VARCHAR(100),\n"
          + "    attribute8 VARCHAR(100),\n"
          + "    attribute9 VARCHAR(100),\n"
          + "    attribute10 VARCHAR(100)\n"
          + ");";
      PreparedStatement pstmnt = connection.prepareStatement(sql);
      pstmnt.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

}
