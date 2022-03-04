package GUI.UpdateDialogs;

import Auction.Auction;
import Controllers.*;
import Exceptions.SaveExcption;
import List.LinkedList;
import Entities.*;
import Repositories.*;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AssignFreeDriversDialog {
    private JPanel mainPanel;
    private JLabel rideNumLabel;
    private JTextField rideNumField;
    private JLabel startAdressLabel;
    private JTextField startAdressField;
    private JLabel arrivalAdressLabel;
    private JTextField arrivalAdressField;
    private JLabel dateAndTimeLabel;
    private JTextField dateAndTimeField;
    private JLabel userLabel;
    private JTextField userField;
    private JComboBox driverBox;
    private JLabel driverLabel;
    private JButton assignButton;
    public static JFrame assignWindow;


    private Auction auction;

    public  AssignFreeDriversDialog(Auction auction) {
        this.auction = auction;

        assignWindow = new JFrame();
        assignWindow.setContentPane(this.mainPanel);
        assignWindow.setLocationRelativeTo(null);
        assignWindow.pack();

        assignWindow.setTitle("Dodela vozaca: ");
        fillInData();
        initActions();

    }

    private void initActions() {
        this.assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignRide();
                assignWindow.dispose();
                assignWindow.setVisible(false);
            }
        });
    }

    private void assignRide() {
        RideController.assignRide(auction.getRide(), SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(),
                String.valueOf(this.driverBox.getSelectedItem())));
        AuctionController.finishAuction(SearchAlgorithms.getAuctionFromRide(LoggedIn.getService().getAuctions(),auction.getRide())
                , SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(),String.valueOf(this.driverBox.getSelectedItem())));

        JOptionPane.showMessageDialog(null, "Uspesno sacuvani podaci", "Cuvanje podataka", JOptionPane.INFORMATION_MESSAGE);

        try {
            RidesFileRepository.writeAllRides(LoggedIn.getService().getRides());
            AuctionFileRepository.writeAllAuctions(LoggedIn.getService().getAuctions());
        } catch (SaveExcption e) {
            JOptionPane.showMessageDialog(null, "Greska prilikom cuvanja u bazu", "Greska", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fillInData() {
        this.rideNumField.setText(String.valueOf(auction.getRide().getRideNumber()));
        this.startAdressField.setText(auction.getRide().getStartAddress());
        this.arrivalAdressField.setText(auction.getRide().getArrivalAddress());
        this.dateAndTimeField.setText(DatesController.dateAndTimeToStr(auction.getRide().getDateAndTime()));
        this.userField.setText(auction.getRide().getUser().getUsername());
        fillInDriverBox();

    }

    private void fillInDriverBox() {
        for (int i = 0; i <  UsersController.getFreeDrivers().size(); i++) {
            if (UsersController.getFreeDrivers().get(i).getVehicle().getType() == auction.getVehicleType()
            && comparePetFriendly(UsersController.getFreeDrivers().get(i).getVehicle()))
            this.driverBox.addItem(UsersController.getFreeDrivers().get(i).getUsername());
        }
    }

    private boolean comparePetFriendly(Vehicle vehicle){
        if (vehicle.isPetFriendly() ==  auction.getPetFreindly()){
            return true;
        }else if (vehicle.isPetFriendly() == true && auction.getPetFreindly() == false){
            return true;
        }
        return false;
    }

}
