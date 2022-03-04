package Auction;

import Entities.Driver;
import Entities.Ride;
import Enums.VehicleType;
import Exceptions.FileErrorExeption;
import Exceptions.LoadException;
import Interfaces.SerializationAndDeserialization;
import List.LinkedList;
import Repositories.UsersFileRepository;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

public class Auction implements SerializationAndDeserialization {
    private int id;
    private Ride ride;
    private Boolean petFreindly;
    private Boolean newerVehicle;
    private VehicleType vehicleType;
    private  LinkedList<Offer> offers;
    private Driver winningDriver;

    public Auction(int id, Ride ride, Boolean petFreindly, Boolean newerVehicle, VehicleType vehicleType, LinkedList<Offer> offers) {
        this.id = id;
        this.ride = ride;
        this.petFreindly = petFreindly;
        this.newerVehicle = newerVehicle;
        this.offers = offers;
        this.vehicleType = vehicleType;
        this.winningDriver = null;
    }


    public Auction() {
        this.id = 0;
        this.ride = null;
        this.petFreindly = false;
        this.newerVehicle = false;
        this.offers = new LinkedList<Offer>();
        this.vehicleType = VehicleType.CAR;
        this.winningDriver = null;
    }



    @Override
    public String Serialize() {
        return this.id + "," + this.ride.getRideNumber() + "," + this.petFreindly + "," + this.newerVehicle + "," + this.vehicleType.ordinal() + "," + (this.winningDriver == null ? "-" : this.winningDriver.getUsername())+ "\n";
    }

    @Override
    public Auction Deserialize(String loadedLine) throws LoadException {
        try {
            String[] entities = loadedLine.split(",");

            if (entities.length != 6) {
                throw new FileErrorExeption("Unable to  load Driver at line: " + loadedLine);
            }

            this.id = Integer.parseInt(entities[0]);
            this.ride = SearchAlgorithms.getRide(LoggedIn.getService().getRides(),Integer.parseInt(entities[1]));
            this.petFreindly = Boolean.parseBoolean(entities[2]);
            this.newerVehicle = Boolean.parseBoolean(entities[3]);
            this.vehicleType = VehicleType.values()[Integer.parseInt(entities[4])];
            this.winningDriver = entities[5].equals("-") ? null : SearchAlgorithms.getDriver(UsersFileRepository.loadAllUsers(),entities[5]);
            this.offers = new LinkedList<Offer>();

            return this;
        } catch (FileErrorExeption e){
            throw e;
        }
    }

    public  LinkedList<Offer> getOffers() {
        return offers;
    }

    public Driver getWinningDriver() {
        return winningDriver;
    }


    public void setWinningDriver(Driver winningDriver) {
        this.winningDriver = winningDriver;
    }
    public int getId() {
        return id;
    }

    public Ride getRide() {
        return ride;
    }

    public Boolean getPetFreindly() {
        return petFreindly;
    }

    public Boolean getNewerVehicle() {
        return newerVehicle;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
