package Entities;

import Enums.*;

import Exceptions.FileErrorExeption;
import List.*;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

public class Driver extends Employee {

    private int membershipCardNumber;
    private Vehicle vehicle;
    private double rating;
    private LinkedList<Ride> driverRides;

    public Driver(String name, String surname, long jmbg, Sex sex, String address, String phoneNumber, String username, String password, Role role, double salary, int membershipCardNumber, Vehicle vehicle, double rating, LinkedList<Ride> driverRides) {
        super(name, surname, jmbg, sex, address, phoneNumber, username, password, role, salary);
        this.membershipCardNumber = membershipCardNumber;
        this.vehicle = vehicle;
        this.rating = rating;
        this.driverRides = driverRides;
    }

    public Driver() {
        super();
        this.membershipCardNumber = 0;
        this.vehicle = null;
        this.rating = 0;
        this.driverRides = null;
    }


    public void printToConsole() {
        System.out.println("Korisnicko ime: " + this.username + "\nIme i prezime: " + this.name + this.surname + "\nJMBG: " + this.jmbg + "\nPol: " + (this.sex == Sex.MALE ? "Muški" : "Ženski") + "\nAdresa: " + this.address + "\nBroj telefona: " + this.phoneNumber + "\n");
    }


    @Override
    public String Serialize() {
        if (this.vehicle != null){
            return this.isDeleted + "," + this.role.ordinal() + "," + this.membershipCardNumber + "," + this.username + "," + this.password + "," + this.name + "," + this.surname + "," + this.jmbg + "," + this.sex.ordinal() + "," + this.address + "," + this.phoneNumber + "," + this.salary + "," + this.rating + "," + this.vehicle.getTaxiLabel() + "\n";
        }else{
            return this.isDeleted + "," + this.role.ordinal() + "," + this.membershipCardNumber + "," + this.username + "," + this.password + "," + this.name + "," + this.surname + "," + this.jmbg + "," + this.sex.ordinal() + "," + this.address + "," + this.phoneNumber + "," + this.salary + "," + this.rating + "," + "-" + "\n";
        }
    }

    @Override
    public Person Deserialize(String loadedLine) {
        try {
            String[] entities = loadedLine.split(",");

        if (entities.length != 14) {
            throw new FileErrorExeption("Unable to  load Driver at line: " + loadedLine);
        }

            this.isDeleted= Boolean.parseBoolean(entities[0]);
            this.name = entities[5];
            this.surname = entities[6];
            this.jmbg = Long.parseLong(entities[7]);
            this.sex = Sex.values()[Integer.parseInt(entities[8])];
            this.address = entities[9];
            this.phoneNumber = entities[10];
            this.username = entities[3];
            this.password = entities[4];
            this.role = Role.values()[Integer.parseInt(entities[1])];
            this.salary = Double.parseDouble(entities[11]);
            this.membershipCardNumber = Integer.parseInt(entities[2]);
            this.rating = Double.parseDouble(entities[12]);
            this.vehicle = entities[13].equals("-") ? null :
                    SearchAlgorithms.getCar(LoggedIn.getService().getVehicles(), Integer.parseInt(entities[13]));
            this.driverRides = new LinkedList<Ride>();

            return this;
        } catch (FileErrorExeption e){
            throw e;
        }
    }


    public int getMembershipCardNumber() {
        return membershipCardNumber;
    }

    public void setMembershipCardNumber(int membershipCardNumber) {
        this.membershipCardNumber = membershipCardNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LinkedList<Ride> getDriverRides() {
        return driverRides;
    }

    public void setDriverRides(LinkedList<Ride> driverRides) {
        this.driverRides = driverRides;
    }

    public void addDriverRides(Ride ride){this.driverRides.add(ride);}
}