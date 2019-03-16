import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BigData {
  // number of columns in the csv
  private static final int COLUMNS = 47;

  private Scanner input;
  // entries in the csv file
  private List<Entry> contents;
  
  public BigData() {
    input = FileUtils.openToRead("Education.csv");
    contents = new ArrayList<Entry>();
  }

  public static void main(String[] args) {
    BigData p = new BigData();
    p.run();
  }

  /**
   * Main run file that is called
   */
  public void run() {
    readFile();

    List<String> headers = new ArrayList<String>();
    headers.add("High school diploma only, 1980");
    headers.add("Some college or associate's degree, 2000");
    headers.add("Percent of adults with less than a high school diploma, 1990");
    headers.add("Some college or associate's degree, 2012-2016");
    printTable(headers);
  }

  /**
   * Prints a table with the given headers using a random subset of the contents
   * @param headers Given list of headers for the table
   */
  public void printTable(List<String> headers) {
    int len = 20;
    int start = (int)(Math.random() * (contents.size() - len));
    System.out.println("---------------------------------------------------------------------------------------------------------");
    for (Entry entry : contents.subList(start, start + len)) {
      System.out.print(entry);
      for (String header : headers)
        System.out.printf(" | " + header.substring(0, 15) + "...: %10.2f", entry.get(header));
      System.out.println();
    }
    System.out.println("---------------------------------------------------------------------------------------------------------");
  }

  /**
   * Reads the contents of the csv file and stores it into contents List
   */
  public void readFile() {
    // get rid of the useless headers
    input.nextLine();
    input.nextLine();
    input.nextLine();
    input.nextLine();

    // get character by character
    input.useDelimiter("");

    // current "value" in csv
    String current = "";
    // current character
    char c = ' ';
    // next character
    char n = input.next().charAt(0);
    // whether or not we are currently in a valid value
    boolean inValue = true;
    // whether or not the value started with quotes (then it needs to end with quotes as well)
    boolean startedWithQuotes = false;
    // values on the current line
    List<String> arr = new ArrayList<String>();
    // headers, set this to the first row
    List<String> headers = null;

    // while still more characters to parse
    while (input.hasNext()) {
      // advance current and next
      c = n;
      n = input.next().charAt(0);

      // once the end of the line has been reached
      if (c == '\n') {
        // set headers as the first line
        if (headers == null)
          headers = arr;
        // only parse normal lines (has 47 columns)
        else if (arr.size() == COLUMNS) {
          // add a new entry
          contents.add(new Entry(
                arr.get(0),
                arr.get(1),
                arr.get(2),
                headers.subList(3, headers.size()),
                arr.subList(3, arr.size())
                ));
        }
        // reset current line values array
        arr = new ArrayList<String>();
      // quotations could start or end values
      } else if (c == '"') {
        // if we are in a value, end it
        if (inValue) {
          inValue = false;
          arr.add(current);
        } else {
          // otherwise start a value
          inValue = true;
          startedWithQuotes = true;
          current = "";
        }
      // commas also start/end values if quotes aren't surrounding it
      } else if (c == ',') {
        // start of next value
        if (!inValue && n != '"' && n != ',') {
          inValue = true;
          startedWithQuotes = false;
          current = "";
        // end of current avlue without quotes
        } else if (!startedWithQuotes && inValue) {
          inValue = false;
          arr.add(current);

          // next value might also start here
          if (n != '"' && n != ',') {
            inValue = true;
            startedWithQuotes = false;
            current = "";
          }
        // add to current if this is just a comma in a quoted value
        } else if (inValue && startedWithQuotes) {
          current += c;
        // add nothing for 2 commas in a row
        } else if (n == ',') {
          arr.add("");
        }
      // add to current if in a value
      } else if (c != '\n') {
        current += c;
      }
    }
  }
}


/**
 * Entry of the csv file (a single row)
 */
class Entry {
  private Map<String, Double> content;
  private String fips;
  private String state;
  private String areaName;

  /**
   * Create an entry given the fips code, state, area name, list of headers
   * (excluding fips, state, and area), and a list of values corresponding
   * to the headers
   */
  public Entry(String fips, String state, String areaName, List<String> headers, List<String> values) {
    content = new HashMap<String, Double>();
    this.fips = fips;
    this.state = state;
    this.areaName = areaName;

    // add the headers to the map
    for (int i = 0; i < headers.size(); i++) {
      try {
        // parse double, removing commas
        content.put(headers.get(i), Double.parseDouble(values.get(i).replaceAll(",", "")));
      } catch (NumberFormatException e) {
        // handle none-numbers
        System.err.println("Not a number! " + values.get(i));
      }
    }
  }

  /** Getters **/

  public String getFips() { return fips; }
  public String getState() { return state; }
  public String getAreaName() { return areaName; }

  /**
   * Get the value given the header key
   * @param key Header string to lookup value
   * @return    Value given the header
   */
  public double get(String key) {
    return content.get(key);
  }

  /**
   * Convert the entry to a string (for debuggin)
   */
  public String toString() {
    String areaName = this.areaName.length() > 17 ? this.areaName.substring(0, 17) + "..." : this.areaName;
    return String.format("%5s | %2s | %-20s", fips, state, areaName);
  }
}
