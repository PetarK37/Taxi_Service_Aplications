package Entities;

import Auction.Auction;
import Enums.Role;

import Exceptions.FileErrorExeption;
import Exceptions.LoadException;
import Exceptions.SaveExcption;
import Interfaces.SerializationAndDeserialization;
import List.*;
import Repositories.AuctionFileRepository;
import Repositories.RidesFileRepository;
import Repositories.UsersFileRepository;
import Repositories.VehiclesFileRepository;


import java.io.IOException;


public class TaxiService implements SerializationAndDeserialization {

    private int pib;
    private String name;
    private String address;
    private LinkedList<Person> users;
    private LinkedList<Ride> rides;
    private  LinkedList<Vehicle> vehicles;
    private LinkedList<Auction> auctions;
    private int priceForKm;
    private int startingPrice;

    public TaxiService() {
        this.users = new LinkedList<Person>();
        this.vehicles = new LinkedList<Vehicle>();
        this.auctions = new LinkedList<Auction>();
    }

    public TaxiService(int pib, String name, String address, LinkedList<Person> users, LinkedList<Ride> rides, LinkedList<Vehicle> vehicles, int priceForKm, int startingPrice, LinkedList<Auction> auctions) {
        this.pib = pib;
        this.name = name;
        this.address = address;
        this.users = users;
        this.rides = rides;
        this.vehicles = vehicles;
        this.priceForKm = priceForKm;
        this.startingPrice = startingPrice;
        this.auctions = auctions;
    }


    public void printAllVehicles() {
        System.out.println("Vozila:");
        for (Vehicle v : vehicles) {
            v.printToConsole();
        }
    }

    public void printAllUsers() {
        System.out.println("Korisnici: ");
        for (Person p : users) {
            if (p.role == Role.USER)
                p.printToConsole();
        }
        System.out.println("Vozači: ");
        for (Person p : users) {
            if (p.role == Role.DRIVER){
                p.printToConsole();
            }
        }
        System.out.println("Dispečeri: ");
        for(Person p : users){
            if (p.role == Role.DISPATCHER){
                p.printToConsole();
            }
        }
    }

    public void printAllRides() {
        System.out.println("Voznje: ");
        for (Ride r : rides) {
            r.printToConsole();
        }
    }

    public void addUser(Person user) {
        this.users.add(user);
    }

    public void loadAllVehicles() {
        try {
            this.vehicles = VehiclesFileRepository.loadAllVehicles();
        }catch (FileErrorExeption e){
            System.out.println(e.getProblematicLine());
        }catch (LoadException e){
            System.out.println("Error while loading");
        }
    }


    public void loadAllUsers() {
        try {
            this.users = UsersFileRepository.loadAllUsers();
        }catch (FileErrorExeption e){
        System.out.println(e.getProblematicLine());
        }catch (LoadException e){
        System.out.println("Error while loading");
        }
    }


    public void loadAllRides(){
        try {
            this.rides = RidesFileRepository.loadAllRides();
        }catch (FileErrorExeption e){
            System.out.println(e.getProblematicLine());
        }catch (LoadException e){
            System.out.println("Error while loading");
        }
    }

    public void loadAllAuctions() {
        try {
            this.auctions = AuctionFileRepository.loadAllAuctions();
        }catch (FileErrorExeption e){
            System.out.println(e.getProblematicLine());
        }catch (LoadException e){
            System.out.println("Error while loading");
        }
    }

    public LinkedList<Vehicle> getNotDeletedVehicles(){
        LinkedList<Vehicle> returnList = new LinkedList<>();
        for (Vehicle v: this.vehicles){
            if (v.isDeleted() == false){
                returnList.add(v);
            }
        }
        return returnList;
    }

    public LinkedList<Driver> getNotDeletedDrivers(){
        LinkedList<Driver> returnList = new LinkedList<>();
        for (Person p: this.users){
            if (p.isDeleted() == false && p instanceof Driver){
                returnList.add((Driver)p);
            }
        }
        return returnList;
    }

    public LinkedList<Auction> getNotFinishedAuctions(){
        LinkedList<Auction> returnList = new LinkedList<>();
        for (Auction a: this.auctions){
            if (a.getWinningDriver() == null){
                returnList.add(a);
            }
        }
        return returnList;
    }

    public LinkedList<Auction> getFinishedAuctions(){
        LinkedList<Auction> returnList = new LinkedList<>();
        for (Auction a: this.auctions){
            if (a.getWinningDriver() != null){
                returnList.add(a);
            }
        }
        return returnList;
    }

    public int getPib() {
        return this.pib;
    }

    public void setPib(int pib) {
        this.pib = pib;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LinkedList<Person> getUsers() {
        return this.users;
    }

    public void setUsers(LinkedList<Person> users) {
        this.users = users;
    }

    public LinkedList<Ride> getRides() {
        return this.rides;
    }

    public void setRides(LinkedList<Ride> rides) {
        this.rides = rides;
    }

    public LinkedList<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(LinkedList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public int getPriceForKm() {
        return this.priceForKm;
    }

    public void setPriceForKm(int priceForKm) {
        this.priceForKm = priceForKm;
    }

    public int getStartingPrice() {
        return this.startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setAuctions(LinkedList<Auction> auctions) {
        this.auctions = auctions;
    }

    public LinkedList<Auction> getAuctions() {
        return auctions;
    }

    @Override
    public String Serialize() {
        return this.pib + "," + this.name + "," + this.address + "," + this.priceForKm + "," + this.startingPrice  + "\n";
    }

    @Override
    public TaxiService Deserialize(String loadedLine) throws LoadException {
        try {
            String[] entities = loadedLine.split(",");

            if (entities.length != 5) {
                throw new FileErrorExeption("Unable to  load Driver at line: " + loadedLine);
            }

            this.pib = Integer.parseInt(entities[0]);
            this.name = entities[1];
            this.address = entities[2];
            this.priceForKm = Integer.parseInt(entities[3]);
            this.startingPrice = Integer.parseInt(entities[4]);
            this.users = new LinkedList<Person>();
            this.vehicles = new LinkedList<Vehicle>();

            return this;
        } catch (FileErrorExeption e){
            throw e;
        }
    }

    public void addRide(Ride r) {
        this.rides.add(r);
    }
}