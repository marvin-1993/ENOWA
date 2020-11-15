package calculation;

import java.util.ArrayList;

/**
 * Class to perform the required calculation output metrics.
 * @author Marvin
 */
public class Calculations {

  // Create ArrayLists for the times needed to calculate the commit and Thread times.
  ArrayList<Long> timeWithinCommit = new ArrayList<Long>();
  ArrayList<Long> timeWithinThread = new ArrayList<Long>();
  double averageTimeWithinCommit;
  int averageTimeWithinThread;
  int numberInsertsWithinThread;

  // Method to set the number of datasets inserted within one Thread.
  public void setNumberInsertsWithinThread(int numberInsertsWithinThread) {
    this.numberInsertsWithinThread = numberInsertsWithinThread;
  }

  /**
   * Method to calculate the time need within the fastest Thread.
   * @return min value time of the fastest Thread.
   */
  private Long fastestThread() {
    Long min = this.timeWithinThread.get(0);
    for (Long time : this.timeWithinThread) {
      if (time < min) {
        min = time;
      }
    }
    return min;
  }

  /**
   * Method to calculate the time need within the slowest Thread.
   * @return max value time of the slowest Thread.
   */
  private Long slowestThread() {
    Long max = this.timeWithinThread.get(0);
    for (Long time : this.timeWithinThread) {
      if (time > max) {
        max = time;
      }
    }
    return max;
  }

  /**
   * Method to calculate the datasets inserted within the fastest Thread.
   * @return datasets inserted per second.
   */
  private Long fastestThreadPerSecond() {
    return this.numberInsertsWithinThread / (fastestThread() / 1000);
  }

  /**
   * Method to calculate the datasets inserted within the slowest Thread.
   * @return datasets inserted per second.
   */
  private Long slowestThreadPerSecond() {
    return this.numberInsertsWithinThread / (slowestThread() / 1000);
  }

  /**
   * ethod to calculate the datasets inserted within the average Thread.
   * @return datasets inserted per second.
   */
  private int calculateAverageThreadTime() {
    int totalTime = 0;
    for (Long time : this.timeWithinThread) {
      totalTime += time;
    }
    this.averageTimeWithinThread = totalTime / this.timeWithinThread.size();
    return this.numberInsertsWithinThread / (this.averageTimeWithinThread / 1000);
  }

  public void addTimeWithinCommit(Long time) {
    this.timeWithinCommit.add(time);
  }

  public void addTimeWithinThread(Long time) {
    this.timeWithinThread.add(time);
  }

  /**
   * Method to calculate the slowest processing datasets within one commit per second.
   * @param maxInsertsWithinCommit describes the max Inserts within one Commit.
   * @return the calculated value.
   */
  private int slowestCommit(int maxInsertsWithinCommit) {
    Long max = this.timeWithinCommit.get(1);
    for (Long time : this.timeWithinCommit) {
      if (time > max) {
        max = time;
      }
    }
    return (int) (1000 / (max.doubleValue() / maxInsertsWithinCommit));
  }

  /**
   * Method to calculate the fastest processing datasets within one commit per second.
   * @param maxInsertsWithinCommit describes the max Inserts within one Commit.
   * @return the calculated value.
   */
  private int fastestCommit(int maxInsertsWithinCommit) {
    Long min = this.timeWithinCommit.get(1);
    for (Long time : this.timeWithinCommit) {
      if (time < min) {
        min = time;
      }
    }
    return (int) (1000 / (min.doubleValue() / maxInsertsWithinCommit));
  }

  /**
   * Method to calculate the average processing datasets within one commit per second.
   * @param maxInsertsWithinCommit describes the max Inserts within one Commit.
   * @return the calculated value.
   */
  private int calculateAverageCommitTime(int maxInsertsWithinCommit) {
    int totalTime = 0;
    for (Long time : this.timeWithinCommit) {
      totalTime += time;
    }
    this.averageTimeWithinCommit = totalTime / this.timeWithinCommit.size();
    return (int) (1000 / (this.averageTimeWithinCommit / maxInsertsWithinCommit));
  }

  /**
   * Method to output all metrics.
   * @param totalData describes the total number of data inserted.
   * @param totalProcessingTime describes the total processing time.
   * @param maxInsertsWithinCommit describes the max Inserts within one commit.
   */
  public void calculate(int totalData, float totalProcessingTime, int maxInsertsWithinCommit) {
    // Output relevant metrics
    System.out.println("\nGesamtdurchsatz:             " + totalData + " Datensätze"
        + "\nDurchsatz pro Sekunde:       " + (int) (totalData / totalProcessingTime)
        + " Datensätze/s"
        + "\n\nThread (Durchsatz): "
        + "\nschnellster Thread:          " + fastestThreadPerSecond() + " Datensätze/s"
        + "\nlangsamster Thread:          " + slowestThreadPerSecond() + " Datensätze/s"
        + "\ndurchschnittlicher Thread:   " + calculateAverageThreadTime() + " Datensätze/s"
        + "\nLaufzeit langsamster Thread: " + slowestThread() + " ms"
        + "\nLaufzeit schnellster Thread: " + fastestThread() + " ms"
        + "\n\nCommit (Durchsatz): "
        + "\nschnellster Commit:          " + fastestCommit(maxInsertsWithinCommit)
        + " Datensätze/s"
        + "\nlangsamster Commit:          " + slowestCommit(maxInsertsWithinCommit)
        + " Datensätze/s"
        + "\ndurchschnittlicher Commit:   " + calculateAverageCommitTime(maxInsertsWithinCommit)
        + " Datensätze/s"
        + "\n\nGesamtlaufzeit:              " + totalProcessingTime * 1000 + " ms"
    );
  }

}
