package Controllers;

import Entities.*;
import Enums.Department;
import Enums.Sex;
import List.LinkedList;
import Repositories.UsersFileRepository;
import Utils.LoggedIn;

import java.util.GregorianCalendar;

public class UsersController {

    public static void addNewUser(LinkedList<Person> users, User newUser){
        users.add(newUser);
    }

    public static void addNewDispatcher(LinkedList<Person> users, Dispatcher newUser){
        users.add(newUser);
    }

    public static void addNewDriver(LinkedList<Person> users, Driver newUser){
        users.add(newUser);
    }

    public static void updateUser(User target, String name, String surname, Sex sex, String address, String phoneNumber, String username, String password){
        target.setName(name);
        target.setSurname(surname);
        target.setSex(sex);
        target.setAddress(address);
        target.setPhoneNumber(phoneNumber);
        target.setUsername(username);
        target.setPassword(password);
    }

    public static void updateDispatcher(Dispatcher target, String name, String surname, Sex sex, String address, String phoneNumber, String username, String password,int salary, int lineNumber, Department department){
        target.setName(name);
        target.setSurname(surname);
        target.setSex(sex);
        target.setAddress(address);
        target.setPhoneNumber(phoneNumber);
        target.setUsername(username);
        target.setPassword(password);
        target.setDepartment(department);
        target.setLineNumber(lineNumber);
        target.setSalary(salary);
    }


    public static void updateDriver(Driver target, String name, String surname, Sex sex, String address, String phoneNumber, String username, String password,int salary){
        target.setName(name);
        target.setSurname(surname);
        target.setSex(sex);
        target.setAddress(address);
        target.setPhoneNumber(phoneNumber);
        target.setUsername(username);
        target.setPassword(password);
        target.setSalary(salary);
    }


    public static void addCarToDriver(Driver testvozac, Vehicle car) {
        testvozac.setVehicle(car);
        car.setDriver(testvozac.getUsername());
    }

    public static void  calculateDriverRating(Driver driver){
        int rating;
        int sum = 0;
        for (Ride r : RideController.getFinishedRides(driver)){
            if (r == null){
                rating = 0;
                driver.setRating(rating);
                return;
            }
                sum += r.getRideRating();
        }
        if (sum == 0){
            driver.setRating(0);
        }
        driver.setRating(sum/ RideController.getFinishedRides(driver).size());
    }

    public static void calculateDriverSalary(Driver driver){
        String date = DatesController.dateToStr(new GregorianCalendar());
        driver.setSalary(DriverRidesReportController.calculateSalary(DriverRidesReportController.getMonthlyRides(date,driver)));
    }

    public static LinkedList<Driver> getFreeDrivers() {
        LinkedList<Driver> retList = new LinkedList<>();
        for (int i = 0; i < LoggedIn.getService().getNotDeletedDrivers().size(); i++) {
            Driver driver = LoggedIn.getService().getNotDeletedDrivers().get(i);
            if (UsersFileRepository.driverIsFree(driver) && driver.getVehicle() != null) {
                retList.add(driver);
            }
        }
        return retList;
    }

    public static void addRidesToUsers(LinkedList<Person> users, LinkedList<Ride> rides){
        for (Person p : users) {
            for (Ride r : rides) {
                if (r.getUser().getUsername().equals(p.getUsername()) && p instanceof User){
                    ((User) p).addUserRides(r);
                }
            }
        }
    }

    public static void addRidesToDrivers(LinkedList<Person> users,LinkedList<Ride> rides){
        for (Ride r : rides) {
            for (Person p : users) {
                if (r.getDriver() == null){
                    continue;
                }
                if (r.getDriver().getUsername().equals(p.getUsername()) && p instanceof Driver){
                    ((Driver) p).addDriverRides(r);
                }
            }
        }
    }
}
