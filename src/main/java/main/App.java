package main;

import calculation.Calculations;
import database.ConnectToDB;
import database.PersistDataToDB;
import database.TableOperations;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class to start the Application.
 * @author Marvin
 */
public class App {

  /**
   * The main Method to start the Application.
   * @param args
   */
  public static void main(String[] args) {

    // Start time of the application.
    long totalStartTime = System.currentTimeMillis();
    // Initial initialization for all Objects needed;
    Params params = new Params();
    Calculations calc = new Calculations();
    Scanner input = new Scanner(System.in);

    // Set params for dbName, the desired total amount of data
    // within this run, the max Threads and max Inserts within a commit.
    params.start(input);

    // Connect to DB
    ConnectToDB conn = new ConnectToDB(params.getDbName());
    Connection postgresql = conn.connect();
    TableOperations tableOperations = new TableOperations(postgresql);

    // Ask the user, if the table is already available or if it should be created.
    System.out.println("Ist die Tabelle bereits vorhanden? (y/n)");
    if (input.next().toString().equals("n")) {
      tableOperations.createTable();
    }
    // Ask the user, if the content of the table should be removed.
    System.out.println("Soll der Inhalt der Tabelle gelöscht werden? (y/n)");
    if (input.next().toString().equals("y")) {
      tableOperations.deleteTableContent();
    }

    // Create an ArrayList with all parallel running Threads
    // constrained by the max number of parallel running Threads given by the user.
    ArrayList<PersistDataToDB> persists = new ArrayList<PersistDataToDB>();
    // Calculates the number of processes for each Thread.
    int processes = params.getTotalData()
        / (params.getMaxThreads() * params.getMaxInsertsWithinCommit()) + 1;
    for (int i = 1; i <= params.getMaxThreads(); i++) {
      persists.add(new PersistDataToDB("Thread-" + i, postgresql,
          params.getStringLength(), params.getMaxInsertsWithinCommit(), calc, processes));
    }
    params.setTotalData(processes * (params.getMaxThreads() * params.getMaxInsertsWithinCommit()));

    // Starts the parallalized process to persist Data.
    for (PersistDataToDB persistDataToDB : persists) {
      persistDataToDB.run();
    }

    // Calculates and Outputs all needed metrics.
    long totalEndTime = System.currentTimeMillis();
    calc.setNumberInsertsWithinThread(params.getTotalData() / params.getMaxThreads());
    float totalProcessingTime = (float)(totalEndTime - totalStartTime) / 1000;
    calc.calculate(params.getTotalData(), totalProcessingTime, params.getMaxInsertsWithinCommit());
  }

}