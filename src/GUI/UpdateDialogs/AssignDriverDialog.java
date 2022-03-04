package GUI.UpdateDialogs;

import Auction.*;
import Controllers.*;
import Entities.*;
import Exceptions.SaveExcption;
import Repositories.*;
import Search.SearchAlgorithms;
import Utils.LoggedIn;
import List.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.GregorianCalendar;

public class AssignDriverDialog {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JLabel title;
    private JPanel buttonsPanel;
    private JButton assignBtn;

    private JScrollPane tablePane;
    private JTable driverTable;
    public static DefaultTableModel tableModel;

    public static JFrame assignWindow;


    private Auction auction;

    public AssignDriverDialog(Auction auction) {
        this.auction = auction;

        assignWindow = new JFrame();
        assignWindow.setContentPane(this.mainPanel);
        assignWindow.setLocationRelativeTo(null);
        assignWindow.pack();

        assignWindow.setTitle("Izaberi vozaca : ");
        createTable();
        initActions();

    }

    private void createTable() {
        String[] header = new String[]{"Vozac: ", "Potrebno vreme:", "Ocena vozaca:","Zarada vozaca predhodne nedelje:","Slobodno vreme u minutima za proslu nedelju", "Poeni na aukciji:"};
        Object[][] content = new Object[AuctionController.getSortedOffers(auction).size()][header.length];

        for (int i = 0; i < AuctionController.getSortedOffers(auction).size(); i++) {
            Offer offer = AuctionController.getSortedOffers(auction).get(i);
            content[i][0] = offer.getDriver().getUsername();
            content[i][1] = offer.getDistance();
            content[i][2] = offer.getDriver().getRating();
            content[i][3] = DriverRidesReportController.calculateSalary(weeklyRides(offer)) + "din";
            content[i][4] = DriverRidesReportController.calculateFreeTime(weeklyRides(offer),7);
            content[i][5] = offer.getPoints();
        }


        tableModel = new DefaultTableModel(content, header);
        driverTable = new JTable(tableModel);

        driverTable.setRowSelectionAllowed(true);
        driverTable.setColumnSelectionAllowed(false);
        driverTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        driverTable.setDefaultEditor(Object.class, null);
        driverTable.getTableHeader().setReorderingAllowed(false);

        tablePane = new JScrollPane(driverTable);
        mainPanel.add(tablePane, BorderLayout.CENTER);
    }

    private void initActions() {
        this.assignBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = driverTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    String driverUsername = String.valueOf(tableModel.getValueAt(row, 0));
                    Driver driver = SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), driverUsername);

                    assignRide(driver);
                    assignWindow.dispose();
                    assignWindow.setVisible(false);                }
            }
        });
    }

    private void assignRide(Driver driver) {
        RideController.assignRide(this.auction.getRide(), SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(),driver.getUsername()));
        AuctionController.finishAuction(this.auction,driver);

        JOptionPane.showMessageDialog(null, "Uspesno sacuvani podaci", "Cuvanje podataka", JOptionPane.INFORMATION_MESSAGE);

        try {
            RidesFileRepository.writeAllRides(LoggedIn.getService().getRides());
            AuctionFileRepository.writeAllAuctions(LoggedIn.getService().getAuctions());
        } catch (SaveExcption e) {
            JOptionPane.showMessageDialog(null, "Greska prilikom cuvanja u bazu", "Greska", JOptionPane.WARNING_MESSAGE);
        }
    }

    private LinkedList<Ride> weeklyRides(Offer offer) {
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.DAY_OF_MONTH,-7);

        return DriverRidesReportController.getWeeklyRides(DatesController.dateToStr(date),offer.getDriver());
    }

}
