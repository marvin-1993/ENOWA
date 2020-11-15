package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to connect to PostgreSQL DB.
 * @author Marvin
 */
public class ConnectToDB {

  private String dbName = "";

  public ConnectToDB(String dbName) {
    this.dbName = dbName;
  }

  /**
   * Method to connect to PostgreSQL DB and returns all necessary details.
   * @return connection details.
   */
  public Connection connect() {
    Connection conn = null;
    String url =  "jdbc:postgresql://localhost/" + this.dbName;

    try {
      conn = DriverManager.getConnection(url);
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }

}
