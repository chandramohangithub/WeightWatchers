package WWUITestAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import java.text.SimpleDateFormat;
import java.util.*;

public class WWUIAutomation {

    public static final String wwURL ="https:\\www.weightwatchers.com\\us\\";

    public static void main(String[] args) throws Exception{

        // chromedriver exist under the WeightWatchers Project folder
        // /Users/chandramohanboggaramgopinath/IdeaProjects/WeightWatchers/chromedriver
        System.setProperty("webdriver.chrome.driver","chromedriver");
        WebDriver  chromeDriver = new ChromeDriver();

        // Step1: Navigate to "https:\\www.weightwatchers.com\\us\\"
        chromeDriver.get(wwURL);

        // Step2: Verify loaded page title matches “WW (Weight Watchers): Weight Loss & Wellness Help”
        String expectedTitle = new String("WW (Weight Watchers): Weight Loss & Wellness Help");
        String actualTitle = chromeDriver.getTitle();

        if(actualTitle.contentEquals(expectedTitle)){
            System.out.println("ExpectedTitle matches with ActualTitle: " + actualTitle);
        }else{
            System.out.println("ExpectedTitle does not match with ActualTitle: " + actualTitle);
        }

        // Step3: On the right corner of the page, click on “Find a Studio”
        chromeDriver.findElement(By.id("ela-menu-visitor-desktop-supplementa_find-a-studio")).click();

        // Step4: Verify loaded page title contains “Find WW Studios & Meetings Near You | WW USA”
        String findStudioPageExpectedTitle = new String("Find WW Studios & Meetings Near You | WW USA");
        String findStudioPageActualTitle = chromeDriver.getTitle();

        if(findStudioPageActualTitle.contentEquals(findStudioPageExpectedTitle)){
            System.out.println("ActualTitle matches with the ExpectedTitle: " + findStudioPageActualTitle);
        }else{
            System.out.println("ActualTitle does not match with ExpectedTitle.  ActualTitle is " + findStudioPageActualTitle);
        }

        // Step5: In the search field, search for meetings for zip code: 10011
        String zipCode = "10011";
        chromeDriver.findElement(By.id("meetingSearch")).sendKeys(zipCode);
        chromeDriver.findElement(By.cssSelector("button.spice-translated")).click();

        // Step6: Print the title of the first result and the distance (located on the right of location title/name)
        List<WebElement> searchResultWebElementsList = chromeDriver.findElements(By.className("meeting-locations-list__item"));
        if(searchResultWebElementsList == null || searchResultWebElementsList.size()==0){
            throw new Exception("Searching for WeightWatches for the zipcode: " + zipCode + " did not return any result");
        }

        WebElement firstResultElement =searchResultWebElementsList.get(0);
        String firstResultTitle = firstResultElement.findElement(By.className("location__name")).getText();
        System.out.println("FirstResultTitle: " + firstResultTitle);
        String firstResultDistance = firstResultElement.findElement(By.className("location__distance")).getText();
        System.out.println("FirstResultDistance: " + firstResultDistance);

        // Step7: Click on the first search result and then, verify displayed location name/title matches with the name of the first searched result that was clicked.
        searchResultWebElementsList.get(0).click();
        String firstResultNavigationPageTitle = chromeDriver.findElement(By.className("location__name")).getText();
        System.out.println("FirstResultNavigationPageTitle: " + firstResultNavigationPageTitle);

        if(firstResultTitle.contentEquals(firstResultNavigationPageTitle)){
            System.out.println("firstResultTitle matches with the firstResultNavigationPageTitle: " + firstResultNavigationPageTitle);
        }else{
            System.out.println("firstResultTitle does not match with firstResultNavigationPageTitle: " + firstResultNavigationPageTitle);
        }

        // Step8: From this location page, print TODAY’s hours of operation (located towards the bottom of the page)
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        String todayDayOfTheWeek = simpleDateformat.format(new Date()).toUpperCase();

        List<WebElement> hoursOfOperationWebElementsList = chromeDriver.findElements(By.className("hours-list-item"));
        for(WebElement currWebElement: hoursOfOperationWebElementsList ){

            WebElement dayWebElement = currWebElement.findElement(By.className("hours-list-item-day"));
            WebElement hoursWebElement = currWebElement.findElement(By.className("hours-list-item-hours"));
            if(dayWebElement.getText().contentEquals(todayDayOfTheWeek)){
                System.out.println(dayWebElement.getText());
                System.out.println(hoursWebElement.getText());
            }
        }

        // Step9: Create a method to print the number of meeting the each person(under the scheduled time) has a particular day of the week
        List<WebElement> scheduleDayWebElementsList = chromeDriver.findElements(By.className("schedule-detailed-day"));

        for ( WebElement currWebElement:  scheduleDayWebElementsList ){

            String currWebElementLabel = currWebElement.findElement(By.className("schedule-detailed-day-label")).getText();
            Map<String, String> mapLeaderToTimeSlots = new HashMap<String, String>();

            if(studioScheduleDayMapping.containsKey(currWebElementLabel)){
                mapLeaderToTimeSlots = studioScheduleDayMapping.get(currWebElementLabel);
            }

            List<WebElement> currWebElementDayMeetings = currWebElement.findElements(By.className("schedule-detailed-day-meetings-item"));
            for(WebElement currWebElementDayMeeting: currWebElementDayMeetings ) {

                String leader = currWebElementDayMeeting.findElement(By.className("schedule-detailed-day-meetings-item-leader")).getText();
                String timeSlot = currWebElementDayMeeting.findElement(By.className("schedule-detailed-day-meetings-item-time")).getText();

                String currTimeSlots = new String();
                if (mapLeaderToTimeSlots.containsKey(leader)) {
                    currTimeSlots = mapLeaderToTimeSlots.get(leader);
                }

                StringBuilder updatedTimeSlots = new StringBuilder(currTimeSlots);
                if(updatedTimeSlots.length() > 0){
                    updatedTimeSlots.append(",");
                }
                updatedTimeSlots.append(timeSlot);


                mapLeaderToTimeSlots.put(leader, updatedTimeSlots.toString());
            }

            studioScheduleDayMapping.put(currWebElementLabel, mapLeaderToTimeSlots);
        }

        printMeetings("WED");

        chromeDriver.close();
        //System.exit();
    }

    public static Map<String, Map<String, String>> studioScheduleDayMapping = new HashMap<String, Map<String, String>>();

    public static void printMeetings(String dayOfTheWeek){

        Map<String, String> mapLeaderToTimeSlots = studioScheduleDayMapping.get(dayOfTheWeek);

        // using for-each loop for iteration over Map.entrySet()
        for (Map.Entry<String,String> entry : mapLeaderToTimeSlots.entrySet())
            System.out.println(entry.getKey() + " " +(entry.getValue().split(",")).length);

    }
}
