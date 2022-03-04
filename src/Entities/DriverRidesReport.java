package Entities;

public class DriverRidesReport {
    private int numOfRides;
    private double numOfKm;
    private double duration;
    private double avrageKmPerRide;
    private double avrageDuration;
    private double freeTime;
    private double avrageSalary;
    private double salary;
    private Driver driver;

    public DriverRidesReport() {
        this.numOfRides = 0;
        this.numOfKm = 0;
        this.duration = 0;
        this.avrageKmPerRide = 0;
        this.avrageDuration = 0;
        this.freeTime = 0;
        this.avrageSalary = 0;
        this.salary = 0;
        this.driver = null;
    }

    public DriverRidesReport(int numOfRides, double numOfKm, double duration, double avrageKmPerRide, double avrageDuration, double freeTime, double avrageSalary, double salary,Driver driver) {
        this.numOfRides = numOfRides;
        this.numOfKm = numOfKm;
        this.duration = duration;
        this.avrageKmPerRide = avrageKmPerRide;
        this.avrageDuration = avrageDuration;
        this.freeTime = freeTime;
        this.avrageSalary = avrageSalary;
        this.salary = salary;
        this.driver = driver;
    }

    public int getNumOfRides() {
        return numOfRides;
    }

    public double getNumOfKm() {
        return numOfKm;
    }

    public double getDuration() {
        return duration;
    }

    public double getAvrageKmPerRide() {
        return avrageKmPerRide;
    }

    public double getAvrageDuration() {
        return avrageDuration;
    }

    public double getFreeTime() {
        return freeTime;
    }

    public double getAvrageSalary() {
        return avrageSalary;
    }

    public double getSalary() {
        return salary;
    }

    public Driver getDriver() {
        return driver;
    }
}
