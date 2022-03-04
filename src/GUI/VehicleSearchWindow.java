package GUI;

import Entities.Vehicle;
import Enums.VehicleType;
import Search.SearchAlgorithms;
import Utils.LoggedIn;
import List.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleSearchWindow {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JLabel modelLabel;
    private JTextField modelField;
    private JLabel manifacturerLabel;
    private JTextField manifacturerField;
    private JLabel yearLabel;
    private JTextField yearField;
    private JLabel registrationLabel;
    private JTextField registrationField;
    private JLabel taxiIdLabel;
    private JTextField taxiIdField;
    private JButton searchBtn;
    private JPanel tablePanel;
    private  JScrollPane tablePane;
    private  JTable vehicleTable;
    public  DefaultTableModel tableModel;
    public static JFrame vehicleWindow;
    private LinkedList<Vehicle> list = SearchAlgorithms.multipleCriteriaCarSearch("","","","","");


    public VehicleSearchWindow() {
        initActions();
        createTable();

        vehicleWindow = new JFrame();
        vehicleWindow.setTitle("Pretraga vozila");
        vehicleWindow.setContentPane(this.mainPanel);
        vehicleWindow.setLocationRelativeTo(null);
        vehicleWindow.pack();

    }

    private void initActions() {
        this.searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String model = modelField.getText().toLowerCase();
                String manifacturer = manifacturerField.getText().toLowerCase();
                String year = yearField.getText().toLowerCase();
                String registration = registrationField.getText().toLowerCase();
                String taxiID = taxiIdField.getText().toLowerCase();

               list = SearchAlgorithms.multipleCriteriaCarSearch(model,manifacturer,year,registration,taxiID);
               tablePanel.remove(tablePane);
               createTable();
               tablePanel.revalidate();
               tablePanel.repaint();
            }
        });
    }

    private void createTable() {
        String[] header = new String[]{"Broj u sluzbi:", "Proizvodjac:", "Model:", "Godina proizvodjne:", "Registarske tablice:", "Voazc",
                "Tip:", "Pet friendly:"};
        Object[][] content = new Object[list.size()][header.length];

        for (int i = 0; i < list.size(); i++) {
            Vehicle vehicle = list.get(i);
            content[i][0] = vehicle.getTaxiLabel();
            content[i][1] = vehicle.getManifacturer();
            content[i][2] = vehicle.getModel();
            content[i][3] = vehicle.getYearOfManifacture();
            content[i][4] = vehicle.getRegistrationNumber();
            content[i][5] = vehicle.getDriver();
            content[i][6] = vehicle.getType() == VehicleType.CAR ? "Automobil" : "Kombi";
            content[i][7] = vehicle.isPetFriendly() == true ? "Da" : "Ne";
        }

        tableModel = new DefaultTableModel(content, header);
        vehicleTable = new JTable(tableModel);

        vehicleTable.setRowSelectionAllowed(true);
        vehicleTable.setColumnSelectionAllowed(false);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehicleTable.setDefaultEditor(Object.class, null);
        vehicleTable.getTableHeader().setReorderingAllowed(false);
        vehicleTable.setAutoCreateRowSorter(true);

        tablePane = new JScrollPane(vehicleTable);
        tablePanel.add(tablePane);
    }
}
