package GUI;

import Entities.Dispatcher;
import Entities.Driver;
import Entities.Person;
import Entities.User;
import Utils.LoggedIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public JPanel LoginPanel;
    private JButton loginButton;
    private JButton exitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    public static JFrame login;


    public Login() {
        login = new JFrame("Prijava");
        login.setContentPane(this.LoginPanel);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.pack();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().trim() == "" || String.valueOf(passwordField.getPassword()).trim() == "") {
                    JOptionPane.showMessageDialog(null, "Morate uneti sve podatke", "Greska", JOptionPane.WARNING_MESSAGE);
                } else {
                    Login(usernameField.getText().trim(), String.valueOf(passwordField.getPassword()).trim());

                    if (LoggedIn.getLoggedUser() == null) {
                        JOptionPane.showMessageDialog(null, "Pogrešni login podaci.", "Greška", JOptionPane.WARNING_MESSAGE);
                    } else {
                        login.dispose();
                        login.setVisible(false);

                        if (LoggedIn.getLoggedUser() instanceof User) {
                            initUserWindow();
                        } else if (LoggedIn.getLoggedUser() instanceof Driver) {
                            initDriverMenu();
                        } else if (LoggedIn.getLoggedUser() instanceof Dispatcher) {
                            initDispatcherMenu();
                        }
                    }
                }
            }
        });

    }

    public static void Login(String username, String password) {
        for (Person p : LoggedIn.getService().getUsers()) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password) && !p.isDeleted()) {
                LoggedIn.setLoggedUser(p);
                return;
            }
        }
        LoggedIn.setLoggedUser(null);
    }

    private void initDispatcherMenu() {
        DispatcherMenu menu = new DispatcherMenu();
        menu.mainMenuWindow.setVisible(true);
    }

    private void initDriverMenu() {
        DriverMenu menu = new DriverMenu();
        menu.mainMenuWindow.setVisible(true);
    }

    private void initUserWindow() {
        UserMenu menu = new UserMenu();
        menu.mainMenuWindow.setVisible(true);
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
        LoginPanel = new JPanel();
        LoginPanel.setLayout(new GridBagLayout());
        LoginPanel.setPreferredSize(new Dimension(300, 100));
        final JLabel label1 = new JLabel();
        label1.setText("Korisnicko ime:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 0, 0);
        LoginPanel.add(label1, gbc);
        usernameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);
        LoginPanel.add(usernameField, gbc);
        loginButton = new JButton();
        loginButton.setText("Prijava");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        LoginPanel.add(loginButton, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Lozinka:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 20, 0, 0);
        LoginPanel.add(label2, gbc);
        passwordField = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);
        LoginPanel.add(passwordField, gbc);
        exitButton = new JButton();
        exitButton.setText("Izlaz");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        LoginPanel.add(exitButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return LoginPanel;
    }

}
