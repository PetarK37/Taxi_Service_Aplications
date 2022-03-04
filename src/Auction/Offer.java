package Auction;

import Entities.Driver;
import Entities.Person;
import Entities.Vehicle;
import Exceptions.FileErrorExeption;
import Exceptions.LoadException;
import Interfaces.SerializationAndDeserialization;
import List.LinkedList;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

public class Offer implements SerializationAndDeserialization {
   private Driver driver;
    private Auction auction;
    private double distance;
    private double points;

    public Offer(Driver driver, Auction auction, double distance) {
        this.driver = driver;
        this.auction = auction;
        this.distance = distance;
        this.points = 0.0;
    }

    public Offer() {
        this.driver = null;
        this.auction = null;
        this.distance = 0;
        this.points = 0.0;
    }

    public Auction getAuction() {
        return auction;
    }

    public Driver getDriver() {
        return driver;
    }

    public double getDistance() {
        return distance;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public String Serialize() {
        return this.auction.getId() + "," + this.driver.getUsername() + "," + this.distance + "," + this.points + "\n";
    }

    @Override
    public Offer Deserialize(String loadedLine) throws LoadException {
        try {
            String[] entities = loadedLine.split(",");

            if (entities.length != 4) {
                throw new FileErrorExeption("Unable to  load offer at line: " + loadedLine);
            }
            this.auction = SearchAlgorithms.getAuction(LoggedIn.getService().getAuctions(),Integer.parseInt(entities[0]));
            this.driver = SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), entities[1]);
            this.distance = Double.parseDouble(entities[2]);
            this.points = Double.parseDouble(entities[3]);

            return this;
        } catch (FileErrorExeption e){
            throw e;
        }
    }
}
