/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Fhaungfha Suvannakajorn
 * @version    2024.03.25
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     * Exercise 7.12 - modify the LogAnalyzer so that it can take a String, 
     * which is the name of a log file.
     * Constructs a LogAnalyzer object that processes web access logs from a specified file. 
     * It initializes an array to store access counts for each hour of the day 
     * and sets up a LogfileReader to read from the given file.
     * @param fileName The name of the log file to be analyzed. 
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Return the number of accesses recorded in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        // Add the value in each element of hourCounts to
        // total.
        for(int i = 0; i < hourCounts.length; i++) {
            total += hourCounts[i];
        }
        return total;
    }
}
