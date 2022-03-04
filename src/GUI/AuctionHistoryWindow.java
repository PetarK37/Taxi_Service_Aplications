package GUI;

import Auction.Auction;
import Controllers.DatesController;
import Utils.LoggedIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AuctionHistoryWindow {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JLabel title;

    private JScrollPane tablePane;
    private JTable ridesTable;
    public static DefaultTableModel tableModel;
    public static JFrame auctionsWindow;

    public AuctionHistoryWindow() {
        createTable();

        auctionsWindow = new JFrame();
        auctionsWindow.setTitle("Pregled aukcija za dodelu");
        auctionsWindow.setContentPane(this.mainPanel);
        auctionsWindow.setLocationRelativeTo(null);
        auctionsWindow.pack();
    }

    private void createTable() {
        String[] header = new String[]{"Id aukcije:", "Datum i vreme narucivanja:", "Korinsik:", "Broj ponuda:","Pobednik:"};
        Object[][] content = new Object[LoggedIn.getService().getFinishedAuctions().size()][header.length];
        for (int i = 0; i < LoggedIn.getService().getFinishedAuctions().size(); i++) {
            Auction auction = LoggedIn.getService().getFinishedAuctions().get(i);
            content[i][0] = auction.getId();
            content[i][1] = DatesController.dateAndTimeToStr(auction.getRide().getDateAndTime());
            content[i][2] = auction.getRide().getUser().getUsername();
            content[i][3] = auction.getOffers().size();
            content[i][4] = auction.getWinningDriver().getUsername();
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


}
