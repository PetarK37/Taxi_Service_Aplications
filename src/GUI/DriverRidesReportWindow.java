package GUI;

import Controllers.DriverRidesReportController;
import Entities.Driver;
import Entities.DriverRidesReport;
import Entities.Vehicle;
import Enums.VehicleType;
import List.LinkedList;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriverRidesReportWindow {
    private JPanel mainPanel;
    private JPanel imputPanel;
    private JPanel tablePanel;
    private JLabel yearLabel;
    private JTextField yearInputField;
    private JLabel monthLabel;
    private JComboBox monthBox;
    private JLabel dayLabel;
    private JTextField dayTextField;
    private JButton yearlyReportBtn;
    private JButton monthlyReportBtn;
    private JButton weeklyReportBtn;
    private JButton dalyReportBtn;
    private  JScrollPane tablePane;
    private  JTable reportsTable;
    public DefaultTableModel tableModel;
    private DriverRidesReport report = new DriverRidesReport();
    public static JFrame reportsWindow;


    public DriverRidesReportWindow() {
        fillInCombobox();
        initActions();
        createTable();

        reportsWindow = new JFrame();
        reportsWindow.setTitle("Statistika voznji");
        reportsWindow.setContentPane(this.mainPanel);
        reportsWindow.setLocationRelativeTo(null);
        reportsWindow.pack();
    }



    private void fillInCombobox(){
        String months[] = new String[]{"Januar","Februar","Mart","April","Maj","Jun","Jul","Avgust","Septembar","Oktobar","Novembar","Decembar"};
        for (int i =0; i < months.length; i++){
            this.monthBox.addItem(months[i]);
        }
    }

        private boolean validation() {
            boolean isValid = true;
            String message = "Molimo vas ispravite sledece greske prilkom unosa:\n";
            if (this.yearInputField.getText().trim().equals("")) {
                message += "Morate uneti godinu \n";
                isValid = false;
            }
            try {
                Integer.parseInt(this.yearInputField.getText().trim());
            } catch (NumberFormatException e) {
                message += "Godina  mora biti broj";
                isValid = false;
            }

            if (isValid == false) {
                JOptionPane.showMessageDialog(null, message, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
            }
            return isValid;
        }

        private boolean validateDay(){
            boolean isValid = true;
            if (dayTextField.getText().trim().equals("")){
                isValid = false;
                JOptionPane.showMessageDialog(null, "Morate uneti dan", "Neispravni podaci", JOptionPane.WARNING_MESSAGE);

            } try {
                Integer.parseInt(dayTextField.getText().trim());
            }catch (NumberFormatException e){
                isValid = false;
                JOptionPane.showMessageDialog(null, "Dan mora biti broj", "Neispravni podaci", JOptionPane.WARNING_MESSAGE);

            }
            return isValid;
        }
    private void initActions() {
        this.yearlyReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation()){
                    int year = Integer.parseInt(yearInputField.getText().trim());

                    report = DriverRidesReportController.initYearlyReport(year, (Driver) LoggedIn.getLoggedUser());
                    tablePanel.remove(tablePane);
                    createTable();
                    tablePanel.revalidate();
                    tablePanel.repaint();
                }
            }
        });
        this.monthlyReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation()){
                    String year = yearInputField.getText().trim();
                    String month = String.valueOf(monthBox.getSelectedItem());
                    String day = dayTextField.getText().trim().equals("")? "1" : dayTextField.getText().trim();

                    String date = day + "-" + month + "-" + year;

                    report = DriverRidesReportController.initMonthlyReport(date,(Driver) LoggedIn.getLoggedUser());
                    tablePanel.remove(tablePane);
                    createTable();
                    tablePanel.revalidate();
                    tablePanel.repaint();
                }
            }
        });

        this.weeklyReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation() && validateDay()){
                    String year = yearInputField.getText().trim();
                    String month = String.valueOf(monthBox.getSelectedItem());

                    String day = dayTextField.getText().trim();

                    String date = day + "-" + month + "-" + year;

                    report = DriverRidesReportController.initWeeklyRepot(date,(Driver) LoggedIn.getLoggedUser());
                    tablePanel.remove(tablePane);
                    createTable();
                    tablePanel.revalidate();
                    tablePanel.repaint();
                }
            }
        });

        this.dalyReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation() && validateDay()){
                    String year = yearInputField.getText().trim();
                    String month = String.valueOf(monthBox.getSelectedItem());

                    String day = dayTextField.getText().trim();

                    String date = day + "-" + month + "-" + year;

                    report = DriverRidesReportController.initDalyReport(date,(Driver) LoggedIn.getLoggedUser());
                    tablePanel.remove(tablePane);
                    createTable();
                    tablePanel.revalidate();
                    tablePanel.repaint();
                }
            }
        });
    }

    private void createTable() {
        String[] header = new String[]{"Broj voznji:", "Broj kilometara:", "Prosecno kilometara po voznji:" , "Trajanje voznji:", "Prosecno trajanje voznje:", "Slobodno vreme",
                "Ukupna zarada:", "Prosecna zarada:"};
        Object[][] content = new Object[1][header.length];

            content[0][0] = report.getNumOfRides();
            content[0][1] = report.getNumOfKm() + " km";
            content[0][2] = String.format("%.3f",report.getAvrageKmPerRide()) + " km";
            content[0][3] = report.getDuration() + " min";
            content[0][4] = String.format("%.3f",report.getAvrageDuration()) + " min";
            content[0][5] = report.getFreeTime() + " min";
            content[0][6] = report.getSalary() + " din";
            content[0][7] = String.format("%.3f",report.getAvrageSalary()) + " din";

        tableModel = new DefaultTableModel(content, header);
        reportsTable = new JTable(tableModel);

        reportsTable.setRowSelectionAllowed(true);
        reportsTable.setColumnSelectionAllowed(false);
        reportsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportsTable.setDefaultEditor(Object.class, null);
        reportsTable.getTableHeader().setReorderingAllowed(false);
        reportsTable.setAutoCreateRowSorter(true);

        tablePane = new JScrollPane(reportsTable);
        tablePanel.add(tablePane);
    }

}
