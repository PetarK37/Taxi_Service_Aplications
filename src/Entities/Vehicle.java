package Entities;

import Enums.*;
import Exceptions.FileErrorExeption;
import Exceptions.LoadException;
import Interfaces.SerializationAndDeserialization;

import java.io.BufferedWriter;
import java.io.IOException;

public class Vehicle implements SerializationAndDeserialization<Vehicle> {

    private String model;
    private String manifacturer;
    private int yearOfManifacture;
    private String registrationNumber;
    private int taxiLabel;
    private VehicleType type;
    private boolean petFriendly;
    private String driver;
    private boolean isDeleted;

    public Vehicle() {
        this.model = "";
        this.manifacturer = "";
        this.yearOfManifacture = 0;
        this.registrationNumber = "";
        this.taxiLabel = 0;
        this.type = null;
        this.petFriendly = false;
        this.driver = null;
        this.isDeleted= false;
    }


    public Vehicle(String model, String manifacturer, int yearOfManifacture, String registrationNumber, int taxiLabel, VehicleType type, boolean petFriendly, Driver driver) {
        this.model = model;
        this.manifacturer = manifacturer;
        this.yearOfManifacture = yearOfManifacture;
        this.registrationNumber = registrationNumber;
        this.taxiLabel = taxiLabel;
        this.type = type;
        this.petFriendly = petFriendly;
        this.driver = driver == null? null : driver.getUsername();
        this.isDeleted = false;
    }



    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManifacturer() {
        return manifacturer;
    }

    public void setManifacturer(String manifacturer) {
        this.manifacturer = manifacturer;
    }

    public int getYearOfManifacture() {
        return yearOfManifacture;
    }

    public void setYearOfManifacture(int yearOfManifacture) {
        this.yearOfManifacture = yearOfManifacture;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getTaxiLabel() {
        return taxiLabel;
    }

    public void setTaxiLabel(int taxiLabel) {
        this.taxiLabel = taxiLabel;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(boolean deleted) {isDeleted = deleted;}

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public String getDriver() {return driver;}

    public void setDriver(String driver) {this.driver = driver;}


    public void printToConsole() {
        System.out.println("Marka: " + this.manifacturer + "\nModel: " + this.model + "\nGodina proizvodnje: " + this.yearOfManifacture + "\nRegistracione tablice: " + this.registrationNumber + "\nTip:" + this.type + "\nPet friendly: " + (this.petFriendly ? "Da" : "Ne") + "\n");
    }

    @Override
    public String Serialize() {
        return this.isDeleted + "," + this.taxiLabel + "," + this.manifacturer + "," + this.model + "," + this.yearOfManifacture + "," + this.registrationNumber + "," + this.type.ordinal() + "," + (this.driver == null ? "-": this.driver) + "," + this.petFriendly + "\n";
    }

    @Override
    public Vehicle Deserialize(String loadedLine){
        try {
            String[] entities = loadedLine.split(",");

            if (entities.length != 9) {
                throw new FileErrorExeption("Unable to  load Vehicle at line: " + loadedLine);

            }

            this.isDeleted= Boolean.parseBoolean(entities[0]);
            this.model = entities[3];
            this.manifacturer = entities[2];
            this.yearOfManifacture = Integer.parseInt(entities[4]);
            this.registrationNumber = entities[5];
            this.taxiLabel = Integer.parseInt(entities[1]);
            this.type = VehicleType.values()[Integer.parseInt(entities[6])];
            this.petFriendly = Boolean.parseBoolean(entities[8]);
            this.driver = entities[7].equals("-") ? null: entities[7];

            return this;
        }catch (FileErrorExeption e){
            throw e;
        }
    }
}