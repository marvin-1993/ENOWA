package main;

import java.util.Scanner;

/**
 * Class to set all params for dbName, the desired total amount of data
 * within this run, the max Threads and max Inserts within a commit.
 * @author Marvin
 */
public class Params {

  // set initial values
  private int maxThreads = 5;
  private int stringLength = 20;
  private int maxInsertsWithinCommit = 20;
  private int totalData = 50000;
  private String dbName = "";

  /**
   * With this method, the user is able to type in the dbName, the desired total amount of data
   * within this run, the max Threads and max Inserts within a commit.
   * @param input describes the command line input
   */
  public void start(Scanner input) {
    System.out.println("Wie ist der Name der Datenbank?");
    dbName = input.next();
    System.out.println("Wieviele Datensätze sollen gespeichert werden?");
    totalData = input.nextInt();
    System.out.println("Wieviele Threads sollen parallel laufen?");
    maxThreads = input.nextInt();
    System.out.println("Wieviele INSERT-Anweisungen sollen zwischen zwei Commits "
        + "durchgeführt werden?");
    maxInsertsWithinCommit = input.nextInt();
  }


  public int getMaxThreads() {
    return maxThreads;
  }

  public int getStringLength() {
    return stringLength;
  }

  public int getMaxInsertsWithinCommit() {
    return maxInsertsWithinCommit;
  }

  public int getTotalData() {
    return totalData;
  }

  public String getDbName() {
    return dbName;
  }

  public void setTotalData(int totalData) {
    this.totalData = totalData;
  }
}