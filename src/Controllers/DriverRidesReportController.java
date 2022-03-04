package Controllers;

import Entities.Driver;
import Entities.DriverRidesReport;
import Entities.Ride;
import List.LinkedList;
import Utils.*;

import java.util.GregorianCalendar;

public class DriverRidesReportController {

    private  static LinkedList<Ride> getYearlyRides(int year, Driver driver){
        LinkedList<Ride> rides = new LinkedList<Ride>();
        for (Ride r : driver.getDriverRides()){
            if (r.getDateAndTime().toZonedDateTime().getYear() == year){
                rides.add(r);
            }
        }
        return rides;
    }

    public static LinkedList<Ride> getMonthlyRides(String dateStr,Driver driver){
        GregorianCalendar date = DatesController.convertDateString(dateStr);
        LinkedList<Ride> rides = new LinkedList<Ride>();
        for (Ride r : driver.getDriverRides()){
            if (r.getDateAndTime().toZonedDateTime().getMonth().equals(date.toZonedDateTime().getMonth())
                    && r.getDateAndTime().toZonedDateTime().getYear() == date.toZonedDateTime().getYear()){
                rides.add(r);
            }
        }
        return rides;
    }

    public static LinkedList<Ride> getWeeklyRides(String dateStr,Driver driver){

        GregorianCalendar date = DatesController.convertDateString(dateStr);
        GregorianCalendar endDay = DatesController.convertDateString(dateStr);
        endDay.add(GregorianCalendar.DAY_OF_MONTH,7);

        LinkedList<Ride> rides = new LinkedList<Ride>();
        for (Ride r : driver.getDriverRides()){
            if (r.getDateAndTime().after(date) && r.getDateAndTime().before(endDay)){
                rides.add(r);
            }
        }
        return rides;
    }

    private static boolean isSameDay(GregorianCalendar date1 ,GregorianCalendar date2){
        if (date1.toZonedDateTime().getMonth().equals(date2.toZonedDateTime().getMonth()) &&
                date1.toZonedDateTime().getDayOfMonth() == date2.toZonedDateTime().getDayOfMonth() &&
                date1.toZonedDateTime().getYear() == date2.toZonedDateTime().getYear()){
            return true;
        }
        return false;
    }

    private static LinkedList<Ride> getDalyRides(String dateStr,Driver driver){
        GregorianCalendar date = DatesController.convertDateString(dateStr);
        LinkedList<Ride> rides = new LinkedList<Ride>();
        for (Ride r : driver.getDriverRides()){
            if (isSameDay(date,r.getDateAndTime())){
                rides.add(r);
            }
        }
        return rides;
    }

    private  static int calculateRidesNum(LinkedList<Ride> rides){
        return rides.size();
    }

    private static double calculateNumOfKm(LinkedList<Ride> rides){
        double numOfKm = 0;
        for (Ride r : rides){
            numOfKm+= r.getNumberOfKm();
        }
        return numOfKm;
    }

    private static double calculateDuration(LinkedList<Ride> rides){
        double duration = 0;
        for (Ride r : rides){
            duration += r.getDuration();
        }
        return duration;
    }

    private static double calculateAvrageKm(LinkedList<Ride> rides){
        if (rides.size() == 0){
            return 0;
        }
        return calculateNumOfKm(rides)/rides.size();
    }

    private static double calculateAvrageDuration(LinkedList<Ride> rides){
        if (rides.size() == 0){
            return 0;
        }
       return calculateDuration(rides)/rides.size();
    }

    public static double calculateSalary(LinkedList<Ride> rides){
        double salary = 0;
        for (Ride r : rides){
            salary += r.getPrice();
        }
        return salary;
    }

    private static double calculateAvrageSalary(LinkedList<Ride> rides){
        if (rides.size() == 0){
            return 0;
        }
        return calculateSalary(rides)/rides.size();
    }

    public static double calculateFreeTime(LinkedList<Ride> rides, int numOfDays){
        if (rides.size() == 0){
            return Constants.WORKING_TIME * numOfDays;
        }
        return Constants.WORKING_TIME * numOfDays - calculateDuration(rides);
    }


    public static DriverRidesReport initDalyReport(String date,Driver driver){
        LinkedList<Ride> rides = getDalyRides(date,driver);
        return new DriverRidesReport(calculateRidesNum(rides),calculateNumOfKm(rides),calculateDuration(rides),
                calculateAvrageKm(rides),calculateAvrageDuration(rides),calculateFreeTime(rides,1),
                calculateAvrageSalary(rides),calculateSalary(rides),driver);
    }

    public static DriverRidesReport initWeeklyRepot(String date,Driver driver){
        LinkedList<Ride> rides = getWeeklyRides(date,driver);
        return new DriverRidesReport(calculateRidesNum(rides),calculateNumOfKm(rides),calculateDuration(rides),
                calculateAvrageKm(rides),calculateAvrageDuration(rides),calculateFreeTime(rides,7),
                calculateAvrageSalary(rides),calculateSalary(rides),driver);

    }

    public static DriverRidesReport initMonthlyReport(String date,Driver driver){
        LinkedList<Ride> rides = getMonthlyRides(date,driver);
        int numOfDays = DatesController.convertDateString(date).getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return new DriverRidesReport(calculateRidesNum(rides),calculateNumOfKm(rides),calculateDuration(rides),
                calculateAvrageKm(rides),calculateAvrageDuration(rides),calculateFreeTime(rides,numOfDays),
                calculateAvrageSalary(rides),calculateSalary(rides),driver);
    }

    public static DriverRidesReport initYearlyReport(int year,Driver driver){
        LinkedList<Ride> rides = getYearlyRides(year,driver);
        int numOfDays = new GregorianCalendar(year, 1 ,1 ).getActualMaximum(GregorianCalendar.DAY_OF_YEAR);
        return new DriverRidesReport(calculateRidesNum(rides),calculateNumOfKm(rides),calculateDuration(rides),
                calculateAvrageKm(rides),calculateAvrageDuration(rides),calculateFreeTime(rides,numOfDays),
                calculateAvrageSalary(rides),calculateSalary(rides),driver);
    }

}
