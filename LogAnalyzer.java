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
    // Where to calculate the daily access counts 
    private int[] dailyCounts;
    // Where to calculate the weekly access counts
    private int[] weeklyCounts;
    // Where to calculate the monthly access counts
    private int[] monthlyCounts;
    // Where to calculate the yearly access counts
    private int[] yearlyCounts;
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
        // Create the array object to hold the monthly
        // access counts.
        monthlyCounts = new int[12];
        // Create the array object to hold the yearly
        // access counts.
        yearlyCounts = new int[20];
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
     * @return highestHourStart The busiest hour pair
     */
    public int busiestTwoHour()
    {
        //Two variables to compare highest value to
        int highestCount = hourCounts[0] + hourCounts[1];
        int highestHourStart = 0;
        int highestHourEnd = 0;
        int checkCount;
        
        //For loop to check every index
        for (int index = 0; index < hourCounts.length; index++) {
            if (index == 23) {
                checkCount = hourCounts[index] + hourCounts[0];
            } else {
                //current index value is added with its next index to compare its score
                checkCount = hourCounts[index] + hourCounts[index + 1];
            }
                //Compares the value of the hour to the newest highest count
                if (checkCount > highestCount) {
                    //If the new highest count beats the old one, replace all info with its.
                    highestCount = checkCount;
                    highestHourStart = index;
                    highestHourEnd = index + 1;
                }
        }
        
        //After the array has been checked, return the highest hour
        System.out.println("The highest two hour period: " + highestHourStart 
                            + " - " + highestHourEnd + " with " 
                            + highestCount + " visitors.");
        return highestHourStart;
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
     * Analyze the monthly access data from the log file.
     */
    public void analyzeMonthlyData() {
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth() ;
            monthlyCounts[month-1]++;
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
    public int quietestDay() 
    {
        if (weeklyCounts.length == 0) {
        return -1;
        }

        int minCount = weeklyCounts[0];
        int quietestDay = 0;

        for (int i = 1; i < weeklyCounts.length; i++) {
            if (weeklyCounts[i] < minCount) {
                quietestDay = i;
                minCount = weeklyCounts[i];
            }
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
        for(int i = 0; i < weeklyCounts.length; i++){
            if(weeklyCounts[i] > maxCount){
                busiestDay = i;
                maxCount = weeklyCounts[i];
            }
        }
        
        return busiestDay;
    }
    
    /**
     * Calculates and returns the total number of web accesses for each month, 
     * assuming each month has exactly 28 days. 
     * @return monthlyAccesses An array of integers where each element 
     * represents the total number of web accesses for a month. 
     */
    public int[] totalAccessesPerMonth() 
    {
        int[] monthlyAccesses = new int[12]; // 12 months in a year
        int daysInMonth = 28; // Assuming each month has 28 days

        for (int i = 0; i < monthlyAccesses.length; i++) {
            monthlyAccesses[i] = 0;
        }

        // Accumulate total accesses for each "28-day month"
        for (int month = 0; month < 12; month++) {
            for (int day = 0; day < daysInMonth; day++) {
                int dayOfYear = month * daysInMonth + day;
                // Ensure we don't access an index beyond the available dailyCounts.
                if (dayOfYear < dailyCounts.length) {
                    monthlyAccesses[month] += dailyCounts[dayOfYear];
                }
            }
        }

        return monthlyAccesses;
    
    }
}
