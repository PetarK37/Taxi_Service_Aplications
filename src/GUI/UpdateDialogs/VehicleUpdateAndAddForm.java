package GUI.UpdateDialogs;

import Controllers.VehicleController;
import Entities.Driver;
import Entities.Vehicle;
import Enums.VehicleType;
import Exceptions.SaveExcption;
import Exceptions.UpdateException;
import Repositories.UsersFileRepository;
import Repositories.VehiclesFileRepository;
import Search.SearchAlgorithms;
import Utils.LoggedIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VehicleUpdateAndAddForm {
    public JPanel mainPanel;
    private JLabel manifacturerLabel;
    private JTextField manifacturerInput;
    private JLabel modelLabel;
    private JTextField modelInput;
    private JLabel yearLabel;
    private JTextField yearInput;
    private JLabel registrationNumLabel;
    private JTextField registrationNumInput;
    private JComboBox<VehicleType> typeBox;
    private JLabel typeLabel;
    private JCheckBox petFriendly;
    private JButton btnOk;
    private JLabel driverLabel;
    private JComboBox<String> driverBox;
    public static JFrame editWindow;

    private Vehicle vehicle;

    public VehicleUpdateAndAddForm(Vehicle vehicle) {
        this.vehicle = vehicle;

        editWindow = new JFrame();
        editWindow.setContentPane(this.mainPanel);
        editWindow.setLocationRelativeTo(null);
        editWindow.pack();

        if (vehicle == null) {
            editWindow.setTitle("Dodavanje novog vozila: ");
            this.typeBox.setModel(new DefaultComboBoxModel<>(VehicleType.values()));
            fillInDriverBox();
            initAddActions();

        } else {
            editWindow.setTitle("Izmena vozila: ");
            this.manifacturerInput.setEditable(false);
            this.typeBox.setEnabled(false);
            this.yearInput.setEditable(false);
            this.modelInput.setEditable(false);
            fillInData();
            initUpdateActions();

        }
    }


    private void fillInData() {
        this.manifacturerInput.setText(vehicle.getManifacturer());
        this.modelInput.setText(vehicle.getModel());
        this.yearInput.setText(String.valueOf(vehicle.getYearOfManifacture()));
        this.registrationNumInput.setText(vehicle.getRegistrationNumber());
        this.typeBox.setModel(new DefaultComboBoxModel<>(VehicleType.values()));
        this.typeBox.setSelectedItem(vehicle.getType());
        if (vehicle.isPetFriendly() == true) {
            this.petFriendly.setSelected(true);
        } else {
            this.petFriendly.setSelected(false);
        }
        fillInDriverBox();
    }

    ;

    private void fillInDriverBox() {
        for (int i = 0; i < LoggedIn.getService().getNotDeletedDrivers().size(); i++) {
            if (UsersFileRepository.driverIsFree(LoggedIn.getService().getNotDeletedDrivers().get(i)) && LoggedIn.getService().getNotDeletedDrivers().get(i).getVehicle() == null) {
                this.driverBox.addItem(LoggedIn.getService().getNotDeletedDrivers().get(i).getUsername());
            }

        }
        this.driverBox.addItem("Bez vozaca");

        if (vehicle == null || vehicle.getDriver() == null) {
            this.driverBox.setSelectedItem("Bez vozaca");
        } else {
            if (UsersFileRepository.driverIsFree(SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), vehicle.getDriver())) != true) {
                this.driverBox.setEnabled(false);
            }
            this.driverBox.setSelectedItem(SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), vehicle.getDriver()).getUsername());
        }

    }

    private boolean validation() {
        boolean isValid = true;
        String message = "Molimo vas ispravite sledece greske prilkom unosa:\n";
        if (this.manifacturerInput.getText().trim().equals("")) {
            message += "Morate uneti proizvodjaca \n";
            isValid = false;
        }
        if (this.modelInput.getText().trim().equals("")) {
            message += "Morate uneti model \n";
            isValid = false;
        }
        if (this.registrationNumInput.getText().trim().equals("")) {
            message += "Morate uneti broj registracije \n";
            isValid = false;
        }
        try {
            Integer.parseInt(this.yearInput.getText().trim());
        } catch (NumberFormatException e) {
            message += "Godina proizvodnje mora biti \n";
            isValid = false;
        }

        if (isValid == false) {
            JOptionPane.showMessageDialog(null, message, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
        }
        return isValid;
    }

    public void saveUpdateData() {
            try {
                VehicleController.updateVehicle(vehicle, this.petFriendly.isSelected(), this.registrationNumInput.getText().trim(), readDriver());
                VehiclesFileRepository.writeAllVehicles(LoggedIn.getService().getVehicles());
                UsersFileRepository.writeAllUsers(LoggedIn.getService().getUsers());


                JOptionPane.showMessageDialog(null, "Uspesno sacuvani podaci", "Cuvanje podataka", JOptionPane.INFORMATION_MESSAGE);
            } catch (UpdateException e) {
                JOptionPane.showMessageDialog(null, "Nemoguce izmeniti vozilo", "Vozac ima zakazane voznje", JOptionPane.WARNING_MESSAGE);
            } catch (SaveExcption e) {
                JOptionPane.showMessageDialog(null, "Greska prilikom pisanja u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
            }
    }

    private Driver readDriver() {
        if (this.driverBox.getSelectedItem().equals("Bez vozaca")) {
            return null;
        } else {
            return SearchAlgorithms.getDriver(LoggedIn.getService().getUsers(), String.valueOf(this.driverBox.getSelectedItem()));
        }
    }

    private void initUpdateActions() {
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation()) {
                    saveUpdateData();
                    editWindow.dispose();
                    editWindow.setVisible(false);
                }
            }
        });
    }

    private void initAddActions() {
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation()) {
                    saveData();
                    editWindow.dispose();
                    editWindow.setVisible(false);
                }
            }
        });
    }

    private void saveData() {
            try {
                Vehicle newVehicle = new Vehicle(this.modelInput.getText().trim(), this.manifacturerInput.getText().trim()
                        , Integer.parseInt(this.yearInput.getText().trim()), this.registrationNumInput.getText().trim(),
                        VehicleController.generateId(LoggedIn.getService().getVehicles()), (VehicleType) this.typeBox.getSelectedItem(),
                        this.petFriendly.isSelected(), readDriver());
                VehicleController.addNewVehicle(LoggedIn.getService().getVehicles(), newVehicle);

                VehiclesFileRepository.writeAllVehicles(LoggedIn.getService().getVehicles());
                UsersFileRepository.writeAllUsers(LoggedIn.getService().getUsers());

                JOptionPane.showMessageDialog(null, "Uspesno sacuvani podaci", "Cuvanje podataka", JOptionPane.INFORMATION_MESSAGE);
            } catch (SaveExcption e) {
                JOptionPane.showMessageDialog(null, "Greska prilikom pisanja u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
            }
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
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(380, 350));
        manifacturerLabel = new JLabel();
        manifacturerLabel.setText("Proizvodjac:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        mainPanel.add(manifacturerLabel, gbc);
        manifacturerInput = new JTextField();
        manifacturerInput.setPreferredSize(new Dimension(180, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(manifacturerInput, gbc);
        modelLabel = new JLabel();
        modelLabel.setText("Model:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        mainPanel.add(modelLabel, gbc);
        modelInput = new JTextField();
        modelInput.setPreferredSize(new Dimension(180, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(modelInput, gbc);
        yearLabel = new JLabel();
        yearLabel.setText("Godina proizvodnje:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        mainPanel.add(yearLabel, gbc);
        yearInput = new JTextField();
        yearInput.setPreferredSize(new Dimension(180, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(yearInput, gbc);
        registrationNumLabel = new JLabel();
        registrationNumLabel.setText("Registraciona oznaka:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        mainPanel.add(registrationNumLabel, gbc);
        registrationNumInput = new JTextField();
        registrationNumInput.setPreferredSize(new Dimension(180, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(registrationNumInput, gbc);
        typeLabel = new JLabel();
        typeLabel.setText("Tip vozila:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        mainPanel.add(typeLabel, gbc);
        btnOk = new JButton();
        btnOk.setText("Potvrdi");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(btnOk, gbc);
        typeBox = new JComboBox();
        typeBox.setEditable(true);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        typeBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(typeBox, gbc);
        petFriendly = new JCheckBox();
        petFriendly.setText("Pet friendly");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(petFriendly, gbc);
        driverBox = new JComboBox();
        driverBox.setEditable(true);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        driverBox.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(driverBox, gbc);
        driverLabel = new JLabel();
        driverLabel.setText("Vozac:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        mainPanel.add(driverLabel, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
