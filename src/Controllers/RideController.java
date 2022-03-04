package Controllers;

import Entities.*;
import Enums.ReservationStatus;
import Exceptions.UpdateException;
import List.LinkedList;
import Utils.Constants;
import Utils.LoggedIn;

import java.util.GregorianCalendar;

public class RideController {

    public static void UpdateRideStatus(Ride reservation, Enums.ReservationStatus status) throws UpdateException {
        if (reservation.getStatus().ordinal() > status.ordinal()){
            throw new UpdateException("Cant update staus");
        }else {
            reservation.setStatus(status);
        }
    };

    public static void assignRide(Ride ride,Driver driver){
        ride.setStatus(ReservationStatus.ASSIGNED);
        ride.setDriver(driver);
    }

    public static void finishAppRide(Ride ride, Double km, Double duration,Driver driver){
        ride.setStatus(ReservationStatus.FINISHED);
        ride.setNumberOfKm(km);
        ride.setDuration(duration);
        ride.setDriver(driver);
        ride.setPrice(calculatePrice(km, LoggedIn.getService()));
    }

    public static void rejectRide(Ride ride){
        ride.setStatus(ReservationStatus.REJECTED);
        ride.setNumberOfKm(0);
        ride.setDuration(0);
        ride.setPrice(0);
    }

    public static void CreateNewAppReservation(LinkedList<Ride> rides, AppReservation reservation){
        rides.add(reservation);
        reservation.getUser().getUserRides().add(reservation);
    }
    public static void CreateNewPhoneReservation(LinkedList<Ride> rides, PhoneReservation reservation){
        rides.add(reservation);
    }

    public static int generateId(LinkedList<Ride> rides){
        return (Constants.RIDES_ID_NUMBER_OF_DIGITS + rides.size());
    }


    private static int calculatePrice(double numOfkm, TaxiService service) {
        return (int)(service.getStartingPrice() + (numOfkm * service.getPriceForKm()));
    }

    public static void rateRide(Ride ride, int rating){
        ride.setRideRating(rating);
    }

    static LinkedList<Ride> getFinishedRides(Driver driver){
        LinkedList<Ride> retList = new LinkedList<Ride>();
        for (Ride r : driver.getDriverRides()){
            if (r.getStatus() == ReservationStatus.FINISHED){
                retList.add(r);
            }
        }
        return retList;
    }
}
