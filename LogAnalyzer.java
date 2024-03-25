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
    // Where to calculate the weekly access counts 
    private int[] dailyCounts;
    // Where to calculate the weekly access counts
    private int[] weeklyCounts;
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
        // Create the array object to hold the daily
        // access counts.
        dailyCounts = new int[366];
        // Create the array object to hold the weekly
        // access distribution.
        weeklyCounts = new int[7];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }
    
    /**
     * The main program generates, analyzes, and reports on web access logs. 
     * It creates a log file with simulated web access entries, analyzes the hourly distribution 
     * of web accesses, prints the hourly access counts, and displays the total number of accesses. 
     * @param args The command-line arguments for the program. 
     */
    public static void main(String[] args) 
    {
        LogfileCreator creator = new LogfileCreator();
        creator.createFile("entries.txt", 8);

        LogAnalyzer analyzer = new LogAnalyzer("entries.txt");
        analyzer.analyzeHourlyData();
        analyzer.printHourlyCounts(); 
        System.out.println(analyzer.numberOfAccesses());
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
     * Exercise 7.14 - Add the numberOfAccesses method to your LogAnalyzer. 
     * you can test it by opening the log file in Notepad++ and seeing the number of lines 
     * in the file
     * Return total The number of accesses recorded in the log file.
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
    
    /**
     * Exercise 7.15 Add a method busiestHour to LogAnalyzer that returns the busiest hour.
     * Find the busiest hour.
     * @return busiestHour The busiest hour.
     */
    public int busiestHour() 
    {
        int maxCount = 0;
        int busiestHour = 0;
        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] > maxCount) {
                busiestHour = i; 
                maxCount = hourCounts[i];
            }
        return busiestHour;
    }
    
    /**
     * Exercise 7.16 Add a method quietestHour to LogAnalyzer that 
     * returns the number of the least?busy hour􏰃 
     * Find the quietest hour.
     * @return quietestHour The quietest hour.
     */
    public int quietestHour() 
    {
        int minCount = numberOfAccesses();
        int quietestHour = 0;
        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] < minCount) {
                quietestHour = i;
                minCount = hourCounts[i];
            }
        return quietestHour;
    }
    
    /**
     * Exercise 7.18 Add a method to LogAnalyzer that finds which two-hour period is the busiest.
     * Find the busiest two hours.
     * @return busiestTwoHours The first of the busiest two hours.
     */
    public int busiestTwoHours() {
        int maxCount = 0;
        int firstOfBusiestHourPair = 0;
        for(int i = 0; i < hourCounts.length/2; i++) {
            int hourPair = hourCounts[i * 2] + hourCounts[i * 2 + 1];
            if (hourPair > maxCount) {
                firstOfBusiestHourPair = i;
            }
        }
        return firstOfBusiestHourPair;
    }
    
    /**
     * Analyze the daily access data from the log file.
     */
    public void analyzeDailyData()
    { 
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dailyCounts[day]++;
        }
    }
    
    /**
     * Analyze the 7-day access patterns(starting from the 1st of January).
     * @return weeklyCounts The array has 7 elements, each for a day of the week. 
     */
    public int[] analyzeWeeklyPatterns()
    {
        for(int i = 0; i < 52; i++){ 
            for(int j = 0; j < 7; j++) {
                weeklyCounts[j] += dailyCounts[i * 7 + j];
            }
        }

        weeklyCounts[0] += dailyCounts[364];
        weeklyCounts[1] += dailyCounts[365];
        return weeklyCounts;
    }
    
    /**
     * Analyze the quietest day in a 7-day cycle.
     * @return quietestDay The quietest day.
     */
    public int quietestDay() {
        int minCount = numberOfAccesses();
        int quietestDay = 0;
        for(int i = 0; i < weeklyCounts.length; i++)
            if(hourCounts[i] < minCount) {
                quietestDay = i;
                minCount = weeklyCounts[i];
            }
        return quietestDay;
    }
    
    /**
     * Analyze the busiest day in a 7-day cycle.
     * @return busiestDay The busiest day.
     */
    public int busiestDay() {
        int maxCount = 0;
        int busiestDay = 0;
        for(int i = 0; i < weeklyCounts.length; i++)
            if(weeklyCounts[i] > maxCount)
                busiestDay = i;
        return busiestDay;
    }
}
