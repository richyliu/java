import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class BigData {
  // number of columns in the csv
  private static final int COLUMNS = 47;

  private Scanner input;
  // entries in the csv file
  private List<Entry> contents;
  // usa has separate data
  private Entry usa;
  private List<String> headers;

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
    System.out.printf("Database loaded. Number of entries: %5d\n", contents.size());

    searchSystem();
  }

  public void searchSystem() {
    boolean quit = false;

    System.out.println("Welcome to the search system for Education.csv");
    System.out.println("This program will print a histogram of the data header that you select\n\n");

    while (!quit) {
      for (int i = 0; i < this.headers.size(); i++)
        System.out.printf("[%2d]: %s\n", i, this.headers.get(i));
      int headerIndex = -2;
      while (headerIndex == -2)
        headerIndex = Prompt.getInt("Select a header, or enter -1 to exit");
      if (headerIndex == -1)
        quit = true;
      else {
        Stats s = new Stats(this.headers.get(headerIndex), contents);
        System.out.println(s.histogram());
        System.out.println(s);
        Prompt.getString("Press enter to continue");
      }
    }
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
      for (String header : headers) {
        Double displayed = entry.get(header);
        if (displayed != null)
          System.out.printf(" | " + header.substring(0, 15) + "...: %10.2f", displayed);
        else
          System.out.printf(" | " + header.substring(0, 15) + "...: %10s", "N/A");
      }
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
    headers = null;

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
        // only parse normal lines (has 47 columns) and it has something for fips code
        else if (arr.size() == COLUMNS && !arr.get(0).equals("")) {
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
        }
        if (n == ',') {
          arr.add("");
        }
      // add to current if in a value
      } else if (c != '\n') {
        current += c;
      }
    }

    // keep usa data separate
    this.usa = contents.remove(0);
    // remove first 3 headers
    this.headers = this.headers.subList(3, this.headers.size());
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
        if (values.get(i).equals(""))
          content.put(headers.get(i), null);
        else
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
  public Double get(String key) {
    return content.get(key);
  }

  /**
   * Convert the entry to a string (for debuggin)
   */
  public String toString() {
    String areaName = this.areaName.length() > 17 ? this.areaName.substring(0, 17) + "..." : this.areaName;
    return String.format("%5s | %2s | %-20s", fips, state, areaName);
  }

  /**
   * Equal entry fips, state, area, and all headers
   * @param otherIn The other entry to compare
   * @return        Whether the other city is equal tho this
   */
  public boolean equals(Object otherIn) {
    if (otherIn != null && otherIn instanceof Entry) {
      Entry other = (Entry)otherIn;

      if (this.fips != other.getFips()) return false;
      if (this.state != other.getState()) return false;
      if (this.areaName != other.getAreaName()) return false;

      // loop through all entries
      for (Map.Entry<String, Double> entry : content.entrySet()) {
        // check that both are equal, otherwise return false
        if (!entry.getValue().equals(other.get(entry.getKey()))) return false;
      }
    }

    // all entries equal
    return true;
  }
}


/**
 * Contains statisticall information about all the info of a header, like
 * average, range, etc.
 */
class Stats {
  // histogram settings
  public static final int NUM_BINS = 50;
  public static final int HEIGHT = 15;

  private String header;
  private double average;
  private double low;
  private double high;
  private double median;
  private double range;

  private List<Double> nums;

  public Stats(String header, List<Entry> contents) {
    this.header = header;

    this.nums = new ArrayList<Double>();
    for (Entry entry : contents) {
      Double n = entry.get(header);
      if (n != null && n == 4932459) System.out.println(entry);
      if (n != null) nums.add(n);
    }
    Collections.sort(nums);

    this.low = nums.get(0);
    this.high = nums.get(nums.size()-1);
    this.median = nums.get(nums.size()/2);
    this.range = this.high - this.low;
    
    double total = 0;
    for (double n : nums)
      total += n;
    this.average = total/nums.size();
  }

  /** Getters */
  public double getAverage() { return average; }
  public double getLow() { return low; }
  public double getHigh() { return high; }
  public double getMedian() { return median; }
  public double getRange() { return range; }

  public String histogram() {
    // height of each bin of the histogram
    double[] bins = new double[NUM_BINS];
    // tallest bin
    double max = 0;
    
    // loop through numbers, adding to the apporpriate bin
    for (double n : this.nums) {
      // add 0.01 because this.range is inclusive of the highest
      int bin = (int)((n-this.low)/(double)(this.range+0.01) * NUM_BINS);
      bins[bin]++;
    }
    // get the max
    for (double bin : bins)
      max = Math.max(max, bin);

    // the number of characters high each bin of the histogram is
    int[] printedBins = new int [NUM_BINS];
    for (int i = 0; i < NUM_BINS; i++)
      printedBins[i] = (int)(bins[i]/max * HEIGHT);

    // generate the actual histogram

    // header
    String str = "\n\nFrequencies    " + this.header + "\n             ┏";
    for (int i = 0; i < NUM_BINS; i++)
      str += "-";
    str += "┓\n";

    // content
    for (int i = HEIGHT; i >= 0; i--) {
      if (i % 3 == 0)
        str += String.format("%12.2f |", (double)i/HEIGHT * max);
      else
        str += "             |";
      for (int bin : printedBins) {
        // print special char for "empty" bins
        if (bin == 0 && i == 0) str += '.';
        else str += bin > i ? '@' : ' ';
      }
      str += "|\n";
    }
    
    // footer
    str += "             └";
    for (int i = 0; i < NUM_BINS; i++)
      str += i % 10 == 0 ? "|" : "-";
    str += "┛\n              ";
    for (int i = 0; i < NUM_BINS; i++)
      if (i%10 == 0)
        str += String.format("%-10.0f", (double)i/NUM_BINS * this.range + this.low);
    str += "\n";

    return str;
  }

  public String toString() {
    return String.format("Average: %-10.2f Low: %-10.2f High: %-10.2f Median: %-10.2f Range: %-10.2f", average, low, high, median, range);
  }
}
