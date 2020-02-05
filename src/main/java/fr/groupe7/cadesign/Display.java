
package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Every displays inside the JFrame
 */
public class Display implements ActionListener {
    private JFrame window = new JFrame();
    private JMenu accessMenu = new JMenu("Se connecter / S'enregistrer");
    private JMenuBar cho = new JMenuBar();
    private JMenuItem connexion = new JMenuItem("Se connecter");
    private JMenuItem registration = new JMenuItem("S'inscrire");
    private JButton logIn = new JButton("Connexion");
    private JButton signUp = new JButton("Inscription");
    private JPanel panel = new JPanel();

    private Box l1 = Box.createHorizontalBox();
    private Box l2 = Box.createHorizontalBox();
    private Box l3 = Box.createHorizontalBox();
    private Box l4 = Box.createHorizontalBox();
    private Box l5 = Box.createHorizontalBox();
    private Box c1 = Box.createVerticalBox();
    private JButton disconnect = new JButton("Disconnect");
    private UpdateProfile updateProfile = new UpdateProfile();
    private JTextField userMailJTF = new JTextField(10);
    private JPasswordField passWord = new JPasswordField(10);

    private Connection connection;

    /**
     * Initialize the connection to the database, call for displays after
     * @throws SQLException
     */
    protected void setConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/bdd_ca_design",
                "anthony", "Atelier2");
        setWindow();
    }

    /**
     * Displays the main window (COnnection / logIn)
     */
    private void setWindow() {
        //*** SET GENERALS INFO OF THE WINDOW ***
        window.setTitle("CA DESIGN - HOME");
        window.setSize(1200, 1000);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        //***************************************

        resetWindow();
        accessMenu.add(connexion);
        accessMenu.add(registration);
        connexion.addActionListener(this);
        registration.addActionListener(this);
        cho.add(accessMenu);
        l1.add(cho);
        c1.add(l1);
        window.add(c1);
        checkIni.check(userMailJTF, passWord);
        window.getContentPane().add(panel);
        window.setVisible(true);
    }

    private JTextField firstName = new JTextField(10);
    private JTextField lastName = new JTextField(10);
    private JCheckBox checkbox = new JCheckBox("Retenir mes informations");
    private CheckIni checkIni = new CheckIni();
    private LogRegActions logRegActions = new LogRegActions();
    private int userID;
    private String userFirstName;
    private String userLastName;
    private String userRole;

    /**
     * S'exécute lorsque l'utilisateur choisis de s'inscrire
     */
    private void displayUserRegistration(){
        resetWindow();

        accessMenu.add(connexion);
        accessMenu.add(registration);
        connexion.addActionListener(this);
        registration.addActionListener(this);
        cho.add(accessMenu);
        l1.add(cho);
        l2.add(new JLabel("Prénom"));
        l2.add(new JLabel("Nom"));
        l2.add(new JLabel("E-Mail"));
        l2.add(new JLabel("Password"));
        l3.add(firstName);
        l3.add(lastName);
        l3.add(userMailJTF);
        l3.add(passWord);
        l4.add(signUp);
        signUp.addActionListener(this);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        c1.add(l5);
        panel.add(c1);
        window.add(panel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Modifie la fenêtre pour permettre à l'utilisateur de se connecter
     */
    private void displayUserConnexion(){
        resetWindow();

        accessMenu.add(connexion);
        accessMenu.add(registration);
        connexion.addActionListener(this);
        registration.addActionListener(this);
        cho.add(accessMenu);
        l1.add(cho);
        l2.add(new JLabel("E-Mail"));
        l2.add(new JLabel("Password"));
        l3.add(userMailJTF);
        l3.add(passWord);
        l3.add(checkbox);
        l4.add(logIn);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);

        logIn.addActionListener(this);

        window.add(panel);
        window.revalidate();
        window.repaint();
    }

    private JMenuBar crudBar = new JMenuBar();
    private JMenu crudMenu = new JMenu("CRUD MENU");
    private JMenu createMenu = new JMenu("Create");
    private JMenu readMenu = new JMenu("Read");
    private JMenu updateMenu = new JMenu("Update");
    private JMenu deleteMenu = new JMenu("Delete");
    private JMenuItem projects = new JMenuItem("Projects");
    private JMenuItem account = new JMenuItem("Account");
    private JMenuItem users = new JMenuItem("Users");
    private JMenuItem selfProfile = new JMenuItem("Your Profile");

    /**
     * Créer le menu principal CRUD selon le rôle de l'utilisateur
     */
    private void displayMainCrudMenu(){
        resetWindow();
        setCrudperms();
        //general widnow properties
        window.setTitle("CA DESIGN - Welcome [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
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
        panel.add(c1);
        window.add(panel);
        window.revalidate();
        window.repaint();

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
                profileMenu();
            }
        });
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Successfully disconnected.");
                setWindow();
            }
        });
        selfProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Consulting your profile");
                selfProfileCheck();
            }
        });
    }

    /**
     * Determine the role of the user
     */
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

    /**
     * Gives the perms of CRUD MENU to every users
     */
    private void setDefaultPerms() {
        readMenu.add(selfProfile);
        updateMenu.add(account);
    }

    /**
     * Gives the perms of CRUD MENU to all Customers
     */
    private void setCustPerms() {
        readMenu.add(projects);
    }

    /**
     * Gives the perms of CRUD MENU to Architects
     */
    private void setArchPerms() {
        createMenu.add(projects);
    }

    /**
     * Gives every permsof CRUD MENU to Admin
     */
    private void setAdminPerms() {
        createMenu.add(users);
        createMenu.add(projects);
    }

    private String userMail;
    private String userSignDateTime;
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == connexion)
            displayUserConnexion();
        else if (actionEvent.getSource() == registration)
            displayUserRegistration();
        else if (actionEvent.getSource() == signUp) {
            logRegActions.checkRegister(firstName, lastName, userMailJTF, passWord, connection);
        }
        else if (actionEvent.getSource() == logIn) {
            String[] userIdNameRole = logRegActions.checkLogIn(userMailJTF, passWord, connection);
            userID = Integer.parseInt(userIdNameRole[0]);
            userFirstName = userIdNameRole[1];
            userLastName = userIdNameRole[2];
            userMail = userIdNameRole[3];
            userRole = userIdNameRole[4];
            userSignDateTime = userIdNameRole[5];
            displayMainCrudMenu();
        }
    }

    private void selfProfileCheck() {
        resetWindow();
        window.setTitle("CA DESIGN - YOUR PROFILE [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        l1.add(new JLabel("   First name   "));
        l1.add(new JLabel("   Last name   "));
        l1.add(new JLabel("   Mail adress  "));
        l1.add(new JLabel("   Role   "));
        l1.add(new JLabel("   Sign datetime   "));
        l2.add(new JLabel(userFirstName));
        l2.add(new JLabel(userLastName));
        l2.add(new JLabel(userMail));
        l2.add(new JLabel(userRole));
        l2.add(new JLabel(userSignDateTime));
        l3.add(back);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMainCrudMenu();
            }
        });
    }

    private JTextField newMail = new JTextField(15);
    private JTextField confirmPass = new JTextField(15);
    private JTextField newPass = new JTextField(15);
    private JButton confirm = new JButton("Confirm");
    private JButton back = new JButton("Back");
    /**
     * Displays the update profile menu
     */
    private void profileMenu() {
        resetWindow();
        window.setTitle("CA DESIGN - UPDATE PROFILE [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        l1.add(new JLabel("Change mail adress"));
        l1.add(new JLabel("password"));
        l2.add(newMail);
        l2.add(confirmPass);
        l3.add(new JLabel("New password"));
        l4.add(newPass);
        l4.add(confirm);
        l5.add(back);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        c1.add(l5);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile.updateMenu(userID, newMail, passWord, newPass, connection);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMainCrudMenu();
            }
        });
    }

    /**
     * Reset every content of the window
     * Must be called before every display
     */
    private void resetWindow() {

        //MENUS AND MENULIST
        accessMenu.removeAll();
        accessMenu.revalidate();
        accessMenu.repaint();
        cho.removeAll();
        cho.revalidate();
        cho.repaint();

        //JTEXT AND JPASS
        userMailJTF.setText(null);
        passWord.setText(null);
        newMail.setText(null);
        confirmPass.setText(null);
        newPass.setText(null);

        //BOXES
        l1.removeAll();
        l1.revalidate();
        l1.repaint();
        l2.removeAll();
        l2.revalidate();
        l2.repaint();
        l3.removeAll();
        l3.revalidate();
        l3.repaint();
        l4.removeAll();
        l4.revalidate();
        l4.repaint();
        l5.removeAll();
        l5.revalidate();
        l5.repaint();
        c1.removeAll();
        c1.revalidate();
        c1.repaint();

        //PANEL AND WINDOW
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        window.getContentPane().removeAll();
        window.getContentPane().revalidate();
        window.getContentPane().repaint();
    }
}