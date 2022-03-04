package Controllers;

import Entities.Driver;
import Entities.Ride;
import Entities.User;
import Entities.Vehicle;
import Exceptions.UpdateException;
import List.LinkedList;
import Repositories.UsersFileRepository;
import Search.SearchAlgorithms;
import Utils.Constants;
import Utils.LoggedIn;

import java.util.GregorianCalendar;

public class VehicleController {

    public static int NEW_WEHICLE_YEAR_NUMBER = 5;

    public static void updateVehicle(Vehicle target,boolean petFriendly, String registrationPlate, Driver driver) throws UpdateException {
        if (UsersFileRepository.driverIsFree(SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(),target.getDriver()))){
            target.setPetFriendly(petFriendly);
            target.setRegistrationNumber(registrationPlate);

            if(driver == null){
                if (target.getDriver() != null){
                    SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), target.getDriver()).setVehicle(null);
                }
                target.setDriver(null);
            }else {
                driver.setVehicle(target);
                target.setDriver(driver.getUsername());
            }
        }else{
            throw new UpdateException("Cant update this vehicle");
        }
    }

    public static void addNewVehicle(LinkedList<Vehicle> vehicles, Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public static boolean isNewerWehicle(Vehicle vehicle){
        if ((new GregorianCalendar().toZonedDateTime().getYear() - vehicle.getYearOfManifacture()) < NEW_WEHICLE_YEAR_NUMBER){
            return true;
        }
        return false;
    }

    public static int generateId(LinkedList<Vehicle> vehicles){
        return (Constants.VEHICLES_ID_NUMBER_OF_DIGITS + vehicles.size());
    }

}
