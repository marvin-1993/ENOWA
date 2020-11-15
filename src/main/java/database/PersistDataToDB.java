package database;

import calculation.Calculations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import stringGenerator.StringGenerator;

/**
 * Class to persist the Data to the PostgreSQL database.
 * @author Marvin
 */
public class PersistDataToDB implements Runnable {
  private Thread t;
  private String threadName;
  private Long threadStartTime;
  private Long threadEndTime;
  private Connection connection;
  private int stringLength;
  private int maxInsertsWithinCommit;
  private StringGenerator generator = new StringGenerator();
  Calculations calc;
  private int processes;

  /**
   * Method to persist the  data.
   * @param threadName describes the Thread name.
   * @param connection describes the connection details to the database.
   * @param stringLength describes the required String length of an attribute.
   * @param maxInsertsWithinCommit describes the max number of Inserts within one commit.
   * @param calc describes the Calculation object built within this project.
   */
  public PersistDataToDB(String threadName, Connection connection, int stringLength,
      int maxInsertsWithinCommit, Calculations calc, int processes) {
    this.threadName = threadName;
    this.connection = connection;
    this.stringLength = stringLength;
    this.maxInsertsWithinCommit = maxInsertsWithinCommit;
    this.calc = calc;
    this.processes = processes;
    setThreadStartTime();
  }

  public void run() {
    createItem(generator.generateString(stringLength));
  }

  /**
   * Method to create an item which is to be persisted into the database.
   * @param attribute describes an Array of the content which is to be insertet.
   */
  private void createItem(String[] attribute) {
    // For loop to loop over all needed processes.
    for (int b = 1; b <= this.processes; b++) {
      try {
        long commitStartTime = System.currentTimeMillis();
        // For loop constrained by the number of Inserts within a Commit.
        for (int a = 0; a < maxInsertsWithinCommit; a++) {
          String sql = "INSERT INTO ENOWA(Attribute1, Attribute2, Attribute3, "
              + "Attribute4, Attribute5, Attribute6, Attribute7,  Attribute8, "
              + "Attribute9,  Attribute10) "
              + "VALUES (?,?,?,?,?,?,?,?,?,?);";
          PreparedStatement pstmnt = connection.prepareStatement(sql);
          for (int i = 0; i < 10; i++) {
            pstmnt.setString(i + 1, attribute[i]);
          }
          pstmnt.executeUpdate();
        }
        // Commit the statements.
        connection.commit();
        calc.addTimeWithinCommit(System.currentTimeMillis() - commitStartTime);
        // Within the last procecdure, set the Thread end time.
        if (b == this.processes) {
          setThreadEndTime();
          calc.addTimeWithinThread(calculateThreadTotalTime());
        }
      } catch (SQLException e) {
        // Error handling
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Method to set Thread start time.
   */
  private void setThreadStartTime() {
    this.threadStartTime = System.currentTimeMillis();
  }

  /**
   * Method to set Thread end time.
   */
  private void setThreadEndTime() {
    this.threadEndTime = System.currentTimeMillis();
  }

  /**
   * Method to calculate the total Thread time.
   * @return value of the total time needed for this thread.
   */
  public long calculateThreadTotalTime() {
    return this.threadEndTime - this.threadStartTime;
  }

  /**
   * Method to start the parallelized persist procedure.
   */
  public void start() {
    if (t == null) {
      t = new Thread (this, threadName);
      t.run();
    }
  }

}