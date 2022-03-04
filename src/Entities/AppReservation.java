package Entities;

import Controllers.DatesController;
import Enums.ReservationStatus;
import Exceptions.FileErrorExeption;
import Exceptions.LoadException;
import Repositories.UsersFileRepository;
import Search.SearchAlgorithms;

import java.util.GregorianCalendar;

public class AppReservation extends Ride {
    private String note;

    public AppReservation(GregorianCalendar dateAndTime, String startAddress, String arivalAddress, double numberOfKm, double duration, int price, ReservationStatus status, int rideNumber, Driver driver, User user, String note,int rideRating) {
        super(dateAndTime, startAddress, arivalAddress, numberOfKm, duration, price, status, rideNumber, driver, user,rideRating);
        this.note = note;
    }

    public AppReservation() {
        super();
        this.note = "";
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void printToConsole(){
        System.out.println("Datum i vreme pocetka: " + DatesController.dateAndTimeToStr(this.dateAndTime) + "\nPocetna adresa: " + this.startAddress + "\nAdresa odredista: " + this.arrivalAddress + "\nDuzina voznje: " + (this.numberOfKm == 0 ? "-" : this.numberOfKm) + "km" +  "\nTrajanje voznje: " + this.duration + "\nCena: " + (this.price == 0 ? "-" : this.price) + "\nStatus: " + this.status + "\nBroj voznje: " + this.rideNumber + "\nVozac: " + (this.driver.equals(null) ? "-" :  this.driver.username) + "\nKorisnik: " + this.user.username  + "," + this.rideRating + "," + this.note + "\n");
    }



    @Override
    public String Serialize() {
        return this.isDeleted + "," + this.rideNumber+ "," + DatesController.dateAndTimeToStr(this.dateAndTime) + "," + this.startAddress + "," + this.arrivalAddress + "," + this.numberOfKm + "," + this.duration + "," + this.price  + "," + this.status.ordinal() + "," + (this.driver == null ? "-" : this.driver.username) + "," + this.user.username +  "," + this.rideRating + "," + this.note + "\n";
    }

    @Override
    public Ride Deserialize(String loadedLine) {
        try{
            String[] entities = loadedLine.split(",");

            if (entities.length != 13) {
                throw new FileErrorExeption("Unable to  deserialize Drive at line: " + loadedLine);
            }

            this.isDeleted= Boolean.parseBoolean(entities[0]);
            this.rideNumber = Integer.parseInt(entities[1]);
            this.dateAndTime = DatesController.convertDateAndTimeString(entities[2]);
            this.startAddress = entities[3];
            this.arrivalAddress = entities[4];
            this.numberOfKm = entities[5].equals("-") ? null : Double.parseDouble(entities[5]);
            this.duration = Double.parseDouble(entities[6]);
            this.price = entities[7].equals("-") ? null : Integer.parseInt(entities[7]);
            this.driver = entities[9].equals("-") ? null : SearchAlgorithms.getDriver(UsersFileRepository.loadAllUsers(),entities[9]);
            this.status = ReservationStatus.values()[Integer.parseInt(entities[8])];
            this.user = SearchAlgorithms.getUser(UsersFileRepository.loadAllUsers(),entities[10]);
            this.rideRating = Integer.parseInt(entities[11]);
            this.note = entities[12];
            return this;
        }catch (FileErrorExeption e){
            throw e;
        }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
            throw new LoadException(e);
        }
    }

}