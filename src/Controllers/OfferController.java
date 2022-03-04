package Controllers;

import Auction.*;
import Entities.Ride;
import List.LinkedList;

import java.util.GregorianCalendar;

public class OfferController {

    private static double DESTINATION_INCREMENT = 5; //in minutes
    private static double DESTINATION_MAX_POINT = 50;
    private static double FREE_TIME_INCREMENT = 840; //in minutes(about 2hrs per day)
    private  static double FREE_TIME_MAX_POINT_MULTIPLIER = 0.00595; // raounds up to 20(Out of 100 offer points)
    private static double SALARY_INCREMENT = 700; // in rsd
    private static double SALARY_MAX_POINTS = 25;

    public static void CreateNewOffer(LinkedList<Offer> offers, Offer offer){
        offers.add(offer);
    }

    private static double calculateDistancePoints(Offer offer){
        return recursiveCalculateDistancePoints(offer,0,DESTINATION_INCREMENT,DESTINATION_MAX_POINT);
    }

    private static double recursiveCalculateDistancePoints(Offer offer,double start ,double end,double retVal){
        if (offer.getDistance() >= start && offer.getDistance() < end){
            return retVal;
        }else{
            if(retVal > 0){
                return recursiveCalculateDistancePoints(offer,end,end+DESTINATION_INCREMENT,retVal-10);
            }
            return 0;
        }
    }

    private static int calculateFreeTimePoints(Offer offer){
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.DAY_OF_MONTH,-7);

        LinkedList<Ride> ridesToCalculate = DriverRidesReportController.getWeeklyRides(DatesController.dateToStr(date),offer.getDriver());
        double freeTime = DriverRidesReportController.calculateFreeTime(ridesToCalculate,7);

        return recursivCalculateFreeTimePoints(freeTime,FREE_TIME_INCREMENT*4,FREE_TIME_INCREMENT*3,FREE_TIME_MAX_POINT_MULTIPLIER);
    }

    private static int  recursivCalculateFreeTimePoints( double freeTime,double start ,double end,double retVal){
        if (freeTime <= start && freeTime > end){
            return  (int) Math.round(retVal * freeTime);
        }else{
            if(retVal > 0){
              return  recursivCalculateFreeTimePoints(freeTime,start,start - FREE_TIME_INCREMENT,(retVal/2));
            }
            return 0;
        }
    }


    private static double calculateSalaryPoints(Offer offer){
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.DAY_OF_MONTH,-7);

        LinkedList<Ride> ridesToCalculate = DriverRidesReportController.getWeeklyRides(DatesController.dateToStr(date),offer.getDriver());
        double salary = DriverRidesReportController.calculateSalary(ridesToCalculate);

        return recursiveCalculateSalaryPoints(salary,0,SALARY_INCREMENT,SALARY_MAX_POINTS);
    }

    private static double recursiveCalculateSalaryPoints(double salary, double start,double end, double retVal){
        if (salary >= start && salary < end){
            return retVal;
        }else{
            if(retVal > 0){
                return recursiveCalculateSalaryPoints(salary,end,end+SALARY_INCREMENT,retVal -2.5);
            }
            return 0;
        }
    }

    private static int calculateRatingPoints(Offer offer){
        return (int) (offer.getDriver().getRating());
    }

    public static void calculateOfferPoints(Offer offer){
        double points = calculateDistancePoints(offer) + calculateFreeTimePoints(offer) + calculateSalaryPoints(offer) + calculateRatingPoints(offer);
        offer.setPoints(points);
    }
}
