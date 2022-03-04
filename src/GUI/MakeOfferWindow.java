package GUI;

import Auction.*;
import Controllers.DatesController;
import Controllers.VehicleController;
import Entities.AppReservation;
import Entities.*;
import Enums.ReservationStatus;
import GUI.UpdateDialogs.FinishRideForm;
import GUI.UpdateDialogs.OfferDialog;
import Search.SearchAlgorithms;
import Utils.LoggedIn;
import List.LinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakeOfferWindow {
    private JPanel buttonsPanel;
    private JButton offerBtn;
    private JPanel mainPanel;
    private JScrollPane tablePane;
    private JTable ridesTable;
    public static DefaultTableModel tableModel;
    public static JFrame ridesWindow;

    public MakeOfferWindow() {
        createTable();
        initActions();

        ridesWindow = new JFrame();
        ridesWindow.setTitle("Aukcijske voznje");
        ridesWindow.setContentPane(this.mainPanel);
        ridesWindow.setLocationRelativeTo(null);
        ridesWindow.pack();

    }

    private void createTable() {
        String[] header = new String[]{"Id aukcije:", "Datum i vreme narucivanja:", "Adresa polaska:", "Adresa dolaska:",  "Status:", "Korisnik", "Napomena:"};
        Object[][] content = new Object[getAuctionRides().size()][header.length];


        for (int i = 0; i < getAuctionRides().size(); i++) {
            Ride ride = getAuctionRides().get(i);
            Auction auction =  SearchAlgorithms.getAuctionFromRide(LoggedIn.getService().getAuctions(), ride);

            content[i][0] = auction.getId();
            content[i][1] = DatesController.dateAndTimeToStr(ride.getDateAndTime());
            content[i][2] = ride.getStartAddress();
            content[i][3] = ride.getArrivalAddress();
            content[i][4] = ride.getStatus();
            content[i][5] = ride.getUser().getUsername();
            if (ride instanceof AppReservation) {
                content[i][6] = ((AppReservation) ride).getNote();
            } else {
                content[i][6] = "-";
            }
        }

        tableModel = new DefaultTableModel(content, header);
        ridesTable = new JTable(tableModel);

        ridesTable.setRowSelectionAllowed(true);
        ridesTable.setColumnSelectionAllowed(false);
        ridesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ridesTable.setDefaultEditor(Object.class, null);
        ridesTable.getTableHeader().setReorderingAllowed(false);
        ridesTable.setAutoCreateRowSorter(true);

        tablePane = new JScrollPane(ridesTable);
        mainPanel.add(tablePane, BorderLayout.CENTER);

    }

    private LinkedList<Ride> getAuctionRides() {
        LinkedList<Ride> retList = new LinkedList<Ride>();
        if (((Driver) LoggedIn.getLoggedUser()).getVehicle() != null ) {
            for (Auction auction : LoggedIn.getService().getAuctions()) {
                if (auction.getWinningDriver() == null) {
                    if (canOffer(auction)) {
                        retList.add(auction.getRide());
                    }
                }
            }
        }
        return retList;
    }

    private boolean canOffer(Auction auction){
        for (Offer o: auction.getOffers()){
            if (o.getDriver().equals(LoggedIn.getLoggedUser())){
                return false;
            }
        }
        if (comparePetFriendly(auction) && compareNewerVehicles(auction)){
            return true;
        }
        return false;
    }

    private boolean comparePetFriendly(Auction auction){
        if (((Driver) LoggedIn.getLoggedUser()).getVehicle().isPetFriendly() ==  auction.getPetFreindly()){
            return true;
        }else if (((Driver) LoggedIn.getLoggedUser()).getVehicle().isPetFriendly() == true && auction.getPetFreindly() == false){
            return true;
        }
        return false;
    }

    private boolean compareNewerVehicles(Auction auction){
        if (VehicleController.isNewerWehicle(((Driver) LoggedIn.getLoggedUser()).getVehicle()) ==  auction.getNewerVehicle()){
            return true;
        }else if (VehicleController.isNewerWehicle(((Driver) LoggedIn.getLoggedUser()).getVehicle()) == true && auction.getNewerVehicle() == false){
            return true;
        }
        return false;
    }

    private void initActions() {
        this.offerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ridesTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = ridesTable.convertRowIndexToModel(row);

                    int auctionID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Auction auction = SearchAlgorithms.getAuction(LoggedIn.getService().getAuctions(), auctionID);

                    initOfferWindow(auction);
                    tableModel.removeRow(row);
                }
            }
        });
    }

    private void initOfferWindow(Auction auction) {
        OfferDialog window = new OfferDialog(auction);
        window.offerWindow.setVisible(true);
    }
}
