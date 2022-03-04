package Controllers;

import Entities.Driver;
import Entities.DriverRidesReport;
import List.LinkedList;
import Utils.LoggedIn;

public class DispatcherRidesReportController {

    public static LinkedList<DriverRidesReport> initDalyReport(String date){
        LinkedList<DriverRidesReport> allReports = new LinkedList<DriverRidesReport>();
        for (Driver d : LoggedIn.getService().getNotDeletedDrivers()){
                allReports.add(DriverRidesReportController.initDalyReport(date, d));
        }
        return allReports;
    }

    public static LinkedList<DriverRidesReport>  initWeeklyRepot(String date){
        LinkedList<DriverRidesReport> allReports = new LinkedList<DriverRidesReport>();
        for (Driver d : LoggedIn.getService().getNotDeletedDrivers()){
                allReports.add(DriverRidesReportController.initWeeklyRepot(date, d));
        }
        return allReports;
    }

    public static LinkedList<DriverRidesReport>  initMonthlyReport(String date){
        LinkedList<DriverRidesReport> allReports = new LinkedList<DriverRidesReport>();
        for (Driver d : LoggedIn.getService().getNotDeletedDrivers()){
                allReports.add(DriverRidesReportController.initMonthlyReport(date,d));
        }
        return allReports;
    }

    public static LinkedList<DriverRidesReport>  initYearlyReport(int year){
        LinkedList<DriverRidesReport> allReports = new LinkedList<DriverRidesReport>();
        for (Driver d : LoggedIn.getService().getNotDeletedDrivers()){
                allReports.add(DriverRidesReportController.initYearlyReport(year, d));
        }
        return allReports;
    }
}
