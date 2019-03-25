import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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
    // System.out.print("Loading databases...");
    // readFile();
    // writeFile();
    // processTuitionFile();
    // convertFipsToZip();
    // convertZipHighSchoolers();
    // averageTuitions();
    // combineData();
    zipToLatLong();
    // System.out.printf("\rDatabase loaded. Number of entries: %5d\n", contents.size());

    // searchSystem();
    // System.out.println("\nThank you for using Education.csv search system.");
  }


  /** Main search runner */
  public void searchSystem() {
    boolean quit = false;

    System.out.println("\nWelcome to the search system for Education.csv");
    System.out.println("Usage:\n\tPress s for scatter plot mode\n\tPress h for histogram mode\n\tPress q to quit\n");

    while (!quit) {
      // get input option
      String input = "";
      while (input.length() == 0)
        input = Prompt.getString("s = scatter plot, h = histogram, q = quit");

      if (input.equals("q"))
        quit = true;
      else if (input.equals("h"))
        searchHistogram();
      else if (input.equals("s"))
        searchScatter();
    }
  }


  /** Prompt the user for histogram display */
  public boolean searchHistogram() {
    // print header
    for (int i = 0; i < this.headers.size(); i++)
      System.out.printf("[%2d]: %s\n", i+1, this.headers.get(i));

    int headerIndex = -1;
    while (headerIndex == -1)
      headerIndex = Prompt.getInt("[histog] -- Select a header, or enter 0 to exit histogram mode");
    if (headerIndex == 0)
      return true;

    Stats s = new Stats(this.headers.get(headerIndex - 1), contents);
    System.out.println(s.histogram());
    System.out.println(s);
    Prompt.getString("[histog] -- Press enter to continue");

    return searchHistogram();
  }


  /** Prompt the user for scatter plot display */
  public boolean searchScatter() {
    // print header
    for (int i = 0; i < this.headers.size(); i++)
      System.out.printf("[%2d]: %s\n", i+1, this.headers.get(i));

    int[] headersIndex = new int[]{-1, -1};
    while (headersIndex[0] == -1)
      headersIndex[0] = Prompt.getInt("[scatter] -- Select a header for the y axis, or enter 0 to exit scatter plot mode");
    if (headersIndex[0] == 0)
      return true;
    while (headersIndex[1] == -1)
      headersIndex[1] = Prompt.getInt("[scatter] -- Select a header for the x axis, or enter 0 to exit scatter plot mode");
    if (headersIndex[1] == 0)
      return true;

    System.out.println("\n");
    System.out.println(Stats.scatter(this.headers.get(headersIndex[0] - 1), this.headers.get(headersIndex[1] - 1), contents));
    Prompt.getString("[scatter] -- Press enter to continue");

    return searchScatter();
  }


  /**
   * Prints a table with the given headers using a random subset of the contents
   * @param headers Given list of headers for the table
   */
  public static void printTable(List<String> headers, List<Entry> contents) {
    int len = 50;
    int start = (int)(Math.random() * (contents.size() - len));

    System.out.println("---------------------------------------------------------------------------------------------------------");
    for (Entry entry : contents.subList(start, start + len)) {
      System.out.print(entry);
      for (String header : headers) {
        Double displayed = entry.get(header);
        if (displayed == null)
          displayed = 0.0;
        System.out.printf(" | " + header.substring(0, Math.min(15, header.length())) + ": %10.2f", displayed);
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


  /**
   * Process the college tuitions file, writing the results back out linearly
   * (not loading the whole file into memory)
   */
  public void processTuitionFile() {
    System.out.println();

    try {
      // read in input using FileReader for better performance
      FileReader fr = new FileReader("assets/CollegeScorecard_Raw_Data/MERGED2016_17_PP.csv");
      // output writers
      PrintWriter pr = new PrintWriter("assets/tuition2016.csv");
      BufferedReader br = new BufferedReader(fr);

      // tokenize the line by commas
      StringTokenizer st = new StringTokenizer(br.readLine(), ",");
      // headers are in the first line
      List<String> headers2 = new ArrayList<String>();
      while (st.hasMoreTokens())
        headers2.add(st.nextToken());

      // print headers onto the output file
      pr.println("ZIP,COST");

      // current line split by commas
      List<String> cur = new ArrayList<String>();
      // limit the number of iterations to 10k to prevent crashing
      int num = 0;
      // current entry
      Entry entry = null;
      // current line
      String line = "";

      // get the current line, and do this while there _is_ a current line
      while (((line = br.readLine()) != null) && num < 10000) {
        // tokenize the line, adding to cur
        cur = new ArrayList<String>();
        st = new StringTokenizer(line, ",");
        while (st.hasMoreTokens())
          cur.add(st.nextToken());
        // add to entry, using the putting the ZIP code in the areaName
        entry = new Entry("", "", cur.get(3), headers2, cur);

        // write either the academic year costs or the program year costs
        Double costa = entry.get("COSTT4_A");
        if (costa == null) {
          costa = entry.get("COSTT4_P");
          if (costa != null) {
            pr.print(cur.get(6) + ",");
            pr.println(costa);
          }
        } else {
          pr.print(cur.get(6) + ",");
          pr.println(costa);
        }

        num++;
        // print debug statements to show progress
        if (num % 200 == 0)
          System.out.print("\rProcessed: " + num);
      }

      // close resources
      br.close();
      pr.close();
    } catch (IOException e) {
      // catch in case file isn't found
      System.out.println("File not found");
    }
  }


  /** Write the parsed results of Education.csv */
  public void writeFile() {
    try {
      // write headers
      PrintWriter pr = new PrintWriter("assets/output2.csv");
      pr.println("FIPS,PERCENT_DIPLOMA_2012-2016");

      // write a line with the number of people with high school diplomas from 2013-2016
      for (Entry entry : this.contents) {
        pr.println(entry.getFips() + "," + entry.get("Percent of adults with a high school diploma only, 2013-2016"));
      }

      // close resources
      pr.close();
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }


  /** Convert fips to zips for unemployment and household income data */
  public void convertFipsToZip() {
    try {
      PrintWriter pr = new PrintWriter("assets/unempInZip.csv");
      // use FileReader for better performance
      FileReader zipConverterFile = new FileReader("assets/fipszip.csv");
      BufferedReader zipConverter = new BufferedReader(zipConverterFile);
      FileReader unemploymentFile = new FileReader("assets/unemploymentAndIncome.csv");
      BufferedReader unemployment = new BufferedReader(unemploymentFile);

      // make a hashmap from county code to zip(s)
      Map<String, Set<String>> zips = new HashMap<String, Set<String>>();
      String line = "";
      zipConverter.readLine();

      // get the line
      while ((line = zipConverter.readLine()) != null) {
        // first index is fips, second is zip
        String[] parts = line.split(",");
        // start a new set if not already have
        if (!zips.containsKey(parts[0]))
          zips.put(parts[0], new HashSet<String>());
        // add it to the set
        zips.get(parts[0]).add(parts[1]);
      }
      zipConverter.close();

      // convert unemployment from fips to zip
      line = "";
      unemployment.readLine();

      // get the line
      while ((line = unemployment.readLine()) != null) {
        // add ",a" to prevent weird split bug
        String[] parts = (line + ",a").split(",");
        // get the set of zips for the current fips code
        Set<String> zip = zips.get(parts[0]);
        if (zip != null && parts[1].length() > 0 && parts[2].length() > 0) {
          // print for each zip code
          for (String z : zip)
            pr.println(z + "," + parts[1] + "," + parts[2]);
        }
      }

      unemployment.close();
      pr.close();
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }


  /** Convert fips to zips for high schoolers data */
  public void convertZipHighSchoolers() {
    try {
      PrintWriter pr = new PrintWriter("assets/zipHighSchoolers.csv");
      // use FileReader for better performance
      FileReader zipConverterFile = new FileReader("assets/fipszip.csv");
      BufferedReader zipConverter = new BufferedReader(zipConverterFile);
      FileReader highSchoolersFile = new FileReader("assets/highschoolers.csv");
      BufferedReader highSchoolers = new BufferedReader(highSchoolersFile);

      // make a hashmap from county code to zip(s)
      Map<String, Set<String>> zips = new HashMap<String, Set<String>>();
      String line = "";
      zipConverter.readLine();

      // get the line
      while ((line = zipConverter.readLine()) != null) {
        // first index is fips, second is zip
        String[] parts = line.split(",");
        // start a new set if not already have
        if (!zips.containsKey(parts[0]))
          zips.put(parts[0], new HashSet<String>());
        // add it to the set
        zips.get(parts[0]).add(parts[1]);
      }
      zipConverter.close();

      // convert unemployment from fips to zip
      line = "";
      highSchoolers.readLine();

      pr.println("ZIP,ONLY_HIGHSCHOOL_PERCENTAGE");
      // get the line
      while ((line = highSchoolers.readLine()) != null) {
        // add ",a" to prevent weird split bug
        String[] parts = (line + ",a").split(",");
        // get the set of zips for the current fips code
        Set<String> zip = zips.get(parts[0]);
        if (zip != null && parts[1].length() > 0) {
          // print for each zip code
          for (String z : zip)
            pr.println(z + "," + parts[1]);
        }
      }

      highSchoolers.close();
      pr.close();
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }


  /** Average the tuitons from 2013-2016 */
  public void averageTuitions() {
    try {
      PrintWriter pr = new PrintWriter("assets/averageTuition.csv");

      // map between zip code and the (tuition, number of times it's been added)
      Map<String, Pair<Double>> tuitions = new HashMap<String, Pair<Double>>();

      // for each year 2013..2016
      for (int i = 3; i <= 6; i++) {
        // use FileReader for better performance
        FileReader tuitionFile = new FileReader("assets/tuition201" + i + ".csv");
        BufferedReader tuitionReader = new BufferedReader(tuitionFile);

        String line;
        // no need for headers
        tuitionReader.readLine();

        // get the line
        while ((line = tuitionReader.readLine()) != null) {
          // first index is zip, second is tuition fee
          String[] parts = line.split(",");
          Double fee = 0.0;
          try {
            fee = Double.parseDouble(parts[1]);
          } catch (NumberFormatException e) {
            System.out.println("not a number: " + parts[1]);
          }
          // start from zero
          if (!tuitions.containsKey(parts[0]))
            tuitions.put(parts[0], new Pair<Double>(0.0, 0.0));
          // add tuition to previous tuition of zip
          Pair<Double> t = tuitions.get(parts[0]);
          tuitions.put(parts[0], new Pair<Double>(t.x + fee, t.y + 1.0));
        }

        tuitionReader.close();
        tuitionFile.close();
      }

      // print to output the zip code tuition averages
      pr.println("ZIP,TUITION");
      for (String key : tuitions.keySet()) {
        Pair<Double> t = tuitions.get(key);
        // print the zip codes and the averages
        pr.printf("%s,%.3f\n", key, (t.x/t.y));
      }

      pr.close();
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }


  /**
   * Combine the different data parts together (tuition, percentage highschool
   * completion, unemployment, and average household income)
   */
  public void combineData() {
    try {
      PrintWriter pr = new PrintWriter("assets/combined.csv");

      // use FileReader for better performance
      FileReader tuitionFile = new FileReader("assets/averageTuition.csv");
      BufferedReader tuitionReader = new BufferedReader(tuitionFile);

      FileReader highSchoolersFile = new FileReader("assets/zipHighSchoolers.csv");
      BufferedReader highSchoolers = new BufferedReader(highSchoolersFile);

      FileReader unemploymentFile = new FileReader("assets/unempInZip.csv");
      BufferedReader unemployment = new BufferedReader(unemploymentFile);

      // map between zip code and the headers to contents
      Map<String, Map<String, String>> output = new HashMap<String, Map<String, String>>();

      String line;
      // no need for headers
      tuitionReader.readLine();
      highSchoolers.readLine();
      unemployment.readLine();

      // read the tuition information
      while ((line = tuitionReader.readLine()) != null) {
        // first index is zip, second is tuition fee
        String[] parts = line.split(",");
        if (!output.containsKey(parts[0]))
          output.put(parts[0], new HashMap<String, String>());
        // add tuition to output
        output.get(parts[0]).put("TUITION", parts[1]);
      }

      // read the highSchoolers information
      while ((line = highSchoolers.readLine()) != null) {
        // first index is zip, second is high school completion only percentage
        String[] parts = line.split(",");
        if (!output.containsKey(parts[0]))
          output.put(parts[0], new HashMap<String, String>());
        // add highSchoolers to output
        output.get(parts[0]).put("HIGHSCHOOLERS", parts[1]);
      }

      // read the unemployment information
      while ((line = unemployment.readLine()) != null) {
        // first index is zip, second is unemployment percentage and household income
        String[] parts = line.split(",");
        if (!output.containsKey(parts[0]))
          output.put(parts[0], new HashMap<String, String>());
        // add unemployment to output
        output.get(parts[0]).put("UNEMPLOYMENT", parts[1]);
        output.get(parts[0]).put("INCOME", parts[2]);
      }


      tuitionReader.close();
      tuitionFile.close();
      highSchoolersFile.close();
      highSchoolers.close();
      unemploymentFile.close();
      unemployment.close();

      // print to output the zip code tuition averages
      pr.println("ZIP,TUITION,HIGHSCHOOLERS,UNEMPLOYMENT,INCOME");
      for (String zip : output.keySet()) {
        String[] data = {
          output.get(zip).get("TUITION"),
          output.get(zip).get("HIGHSCHOOLERS"),
          output.get(zip).get("UNEMPLOYMENT"),
          output.get(zip).get("INCOME")
        };
        if (data[0] == null || data[1] == null || data[2] == null || data[3] == null)
          continue;

        // print the zip codes and the data
        pr.printf("%s,%s,%s,%s,%s\n",
            zip,
            data[0],
            data[1],
            data[2],
            data[3]
            );
      }

      pr.close();
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }


  public void zipToLatLong() {
    try {
      PrintWriter pr = new PrintWriter("assets/inLatLong.csv");

      // use FileReader for better performance
      FileReader latLongFile = new FileReader("assets/coordzip.csv");
      BufferedReader latLongReader = new BufferedReader(latLongFile);
      FileReader combinedFile = new FileReader("assets/combined.csv");
      BufferedReader combined = new BufferedReader(combinedFile);

      // map between zip code and lat long (coordinates)
      Map<String, Pair<String>> latLong = new HashMap<String, Pair<String>>();

      String line;
      // no need for headers
      latLongReader.readLine();
      combined.readLine();
      pr.println("LAT,LONG,TUITION,HIGHSCHOOLERS,UNEMPLOYMENT,INCOME");

      // read the lat long information
      while ((line = latLongReader.readLine()) != null) {
        // first index is zip, second is tuition lat, third is long
        String[] parts = line.split(",");
        // add lat long
        latLong.put(parts[0], new Pair<String>(parts[1], parts[2]));
      }

      // read the combined information
      while ((line = combined.readLine()) != null) {
        // first index is zip, second+ is data
        String zip = line.substring(0, line.indexOf(','));
        String data = line.substring(line.indexOf(',') + 1);
        // convert to lat long, write to file
        Pair coord = latLong.get(zip);
        if (coord != null)
          pr.printf("%s,%s,%s\n", coord.x, coord.y, data);
        else
          System.out.println("got null for zip: " + zip);
      }

      latLongReader.close();
      combined.close();
      pr.close();
    } catch (IOException e) {
      System.out.println("File not found");
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
        if (values.get(i).equals(""))
          content.put(headers.get(i), null);
        else
          // parse double, removing commas
          content.put(headers.get(i), Double.parseDouble(values.get(i).replaceAll(",", "")));
      } catch (NumberFormatException e) {
        // handle none-numbers
        // System.err.println("Not a number! " + values.get(i));
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
  public static final int HIST_HEIGHT = 15;
  // scatter plot settings
  public static final int SCATTER_HEIGHT = 20;
  public static final int SCATTER_WIDTH = 60;

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
      printedBins[i] = (int)(bins[i]/max * HIST_HEIGHT);

    // generate the actual histogram

    // header
    String str = "\n\nFrequencies    " + this.header + "\n             ┏";
    for (int i = 0; i < NUM_BINS; i++)
      str += "-";
    str += "┓\n";

    // content
    for (int i = HIST_HEIGHT; i >= 0; i--) {
      if (i % 3 == 0)
        str += String.format("%12.2f |", (double)i/HIST_HEIGHT * max);
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


  public static String scatter(String header1, String header2, List<Entry> contents) {
    Stats data1 = new Stats(header1, contents);
    Stats data2 = new Stats(header2, contents);

    List<Pair<Double>> data = new ArrayList<Pair<Double>>();
    for (Entry entry : contents) {
      Double a = entry.get(header1);
      Double b = entry.get(header2);
      if (a != null && b != null)
        data.add(new Pair<Double>(a, b));
    }

    int[][] scatter = new int[SCATTER_HEIGHT][SCATTER_WIDTH];
    for (Pair<Double> p : data) {
      int y = (int)((p.x - data1.getLow())/(double)(data1.getRange() + 0.01) * SCATTER_HEIGHT);
      y = SCATTER_HEIGHT - y - 1;
      int x = (int)((p.y - data2.getLow())/(double)(data2.getRange() + 0.01) * SCATTER_WIDTH);
      scatter[y][x]++;
    }

    int max = 0;
    for (int[] row : scatter)
      for (int square : row)
        max = Math.max(max, square);

    String str = "";

    // header
    str += header1;
    str += "\n                        vs\n";
    str += header2;
    str += "\n";
    for (int i = 0; i < SCATTER_WIDTH+2; i++)
      str += "-";
    str += "\n";

    // content
    for (int i = 0; i < SCATTER_HEIGHT; i++) {
      str += "|";
      for (int j = 0; j < SCATTER_WIDTH; j++) {
        int cur = scatter[i][j];
        if (cur > max*2/3)
          str += "@";
        else if (cur > max/3)
          str += "*";
        else if (cur > 0)
          str += ".";
        else
          str += " ";
      }
      str += "|\n";
    }

    // footer
    for (int i = 0; i < SCATTER_WIDTH+2; i++)
      str += "-";
    str += "\n";

    return str;
  }


  public String toString() {
    return String.format("Average: %-10.2f Low: %-10.2f High: %-10.2f Median: %-10.2f Range: %-10.2f", average, low, high, median, range);
  }
}


class Pair<E> {
  public E x;
  public E y;
  public Pair(E x, E y) {
    this.x = x;
    this.y = y;
  }
}
