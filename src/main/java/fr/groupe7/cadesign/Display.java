package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Display implements ActionListener {
    private JFrame window = new JFrame();
    private JMenu accessMenu = new JMenu("Se connecter / S'enregistrer");
    private JMenuBar cho = new JMenuBar();
    private JMenuItem connexion = new JMenuItem("Se connecter");
    private JMenuItem registration = new JMenuItem("S'inscrire");
    private JLabel firstNameLabel = new JLabel("Prénom");
    private JLabel lastNameLabel = new JLabel("Nom");
    private JLabel mailLabel = new JLabel("E-Mail");
    private JLabel passLabel = new JLabel("Password");
    private JButton logIn = new JButton("Connexion");
    private JButton signUp = new JButton("Inscription");
    private JPanel registerPanel = new JPanel();
    private JPanel connexionPanel = new JPanel();
    private JPanel logInPanel = new JPanel();

    Box l1 = Box.createHorizontalBox();
    Box l2 = Box.createHorizontalBox();
    Box l3 = Box.createHorizontalBox();
    Box l4 = Box.createHorizontalBox();
    Box c1 = Box.createVerticalBox();
    JPanel crudPanel = new JPanel();
    JMenu crudMenu = new JMenu("CRUD MENU");
    JMenuBar crudBar = new JMenuBar();
    JMenu createMenu = new JMenu("Create");
    JMenu readMenu = new JMenu("Read");
    JMenu updateMenu = new JMenu("Update");
    JMenu deleteMenu = new JMenu("Delete");
    JMenuItem projects = new JMenuItem("Projects");
    JMenuItem account = new JMenuItem("Account");
    JMenuItem users = new JMenuItem("Users");
    JButton disconnect = new JButton("Disconnect");
    UpdateProfile updateProfile = new UpdateProfile();

    JTextField firstName = new JTextField(10);
    JTextField lastName = new JTextField(10);
    JTextField userMail = new JTextField(10);
    JPasswordField passWord = new JPasswordField(10);
    JCheckBox checkbox = new JCheckBox("Retenir mes informations");
    CheckIni checkIni = new CheckIni();
    LogRegActions logRegActions = new LogRegActions();
    String[] userIdNameRole = new String[] {"a", "b", "c"};
    int userID;
    String userFirstName;
    String userLastName;
    String userRole;
    Connection connection;

    JLabel newMailLabel = new JLabel("Change mail adress");
    JLabel confirmPassLabel = new JLabel("Actual password *");
    JLabel newPassLabel = new JLabel("New password");
    JTextField newMail = new JTextField(15);
    JTextField confirmPass = new JTextField(15);
    JTextField newPass = new JTextField(15);
    JButton confirm = new JButton("Confirm");

    public void setWindow() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/bdd_ca_design",
                "anthony", "Atelier2");
        window.setTitle("CA DESIGN - HOME");
        window.setSize(1200, 1000);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout()); //Adapter taille des boutons
        //Ajouter Connexion/s'enregistrer sur le menu
        accessMenu.add(connexion);
        accessMenu.add(registration);
        connexion.addActionListener(this);
        registration.addActionListener(this);
        cho.add(accessMenu);
        l1.add(cho);
        c1.add(l1);
        window.add(c1);
        checkIni.check(userMail, passWord);
        window.getContentPane().add(logInPanel);
        window.setVisible(true);
    }

    /**
     * S'exécute lorsque l'utilisateur choisis de s'inscrire
     */
    private void displayUserRegistration(){

        window.getContentPane().remove(connexionPanel);
        l2.add(firstNameLabel);
        l2.add(lastNameLabel);
        l2.add(mailLabel);
        l2.add(passLabel);
        l3.add(firstName);
        l3.add(lastName);
        l3.add(userMail);
        l3.add(passWord);
        l4.add(signUp);
        signUp.addActionListener(this);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        registerPanel.add(c1);
        window.add(registerPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Modifie la fenêtre pour permettre à l'utilisateur de se connecter
     */
    private void displayUserConnexion(){
        window.getContentPane().remove(registerPanel);
        l2.add(mailLabel);
        l2.add(passLabel);
        l3.add(userMail);
        l3.add(passWord);
        l3.add(checkbox);
        l4.add(logIn);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        connexionPanel.add(c1);

        logIn.addActionListener(this);

        window.add(connexionPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Créer le menu principal CRUD selon le rôle de l'utilisateur
     */
    private void displayMainCrudMenu(){
        setCrudperms();
        window.setTitle("CA DESIGN - Welcome [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName + "!");
        window.getContentPane().removeAll();
        window.revalidate();
        window.repaint();
        crudMenu.add(createMenu);
        crudMenu.add(readMenu);
        crudMenu.add(updateMenu);
        crudMenu.add(deleteMenu);
        crudBar.add(crudMenu);
        l1.add(crudBar);
        l3.add(disconnect);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        crudPanel.add(c1);
        window.getContentPane().add(crudPanel);
        window.setVisible(true);

        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("USERS");
            }
        });
        projects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PROJEEETS");
            }
        });
        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("TEST UPDATE PROFILE");
                profileMenu();
            }
        });
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    System.out.println("Successfully disconnected.");
                    setWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCrudperms() {
        setDefaultPerms();
        switch (userRole) {
            case "customer":
                setCustPerms();
                break;
            case "architect":
                setArchPerms();
                break;
            case "admin":
                setAdminPerms();
                break;
        }
    }

    private void setDefaultPerms() {
        updateMenu.add(account);
    }

    private void setCustPerms() {
        readMenu.add(projects);
    }

    private void setArchPerms() {
        createMenu.add(projects);
    }

    private void setAdminPerms() {
        createMenu.add(users);
        createMenu.add(projects);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == connexion)
            displayUserConnexion();
        else if (actionEvent.getSource() == registration)
            displayUserRegistration();
        else if (actionEvent.getSource() == signUp) {
            logRegActions.checkRegister(firstName, lastName, userMail, passWord, connection);
        }
        else if (actionEvent.getSource() == logIn) {
            userIdNameRole = logRegActions.checkLogIn(userMail, passWord, connection);
            userID = Integer.parseInt(userIdNameRole[0]);
            userFirstName = userIdNameRole[1];
            userLastName = userIdNameRole[2];
            userRole = userIdNameRole[3];
            displayMainCrudMenu();
        }
    }

    private void profileMenu() {
        window.getContentPane().removeAll();
        window.add(newMailLabel);
        window.add(confirmPassLabel);
        window.add(newMail);
        window.add(confirmPass);
        window.add(newPassLabel);
        window.add(newPass);
        window.add(confirm);
        window.setVisible(true);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile.updateMenu(userID, newMail, passWord, newPass, connection);
            }
        });
    }
}
