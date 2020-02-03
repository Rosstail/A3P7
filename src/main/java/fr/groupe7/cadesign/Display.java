package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class Display implements ActionListener {
    JFrame window = new JFrame();
    JMenu accessMenu = new JMenu("Se connecter / S'enregistrer");
    JMenuBar cho = new JMenuBar();
    JMenuItem connexion = new JMenuItem("Se connecter");
    JMenuItem registration = new JMenuItem("S'inscrire");
    JLabel firstNameLabel = new JLabel("Prénom");
    JLabel lastNameLabel = new JLabel("Nom");
    JLabel mailLabel = new JLabel("E-Mail");
    JLabel passLabel = new JLabel("Password");
    JButton logIn = new JButton("Connexion");
    JButton signUp = new JButton("Inscription");
    JPanel registerPanel = new JPanel();
    JPanel connexionPanel = new JPanel();
    JPanel logInPanel = new JPanel();

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
    Actions actions = new Actions();
    Menu menu = new Menu();

    public void setWindow() {
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
    public void displayUserRegistration(){

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
    public void displayUserConnexion(){
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
     * @param role
     */
    public void displayMainCrudMenu(int userID, Connection connection, String role ){
        window.setTitle("CA DESIGN - Welcome " + role + "!");
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
        menu.setMenuPermissions(role);

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
                System.out.println("TEST 1");
            }
        });
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    connection.close();
                    System.out.println("Successfully disconnected.");
                    window.removeAll();
                    window.revalidate();
                    window.setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        crudPanel.add(c1);
        window.getContentPane().add(crudPanel);
        window.setVisible(true);
    }

    public void setDefaultPerms() {
        updateMenu.add(account);
    }

    public void setCustPerms() {
        readMenu.add(projects);
    }

    public void setArchPerms() {
        createMenu.add(projects);
    }

    public void setAdminPerms() {
        createMenu.add(users);
        createMenu.add(projects);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == connexion)
            displayUserConnexion();
        else if (actionEvent.getSource() == registration)
            displayUserRegistration();
        else if (actionEvent.getSource() == signUp)
            actions.checkRegister(firstName, lastName, userMail, passWord);
        else if (actionEvent.getSource() == logIn)
            actions.checkLogIn(userMail, passWord);
    }
}
