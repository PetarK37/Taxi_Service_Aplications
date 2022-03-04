package GUI;

import Controllers.DispatcherRidesReportController;
import Controllers.DriverRidesReportController;
import Entities.Driver;
import Entities.DriverRidesReport;
import Entities.Person;
import List.LinkedList;
import Utils.LoggedIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DispatcherRidesReportWidnow {
    private JPanel mainPanel;
    private JPanel imputPanel;
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
    private JPanel tablePanel;

    private JScrollPane tablePane;
    private  JTable reportsTable;
    public DefaultTableModel tableModel;
    private LinkedList<DriverRidesReport>  reports = new LinkedList<DriverRidesReport>();
    public static JFrame reportsWindow;


    public DispatcherRidesReportWidnow() {
        fillInCombobox();
        initActions();
        createTable();

        reportsWindow = new JFrame();
        reportsWindow.setTitle("Statistika voznji po vozacima");
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

                    reports = DispatcherRidesReportController.initYearlyReport(year);
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

                    reports = DispatcherRidesReportController.initMonthlyReport(date);
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

                    reports = DispatcherRidesReportController.initWeeklyRepot(date);
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

                    reports = DispatcherRidesReportController.initDalyReport(date);
                    tablePanel.remove(tablePane);
                    createTable();
                    tablePanel.revalidate();
                    tablePanel.repaint();
                }
            }
        });
    }

    private void createTable(){
        String[] header =new String[]{"Vozac:","Broj voznji:", "Broj kilometara:", "Prosecno kilometara po voznji:" , "Trajanje voznji:", "Prosecno trajanje voznje:", "Slobodno vreme",
                "Ukupna zarada:", "Prosecna zarada:"};;
        Object[][] content = new Object[LoggedIn.getService().getNotDeletedDrivers().size()][header.length];

        if (reports.size() > 0){
            for (int i = 0; i < LoggedIn.getService().getNotDeletedDrivers().size(); i++){
                content[i][0] = reports.get(i).getDriver().getUsername();
                content[i][1] = reports.get(i).getNumOfRides();
                content[i][2] = reports.get(i).getNumOfKm() + " km";;
                content[i][3] = String.format("%.3f",reports.get(i).getAvrageKmPerRide()) + " km";;
                content[i][4] = reports.get(i).getDuration() + " min";
                content[i][5] = String.format("%.3f",reports.get(i).getAvrageDuration()) + " min";
                content[i][6] = reports.get(i).getFreeTime() + " min";
                content[i][7] = reports.get(i).getSalary() + " din";
                content[i][8] = String.format("%.3f",reports.get(i).getAvrageSalary()) + " din";
            }
        }

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
