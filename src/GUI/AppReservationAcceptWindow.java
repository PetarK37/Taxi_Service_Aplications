package GUI;


import Controllers.AuctionController;
import Controllers.DatesController;
import Controllers.RideController;
import Entities.*;
import Enums.ReservationStatus;
import Exceptions.SaveExcption;
import Exceptions.UpdateException;
import GUI.UpdateDialogs.FinishRideForm;
import Repositories.AuctionFileRepository;
import Repositories.RidesFileRepository;
import Search.SearchAlgorithms;
import Utils.LoggedIn;
import List.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AppReservationAcceptWindow {
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JButton acceptBtn;
    private JButton declineBtn;
    private JButton finishBtn;
    private JScrollPane tablePane;
    private JTable ridesTable;
    public static DefaultTableModel tableModel;
    public static JFrame ridesWindow;

    public AppReservationAcceptWindow() {
        createTable();
        initActions();

        ridesWindow = new JFrame();
        ridesWindow.setTitle("Moje voznje");
        ridesWindow.setContentPane(this.mainPanel);
        ridesWindow.setLocationRelativeTo(null);
        ridesWindow.pack();

    }

    private void initActions() {
        this.declineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ridesTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = ridesTable.convertRowIndexToModel(row);

                    int rideID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Ride ride = SearchAlgorithms.getRide(LoggedIn.getService().getRides(), rideID);

                    int choice = JOptionPane.showConfirmDialog(null,
                            "Da li ste sigurni da zelite da odbijete voznju?",
                            ride.getRideNumber() + " - Porvrda", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        if (ride.getStatus() != ReservationStatus.ASSIGNED){
                            JOptionPane.showMessageDialog(null, "Ne mozete odbiti voznju" , "Greska", JOptionPane.WARNING_MESSAGE);
                        }else {
                            RideController.rejectRide(ride);
                        }
                        try {
                            RidesFileRepository.writeAllRides(LoggedIn.getService().getRides());
                        } catch (SaveExcption saveException) {
                            JOptionPane.showMessageDialog(null, "Greska prilikom cuvanja " +
                                    "u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }

            }
        });
        this.acceptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ridesTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = ridesTable.convertRowIndexToModel(row);

                    int rideID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Ride ride = SearchAlgorithms.getRide(LoggedIn.getService().getRides(), rideID);

                    int choice = JOptionPane.showConfirmDialog(null,
                            "Da li ste sigurni da zelite da prihvatite voznju?",
                            ride.getRideNumber() + " - Porvrda", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            RideController.UpdateRideStatus(ride, ReservationStatus.ACCEPTED);
//                            ((Driver) LoggedIn.getLoggedUser()).getDriverRides().add(ride);
                            AuctionController.finishAuction(SearchAlgorithms.getAuctionFromRide(LoggedIn.getService().getAuctions(),ride)
                                    , (Driver) LoggedIn.getLoggedUser());
                        } catch (UpdateException updateException) {
                            JOptionPane.showMessageDialog(null, "Greska prilikom promene statusa",
                                    "Greska", JOptionPane.WARNING_MESSAGE);
                        }
                        try {
                            RidesFileRepository.writeAllRides(LoggedIn.getService().getRides());
                            AuctionFileRepository.writeAllAuctions(LoggedIn.getService().getAuctions());
                        } catch (SaveExcption saveException) {
                            JOptionPane.showMessageDialog(null, "Greska prilikom cuvanja " +
                                    "u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        this.finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ridesTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = ridesTable.convertRowIndexToModel(row);

                    int rideID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Ride ride = SearchAlgorithms.getRide(LoggedIn.getService().getRides(), rideID);

                    if (ride.getStatus() == ReservationStatus.ACCEPTED){
                        initFinishWindow(ride);
                    }else {
                        JOptionPane.showMessageDialog(null, "Ne mozete zavrsiti voznju" ,"Greska", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });
    }


    private void createTable() {
        String[] header = new String[]{"Broj voznje:", "Datum i vreme narucivanja:", "Adresa polaska:", "Adresa dolaska:", "Broj kilometara:", "Trajanje u minutima:", "Cena:", "Status:", "Korisnik",
                "Ocena:", "Napomena:"};
        Object[][] content = new Object[getUnfisihedRides().size()][header.length];


        for (int i = 0; i < getUnfisihedRides().size(); i++) {
            Ride ride = getUnfisihedRides().get(i);

            content[i][0] = ride.getRideNumber();
            content[i][1] = DatesController.dateAndTimeToStr(ride.getDateAndTime());
            content[i][2] = ride.getStartAddress();
            content[i][3] = ride.getArrivalAddress();
            content[i][4] = ride.getNumberOfKm() + "km";
            content[i][5] = ride.getDuration();
            content[i][6] = ride.getPrice();
            content[i][7] = ride.getStatus();
            content[i][8] = ride.getUser().getUsername();
            content[i][9] = ride.getRideRating();
            if (ride instanceof AppReservation) {
                content[i][10] = ((AppReservation) ride).getNote();
            } else {
                content[i][10] = "-";
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

private LinkedList<Ride> getUnfisihedRides() {
    LinkedList<Ride> retList = new LinkedList<Ride>();

    for (int i = 0; i < ((Driver) LoggedIn.getLoggedUser()).getDriverRides().size(); i++) {
        Ride ride = ((Driver) LoggedIn.getLoggedUser()).getDriverRides().get(i);
        if (ride instanceof AppReservation && (ride.getStatus() == ReservationStatus.ACCEPTED || ride.getStatus() == ReservationStatus.ASSIGNED)) {
            retList.add(ride);
        }
    }
    return retList;
}

    private static void initFinishWindow(Ride ride) {
        FinishRideForm window = new FinishRideForm(ride);
        window.finishWindow.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setPreferredSize(new Dimension(1200, 600));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonsPanel.setPreferredSize(new Dimension(1200, 50));
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        acceptBtn = new JButton();
        acceptBtn.setPreferredSize(new Dimension(80, 40));
        acceptBtn.setText("Prihvati");
        buttonsPanel.add(acceptBtn);
        declineBtn = new JButton();
        declineBtn.setPreferredSize(new Dimension(80, 40));
        declineBtn.setText("Odbi");
        buttonsPanel.add(declineBtn);
        finishBtn = new JButton();
        finishBtn.setPreferredSize(new Dimension(80, 40));
        finishBtn.setText("Zavrsi");
        buttonsPanel.add(finishBtn);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}