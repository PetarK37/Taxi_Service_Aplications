package Main;

import Controllers.*;
import Repositories.*;

import java.io.File;

import GUI.*;
import Utils.*;

public class Main {

    public static void main(String[] args)  {

        File users = new File(Constants.USERS_PATH);
        File cars = new File(Constants.VEHICLES_PATH);
        File rides = new File(Constants.RIDES_PATH);
        File service = new File(Constants.SERVICE_PATH);

        LoggedIn.setService(TaxiServiceFileRepository.loadService());
        LoggedIn.getService().loadAllVehicles();
        LoggedIn.getService().loadAllUsers();
        LoggedIn.getService().loadAllRides();
        UsersController.addRidesToDrivers(LoggedIn.getService().getUsers(),LoggedIn.getService().getRides());
        UsersController.addRidesToUsers(LoggedIn.getService().getUsers(),LoggedIn.getService().getRides());
        LoggedIn.getService().loadAllAuctions();
        AuctionController.addOffersToAuction(AuctionFileRepository.loadAllOffers(),LoggedIn.getService().getAuctions());

        initLoginMenu();
    }

	private static void initLoginMenu(){
        Login menu = new Login();
        menu.login.setVisible(true);
    }






}

