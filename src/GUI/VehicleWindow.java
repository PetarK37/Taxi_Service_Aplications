package GUI;

import Entities.Vehicle;
import Enums.VehicleType;
import Exceptions.DeleteException;
import Exceptions.SaveExcption;
import GUI.UpdateDialogs.VehicleUpdateAndAddForm;
import Repositories.VehiclesFileRepository;
import Search.SearchAlgorithms;
import Utils.Constants;
import Utils.LoggedIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VehicleWindow {
    public JPanel mainPanel;
    private JPanel buttonsPanel;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JScrollPane tablePane;
    private JTable vehicleTable;
    public static DefaultTableModel tableModel;
    public static JFrame vehicleWindow;

    public VehicleWindow() {
        createTable();
        initActions();

        vehicleWindow = new JFrame();
        vehicleWindow.setTitle("Sva vozila");
        vehicleWindow.setContentPane(this.mainPanel);
        vehicleWindow.setLocationRelativeTo(null);
        vehicleWindow.pack();
    }

    private void createTable() {
        String[] header = new String[]{"Broj u sluzbi:", "Proizvodjac:", "Model:", "Godina proizvodjne:", "Registarske tablice:", "Voazc",
                "Tip:", "Pet friendly:"};
        Object[][] content = new Object[LoggedIn.getService().getNotDeletedVehicles().size()][header.length];


        for (int i = 0; i < LoggedIn.getService().getNotDeletedVehicles().size(); i++) {
            Vehicle vehicle = LoggedIn.getService().getNotDeletedVehicles().get(i);
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
        mainPanel.add(tablePane, BorderLayout.CENTER);

    }

    private void initActions() {
        this.btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = vehicleTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = vehicleTable.convertRowIndexToModel(row);
                    int vehicleID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Vehicle vehicle = SearchAlgorithms.getCar(LoggedIn.getService().getVehicles(), vehicleID);

                    int choice = JOptionPane.showConfirmDialog(null,
                            "Da li ste sigurni da zelite da obrisete automobil?",
                            vehicle.getModel() + " - Porvrda brisanja", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            VehiclesFileRepository.deleteVehicle(LoggedIn.getService().getVehicles(), vehicle);
                            tableModel.removeRow(row);
                            VehiclesFileRepository.writeAllVehicles(LoggedIn.getService().getVehicles());

                        } catch (DeleteException deleteException) {
                            JOptionPane.showMessageDialog(null, "Nije moguce obrisati " +
                                    "zeljeno vozilo", "Greska", JOptionPane.WARNING_MESSAGE);
                        } catch (SaveExcption saveExcption) {
                            JOptionPane.showMessageDialog(null, "Greska prilikom cuvanja " +
                                    "u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        this.btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initEditForm(null);
            }
        });

        this.btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = vehicleTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    row = vehicleTable.convertRowIndexToModel(row);
                    int vehicleID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    Vehicle vehicle = SearchAlgorithms.getCar(LoggedIn.getService().getVehicles(), vehicleID);

                    initEditForm(vehicle);
                }
            }
        });
    }

    private void initEditForm(Vehicle vehicle) {
        VehicleUpdateAndAddForm updateAndEditWindow = new VehicleUpdateAndAddForm(vehicle);
        updateAndEditWindow.editWindow.setVisible(true);
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
        mainPanel.setPreferredSize(new Dimension(650, 400));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
        buttonsPanel.setPreferredSize(new Dimension(500, 50));
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        btnAdd = new JButton();
        btnAdd.setInheritsPopupMenu(false);
        btnAdd.setMargin(new Insets(0, 0, 0, 0));
        btnAdd.setText("Dodaj novog");
        buttonsPanel.add(btnAdd);
        btnEdit = new JButton();
        btnEdit.setMargin(new Insets(0, 0, 0, 0));
        btnEdit.setText("Izmeni");
        buttonsPanel.add(btnEdit);
        btnDelete = new JButton();
        btnDelete.setMargin(new Insets(0, 0, 0, 0));
        btnDelete.setText("Obrisi");
        buttonsPanel.add(btnDelete);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}


