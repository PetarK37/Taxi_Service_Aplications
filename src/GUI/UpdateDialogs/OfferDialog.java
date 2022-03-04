package GUI.UpdateDialogs;

import Auction.*;
import Controllers.OfferController;
import Entities.Driver;
import Exceptions.SaveExcption;
import Repositories.AuctionFileRepository;
import Utils.LoggedIn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OfferDialog {
    private JPanel mainPanel;
    private JLabel inputLabel;
    private JTextField durationInput;
    private JButton offerBtn;
    public static JFrame offerWindow;
    private Auction auction;

    public OfferDialog(Auction auction) {
        this.auction = auction;

        offerWindow = new JFrame();
        offerWindow.setContentPane(this.mainPanel);
        offerWindow.setLocationRelativeTo(null);
        offerWindow.setTitle("Ponuda za voznju");
        offerWindow.pack();
        initOfferActions();

    }

    private void initOfferActions() {
        this.offerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation()){
                    makeOfrer();
                    offerWindow.dispose();
                    offerWindow.setVisible(false);
                }
            }
        });
    }

    private void makeOfrer() {
        if (validation()) {
            Offer newOfer = new Offer((Driver) LoggedIn.getLoggedUser(),auction,Double.parseDouble(this.durationInput.getText()));
            OfferController.calculateOfferPoints(newOfer);
            OfferController.CreateNewOffer(auction.getOffers(),newOfer);

            try {
                AuctionFileRepository.writeNewOffer(newOfer);
                JOptionPane.showMessageDialog(null, "Uspesno  sacuvana ponuda", "Cuvanje podataka", JOptionPane.INFORMATION_MESSAGE);
            } catch (SaveExcption e) {
                JOptionPane.showMessageDialog(null, "Greska prilikom pisanja u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean validation() {
        boolean isValid = true;
        String message = "Molimo vas ispravite sledece greske prilkom unosa:\n";
        if (this.durationInput.getText().trim().equals("")) {
            message += "Morate uneti koliko vam treba minuta \n";
            isValid = false;
        }
        try {
            Integer.parseInt(this.durationInput.getText().trim());
        } catch (NumberFormatException e) {
            message += "Trajanje mora biti broj";
            isValid = false;
        }
        if (isValid == false) {
            JOptionPane.showMessageDialog(null, message, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
        }
        return isValid;
    }
}
