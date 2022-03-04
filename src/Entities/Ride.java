package Entities;

import Enums.*;
import Interfaces.SerializationAndDeserialization;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public abstract class Ride implements SerializationAndDeserialization<Ride> {

    protected GregorianCalendar dateAndTime;
    protected String startAddress;
    protected String arrivalAddress;
    protected double numberOfKm;
    protected double duration;
    protected int price;
    protected ReservationStatus status;
    protected int rideNumber;
    protected Driver driver;
    protected User user;
    protected boolean isDeleted;
    protected int rideRating;

    public Ride(GregorianCalendar dateAndTime, String startAddress, String arrivalAddress, double numberOfKm, double duration, int price, ReservationStatus status, int rideNumber, Driver driver, User user, int rideRating) {
        this.dateAndTime = dateAndTime;
        this.startAddress = startAddress;
        this.arrivalAddress = arrivalAddress;
        this.numberOfKm = numberOfKm;
        this.duration = duration;
        this.price = price;
        this.status = status;
        this.rideNumber = rideNumber;
        this.driver = driver;
        this.user = user;
        this.rideRating = rideRating;
        this.isDeleted = false;
    }

    public Ride() {
        this.dateAndTime = null;
        this.startAddress = "";
        this.arrivalAddress = "";
        this.numberOfKm = 0;
        this.duration = 0;
        this.price = 0;
        this.status = null;
        this.rideNumber = 0;
        this.driver = null;
        this.user = null;
        this.isDeleted = false;
        this.rideRating = 0;
    }

    public void printToConsole(){
    }

    public GregorianCalendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(GregorianCalendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public double getNumberOfKm() {
        return numberOfKm;
    }

    public void setNumberOfKm(double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(int rideNumber) {
        this.rideNumber = rideNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getRideRating() {
        return rideRating;
    }

    public void setRideRating(int rideRating) {
        this.rideRating = rideRating;
    }
}