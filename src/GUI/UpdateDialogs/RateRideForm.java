package GUI.UpdateDialogs;

import Controllers.RideController;
import Controllers.UsersController;
import Entities.Driver;
import Entities.Ride;
import Exceptions.SaveExcption;
import Repositories.RidesFileRepository;
import Repositories.UsersFileRepository;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RateRideForm {
    private JPanel mainPanel;
    private JLabel rateLabel;
    private JTextField ratingField;
    private JButton rateBtn;

    public static JFrame rateWidnow;

    public RateRideForm(Ride ride){
        rateWidnow = new JFrame();
        rateWidnow.setTitle("Unos ocene");
        rateWidnow.setContentPane(this.mainPanel);
        rateWidnow.setLocationRelativeTo(null);
        rateWidnow.setResizable(false);
        rateWidnow.pack();

        initActions(ride);
    }

    private void initActions(Ride ride) {
        this.rateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (validate()){
                   rate(ride);
                   rateWidnow.dispose();
                   rateWidnow.setVisible(false);
               }

            }
        });
    }

    private boolean validate(){
        boolean isValid = true;
        if (ratingField.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Morate uneti ocenu", "Ocena voznje", JOptionPane.WARNING_MESSAGE);
            rateWidnow.dispose();
            rateWidnow.setVisible(false);
            isValid = false;
        }
        return isValid;
    }

    public void rate(Ride ride){
        int rating = Integer.parseInt(this.ratingField.getText().trim());
        RideController.rateRide(ride,rating);
        Driver driver = SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), ride.getDriver().getUsername());
        UsersController.calculateDriverRating(driver);
        try {
            RidesFileRepository.writeAllRides(LoggedIn.getService().getRides());
            UsersFileRepository.writeAllUsers(LoggedIn.getService().getUsers());

            JOptionPane.showMessageDialog(null, "Uspesno  ocenjena voznja", "Ocena voznje", JOptionPane.INFORMATION_MESSAGE);

        } catch (SaveExcption e) {
            JOptionPane.showMessageDialog(null, "Greska prilikom pisanja u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
        }
    }
}
