package GUI;

import Auction.Auction;
import Controllers.DatesController;
import Entities.Ride;
import GUI.UpdateDialogs.*;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuctionReservationAssigmentWindow {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton finishBtn;
    private JScrollPane tablePane;
    private JTable ridesTable;
    public static DefaultTableModel tableModel;
    public static JFrame appRidesWindow;

    public AuctionReservationAssigmentWindow() {
        createTable();
        initActions();

        appRidesWindow = new JFrame();
        appRidesWindow.setTitle("Pregled aukcija za dodelu");
        appRidesWindow.setContentPane(this.mainPanel);
        appRidesWindow.setLocationRelativeTo(null);
        appRidesWindow.pack();
    }

    private void initActions() {
        this.finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ridesTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = ridesTable.convertRowIndexToModel(row);
                    int auctionID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Auction auction = SearchAlgorithms.getAuction(LoggedIn.getService().getAuctions(), auctionID);

                   if (auction.getOffers().size() != 0){
                       initAssignForm(auction);
                   }else {
                       initFreeDriversAssgnForem(auction);
                    }
                }
            }
        });
    }

    private void initFreeDriversAssgnForem(Auction auction) {
        AssignFreeDriversDialog assignDialog = new AssignFreeDriversDialog(auction);
        assignDialog.assignWindow.setVisible(true);
    }


    private void initAssignForm(Auction auction) {
        AssignDriverDialog assignDialog = new AssignDriverDialog(auction);
        assignDialog.assignWindow.setVisible(true);

    }

    private void createTable() {
        String[] header = new String[]{"Id aukcije:", "Datum i vreme narucivanja:", "Korinsik:", "Broj ponuda:"};
        Object[][] content = new Object[LoggedIn.getService().getNotFinishedAuctions().size()][header.length];
        for (int i = 0; i < LoggedIn.getService().getNotFinishedAuctions().size(); i++) {
            Auction auction = LoggedIn.getService().getNotFinishedAuctions().get(i);
            content[i][0] = auction.getId();
            content[i][1] = DatesController.dateAndTimeToStr(auction.getRide().getDateAndTime());
            content[i][2] = auction.getRide().getUser().getUsername();
            content[i][3] = auction.getOffers().size();
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
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
        buttonPanel.setPreferredSize(new Dimension(1200, 50));
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        finishBtn = new JButton();
        finishBtn.setAlignmentX(0.0f);
        finishBtn.setAlignmentY(0.0f);
        finishBtn.setContentAreaFilled(true);
        finishBtn.setHorizontalAlignment(0);
        finishBtn.setMargin(new Insets(0, 0, 0, 0));
        finishBtn.setPreferredSize(new Dimension(106, 40));
        finishBtn.setText("Dodeli voznju");
        buttonPanel.add(finishBtn);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
