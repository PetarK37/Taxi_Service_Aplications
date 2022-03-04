package Search;

import Auction.*;
import Entities.*;
import List.LinkedList;
import Utils.Constants;
import Utils.LoggedIn;

public class SearchAlgorithms {

    public static Vehicle getCar(LinkedList<Vehicle> vehicles, int taxiLabel){
        return BinarySearchVehicle(vehicles, taxiLabel, vehicles.get(0), vehicles.get(vehicles.size()-1));
    }


    private static Vehicle BinarySearchVehicle(LinkedList<Vehicle> vehicles,int target, Vehicle low, Vehicle high){
        if (low.getTaxiLabel() > high.getTaxiLabel()){
            return null;
        }

        int midIndex = (low.getTaxiLabel()+high.getTaxiLabel())/2 - Constants.VEHICLES_ID_NUMBER_OF_DIGITS;
        Vehicle mid = vehicles.get(midIndex);

        if(mid.getTaxiLabel() == target){
                 return mid;
        }
         else if (target < mid.getTaxiLabel()){
             return BinarySearchVehicle(vehicles, target, low, vehicles.get(midIndex - 1));
        }else {
            return BinarySearchVehicle(vehicles,target,vehicles.get(midIndex + 1),high);
        }
    }


    public static Auction getAuction(LinkedList<Auction> auctions, int id){
        return BinarySearchAuction(auctions, id, auctions.get(0), auctions.get(auctions.size()-1));
    }

    private static Auction BinarySearchAuction(LinkedList<Auction> auctions, int target, Auction low, Auction high){
        if (low.getId() > high.getId()){
            return null;
        }

        Auction mid = auctions.get((low.getId() + high.getId())/2);

        if(mid.getId() == target){
            return mid;
        }

        else if (target < mid.getId()){
            return BinarySearchAuction(auctions, target, low, auctions.get(mid.getId() - 1));
        }else {
            return BinarySearchAuction(auctions,target,auctions.get(mid.getId() + 1),high);
        }
    }

    public static User getUser(LinkedList<Person> users, String username){
        for (Person user: users){
            if (user.getUsername().equals(username) && user instanceof User){
                return (User) user;
            }
        }
        return null;
    }


    public static Dispatcher getDispatcher(LinkedList<Person> users, String username){
        for (Person user: users){
            if (user.getUsername().equals(username) && user instanceof Dispatcher){
                return (Dispatcher) user;
            }
        }
        return null;
    }

    public static Driver getDriver (LinkedList<Person> users, String username){
        for (Person user: users){
            if (user.getUsername().equals(username) && user instanceof Driver){
                return (Driver) user;
            }
        }
        return null;
    }

    public static Ride getRide(LinkedList<Ride> rides, int number) {
        for (Ride ride: rides){
            if (ride.getRideNumber() == number){
                return ride;
            }
        }
        return null;
    }

    public static Auction getAuctionFromRide(LinkedList<Auction> auctions, Ride ride){
      for (Auction a: auctions){
         if(a.getRide().equals(ride)){
            return a;
         }
      }
      return null;
    };

    public static LinkedList<Vehicle> multipleCriteriaCarSearch(String model,String manifacturer,String year,
                                                                String registration,String taxiId){
        LinkedList<Vehicle> retList = new LinkedList<Vehicle>();

        for (Vehicle v: LoggedIn.getService().getVehicles()){
            if (v.getModel().toLowerCase().contains(model) && v.getManifacturer().toLowerCase().contains(manifacturer) &&
            String.valueOf(v.getYearOfManifacture()).toLowerCase().contains(year) && v.getRegistrationNumber().toLowerCase().contains(registration)
            && String.valueOf(v.getTaxiLabel()).toLowerCase().contains(taxiId)){
                retList.add(v);
            }
        }
        return retList;
     }
}
