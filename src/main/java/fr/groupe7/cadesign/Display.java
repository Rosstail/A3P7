
package fr.groupe7.cadesign;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Every displays inside the JFrame
 */
public class Display implements ActionListener {
    private UpdateProfile updateProfile = new UpdateProfile();
    private EditableTable editableTable = new EditableTable();

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
    private JTextField userMailJTF = new JTextField(10);
    private JPasswordField passWord = new JPasswordField(10);
    private JPasswordField passWordConfirm = new JPasswordField(10);

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

    JButton leave = new JButton("EXIT");
    /**
     * Displays the main window (Connection / logIn)
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
        l2.add(leave);
        c1.add(l1);
        c1.add(l2);
        window.add(c1);
        window.getContentPane().add(panel);
        window.setVisible(true);

        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
    }

    private JTextField firstName = new JTextField(10);
    private JTextField lastName = new JTextField(10);
    private JCheckBox checkbox = new JCheckBox("Retenir mes informations");
    private LogRegActions logRegActions = new LogRegActions();
    private int userID;
    private String userFirstName;
    private String userLastName;
    private String userRole;

    /**
     * Modify the window to able user to register as default user
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
        l2.add(new JLabel("Confirm Password"));
        l3.add(firstName);
        l3.add(lastName);
        l3.add(userMailJTF);
        l3.add(passWord);
        l3.add(passWordConfirm);
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

    CheckIni checkIni = new CheckIni();
    /**
     * Modify the window to able the user to connect
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
        checkIni.check(userMailJTF, passWord);
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
     * Create the CRUD MENU using the user role
     */
    private void displayMainCrudMenu(){
        resetWindow();
        setCrudperms();
        //general widnow properties
        window.setTitle("CA DESIGN - Welcome [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
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
                listUsersAdmin();
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
                selfProfileCheck();
                //profileMenu();
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
        crudMenu.add(readMenu);
        crudMenu.add(updateMenu);
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
        readMenu.add(users);
        crudMenu.add(createMenu);
    }

    /**
     * Gives every permsof CRUD MENU to Admin
     */
    private void setAdminPerms() {
        createMenu.add(users);
        createMenu.add(projects);
        crudMenu.add(deleteMenu);
        crudMenu.add(createMenu);
    }

    private String userMail;
    private String userSignDateTime;

    /**
     * Some buttons check if user choose to register / connect or confirm connection / registering.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == connexion)
            displayUserConnexion();
        else if (actionEvent.getSource() == registration)
            displayUserRegistration();
        else if (actionEvent.getSource() == signUp) {
            logRegActions.checkRegister(firstName, lastName, userMailJTF, passWord, passWordConfirm, connection);
            displayUserConnexion();
        }
        else if (actionEvent.getSource() == logIn) {
            String[] userIdNameRole = logRegActions.checkLogIn(userMailJTF, passWord, connection, checkbox);
            userID = Integer.parseInt(userIdNameRole[0]);
            userFirstName = userIdNameRole[1];
            userLastName = userIdNameRole[2];
            userMail = userIdNameRole[3];
            userRole = userIdNameRole[4];
            userSignDateTime = userIdNameRole[5];
            displayMainCrudMenu();
        }
    }

    JButton updateAccount = new JButton("Updates Infos");
    private JButton backProfileToCRUD = new JButton("Back");

    /**
     * Default user can check his own profile and access to the Account modification
     */
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
        l3.add(updateAccount);
        l4.add(backProfileToCRUD);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);

        updateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfileMenu();
            }
        });
        backProfileToCRUD.addActionListener(new ActionListener() {
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
    private JButton backUpdateAccountToProfile = new JButton("Back");
    /**
     * Displays the update profile menu
     */
    private void updateProfileMenu() {
        resetWindow();
        window.setTitle("CA DESIGN - UPDATE PROFILE [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        l1.add(new JLabel("Change mail adress"));
        l1.add(new JLabel("password"));
        l2.add(newMail);
        l2.add(confirmPass);
        l3.add(new JLabel("New password"));
        l4.add(newPass);
        l4.add(confirm);
        l5.add(backUpdateAccountToProfile);
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
                displayMainCrudMenu();
            }
        });
        backUpdateAccountToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selfProfileCheck();
            }
        });
    }

    private String[] headerUsers = {"ID", "Name", "First name", "Mail adress", "Password", "Role", "Sign datetime"};
    private String[] headerArchitects = {"ID", "Referent architect first name", "referent architect name", "Project name", "Architect assignation datetime"};
    private String[] headerProjects = {"ID", "Project name", "Customer", "Referent Architect", "Project start datetime", "Project delivery datetime", "Project quotation", "Project commentary"};
    private String[] headerSteps = {"ID", "Project Name", "Referent architect", "Step name", "Step commentary", "Step start datetime", "Step done datetime"};
    private String request = "SELECT user_id, user_name, user_firstname, user_mail, user_password, user_role, user_signdatetime FROM users";
    Dimension dimension = new Dimension(700, 400);
    JTable table = new JTable();
    DefaultTableModel userTableModel = (DefaultTableModel) table.getModel();
    DefaultTableModel projectTableModel = (DefaultTableModel) table.getModel();
    DefaultTableModel architectTableModel = (DefaultTableModel) table.getModel();
    DefaultTableModel stepTableModel = (DefaultTableModel) table.getModel();
    JScrollPane jsp = new JScrollPane(table);
    int colCount;
    private JButton backFromUserListToCRUD = new JButton("Back");
    private JMenu tableMenu = new JMenu("TABLE");
    private JMenuBar tableChoice = new JMenuBar();
    private JMenuItem userTable = new JMenuItem("USERS");
    private JMenuItem projectTable = new JMenuItem("PROJECTS");
    private JMenuItem stepTable = new JMenuItem("STEPS");
    //private JMenuItem usedMaterialTable = new JMenuItem("MATERIAL LIST");
    private JMenuItem architectTable = new JMenuItem("ARCHITECTS");
    JTextField userNameFilter = new JTextField();
    JTextField userFirstNameFilter = new JTextField();
    JMenuBar userRoleFilter = new JMenuBar();
    JTextField projectNameFilter = new JTextField();
    JButton projectFilter = new JButton("Filter");
    JButton architectProjectFilter = new JButton("Filter");
    JButton stepFilter = new JButton("Filter");
    JMenu roleSelect = new JMenu("Role");
    JMenuItem anyRole = new JMenuItem("Any");
    JMenuItem defaultRole = new JMenuItem("Default");
    JMenuItem customerRole = new JMenuItem("Customer");
    JMenuItem architectRole = new JMenuItem("Architect");
    RequestFilter requestFilter = new RequestFilter();
    /**
     * ADMIN USER ONLY
     * Able the admin to check everything inside the user table
     */
    private void listUsersAdmin() {
        window.setTitle("CA DESIGN - EDIT LIST [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        resetWindow();
        //DESIGN
        tableMenu.add(userTable);
        tableMenu.add(projectTable);
        tableMenu.add(architectTable);
        tableMenu.add(stepTable);
        tableChoice.add(tableMenu);
        l1.add(tableChoice);

        //CREATION OF TABLE
        userTableModel.setColumnIdentifiers(headerUsers);
        jsp.setPreferredSize(dimension);

        //Remmplissage du tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                userTableModel.addRow(objects);
            }
            table.setModel(userTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************
        l2.add(new JLabel("Name (>3)"));
        l2.add(new JLabel("First name (>3)"));
        l3.add(userNameFilter);
        l3.add(userFirstNameFilter);
        roleSelect.add(anyRole);
        roleSelect.add(defaultRole);
        roleSelect.add(customerRole);
        roleSelect.add(architectRole);
        userRoleFilter.add(roleSelect);
        l3.add(userRoleFilter);
        l4.add(jsp);
        l5.add(backFromUserListToCRUD);
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);
        userTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("USERS");
                request = "SELECT user_id, user_name, user_firstname, user_mail, user_password, user_role, user_signdatetime FROM users";
                filterUsers();
            }
        });
        projectTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PROJECTS");
                request = "SELECT P.project_id, P.project_name, P.project_customer_id, P.project_architect_id, P.project_start_datetime, P.project_delivery_datetime, P.project_quotation, project_commentary FROM projects AS P";
                filterProjects();
            }
        });
        architectTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ARCHITECTS");
                request = "SELECT A.architect_id, U.user_firstname, user_name, P.project_name, A.architect_assigned_datetime FROM architects AS A, users AS U, projects AS P WHERE U.user_id = A.architect_id AND P.project_id = A.architect_project_id";
                System.out.println(request);
                filterArchitects();
            }
        });
        stepTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("STEPS");
                request = "SELECT S.step_id, P.project_name, S.step_architect_id, S.step_name, S.step_commentary, S.step_start_datetime, S.step_done_datetime" +
                        " FROM users AS U, projects AS P, steps as S" +
                        " WHERE U.user_id = S.step_architect_id AND P.project_id = S.step_project_id";
                filterSteps();

            }
        });
        backFromUserListToCRUD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMainCrudMenu();
            }
        });
        anyRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.anyRoleFilter(userNameFilter, userFirstNameFilter);
                updateUserBoard();
            }
        });
        defaultRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.defaultRoleFilter(userNameFilter, userFirstNameFilter);
                updateUserBoard();
            }
        });
        customerRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.customerRoleFilter(userNameFilter, userFirstNameFilter);
                updateUserBoard();
            }
        });
        architectRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.architectRoleFilter(userNameFilter, userFirstNameFilter);
                updateUserBoard();
            }
        });
        projectFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.projectFilter(userNameFilter, userFirstNameFilter, projectNameFilter);
                updateProjectBoard();
            }
        });
        stepFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.stepProjectFilter(userNameFilter, userFirstNameFilter, projectNameFilter);
                updateStepsBoard();
            }
        });
        architectProjectFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request = requestFilter.architectProjectFilter(userNameFilter, userFirstNameFilter, projectNameFilter);
                updateArchitectBoard();
            }
        });
    }

    private void filterUsers() {

        window.setTitle("CA DESIGN - EDIT LIST [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        userTableModel.setColumnIdentifiers(headerUsers);
        if (userTableModel.getRowCount() > 0) {
            for (int i = userTableModel.getRowCount() - 1; i > -1; i--) {
                userTableModel.removeRow(i);
            }
        }
        l2.removeAll();
        l2.revalidate();
        l2.repaint();
        l3.removeAll();
        l3.revalidate();
        l3.repaint();
        l4.removeAll();
        l4.revalidate();
        l4.repaint();

        //CREATION OF TABLE
        /**
         * L'ERREUR DE RETABLE SE SITUE ICI
         */
        userTableModel.setColumnIdentifiers(headerUsers);
        jsp.setPreferredSize(dimension);

        //Remmplissage du tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                userTableModel.addRow(objects);
            }
            table.setModel(userTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************
        l2.add(new JLabel("Name (>3)"));
        l2.add(new JLabel("First name (>3)"));
        l2.add(new JLabel("Role"));
        l3.add(userNameFilter);
        l3.add(userFirstNameFilter);
        roleSelect.add(anyRole);
        roleSelect.add(defaultRole);
        roleSelect.add(customerRole);
        roleSelect.add(architectRole);
        userRoleFilter.add(roleSelect);
        l3.add(userRoleFilter);
        l4.add(jsp);
        l5.add(backFromUserListToCRUD);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);
    }

    /**
     * Update the board with new filters. Filters are reseted after because JTextfield keeps the text
     * to avoid adding multiple WHERE
     */
    private void updateUserBoard() {
        //
        EditableTable editableTable = new EditableTable();
        if (userTableModel.getRowCount() > 0) {
            for (int i = userTableModel.getRowCount() - 1; i > -1; i--) {
                userTableModel.removeRow(i);

            }
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                userTableModel.addRow(objects);
            }
            table.setModel(userTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterProjects() {

        window.setTitle("CA DESIGN - EDIT PROJECTS [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        userTableModel.setColumnIdentifiers(headerProjects);
        if (userTableModel.getRowCount() > 0) {
            for (int i = userTableModel.getRowCount() - 1; i > -1; i--) {
                projectTableModel.removeRow(i);
            }
        }
        l2.removeAll();
        l2.revalidate();
        l2.repaint();
        l3.removeAll();
        l3.revalidate();
        l3.repaint();
        l4.removeAll();
        l4.revalidate();
        l4.repaint();

        //CREATION OF TABLE
        /**
         * L'ERREUR DE RETABLE SE SITUE ICI
         */
        projectTableModel.setColumnIdentifiers(headerProjects);
        jsp.setPreferredSize(dimension);

        //Remmplissage du tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                projectTableModel.addRow(objects);
            }
            table.setModel(projectTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************
        l2.add(new JLabel("Name (>3)"));
        l2.add(new JLabel("First name (>3)"));
        l2.add(new JLabel("Project name (>3)"));
        l3.add(userNameFilter);
        l3.add(userFirstNameFilter);
        l3.add(projectNameFilter);
        l3.add(projectFilter);
        l4.add(jsp);
        l5.add(backFromUserListToCRUD);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);
    }

    /**
     * Update the board with new filters. Filters are reseted after because JTextfield keeps the text
     * to avoid adding multiple WHERE
     */
    private void updateProjectBoard() {
        //
        EditableTable editableTable = new EditableTable();
        if (userTableModel.getRowCount() > 0) {
            for (int i = userTableModel.getRowCount() - 1; i > -1; i--) {
                userTableModel.removeRow(i);

            }
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                userTableModel.addRow(objects);
            }
            table.setModel(userTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterSteps() {

        window.setTitle("CA DESIGN - EDIT STEPS [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        stepTableModel.setColumnIdentifiers(headerSteps);
        if (stepTableModel.getRowCount() > 0) {
            for (int i = stepTableModel.getRowCount() - 1; i > -1; i--) {
                stepTableModel.removeRow(i);
            }
        }
        l2.removeAll();
        l2.revalidate();
        l2.repaint();
        l3.removeAll();
        l3.revalidate();
        l3.repaint();
        l4.removeAll();
        l4.revalidate();
        l4.repaint();

        //CREATION OF TABLE
        /**
         * L'ERREUR DE RETABLE SE SITUE ICI
         */
        jsp.setPreferredSize(dimension);

        //Remmplissage du tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                stepTableModel.addRow(objects);
            }
            table.setModel(stepTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************
        l2.add(new JLabel("Name (>3)"));
        l2.add(new JLabel("First name (>3)"));
        l2.add(new JLabel("Project name (>3)"));
        l3.add(userNameFilter);
        l3.add(userFirstNameFilter);
        l3.add(projectNameFilter);
        l3.add(stepFilter);
        l4.add(jsp);
        l5.add(backFromUserListToCRUD);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);
    }

    /**
     * Update the board with new filters. Filters are reseted after because JTextfield keeps the text
     * to avoid adding multiple WHERE
     */
    private void updateStepsBoard() {
        //
        EditableTable editableTable = new EditableTable();
        if (stepTableModel.getRowCount() > 0) {
            for (int i = stepTableModel.getRowCount() - 1; i > -1; i--) {
                stepTableModel.removeRow(i);

            }
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                stepTableModel.addRow(objects);
            }
            table.setModel(stepTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterArchitects() {

        window.setTitle("CA DESIGN - ARCHITECTS EDIT LIST [" + userRole.toUpperCase() + "] " + userFirstName + " " + userLastName);
        if (architectTableModel.getRowCount() > 0) {
            for (int i = architectTableModel.getRowCount() - 1; i > -1; i--) {
                architectTableModel.removeRow(i);
            }
        }
        l2.removeAll();
        l2.revalidate();
        l2.repaint();
        l3.removeAll();
        l3.revalidate();
        l3.repaint();
        l4.removeAll();
        l4.revalidate();
        l4.repaint();

        //CREATION OF TABLE
        userTableModel.setColumnIdentifiers(headerArchitects);
        jsp.setPreferredSize(dimension);

        //Remmplissage du tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                architectTableModel.addRow(objects);
            }
            table.setModel(architectTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************
        l2.add(new JLabel("Name (>3)"));
        l2.add(new JLabel("First name (>3)"));
        l2.add(new JLabel("Project Name (>3)"));
        l3.add(userNameFilter);
        l3.add(userFirstNameFilter);
        l3.add(projectNameFilter);
        l3.add(architectProjectFilter);
        l4.add(jsp);
        l5.add(backFromUserListToCRUD);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        panel.add(c1);
        window.add(panel);
        window.setVisible(true);
    }

    /**
     * Update the Architect list after filter
     */
    private void updateArchitectBoard() {
        //
        EditableTable editableTable = new EditableTable();
        if (architectTableModel.getRowCount() > 0) {
            for (int i = architectTableModel.getRowCount() - 1; i > -1; i--) {
                architectTableModel.removeRow(i);

            }
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            colCount = meta.getColumnCount();
            while (results.next()) {
                Object[] objects = new Object[colCount];
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i+1);
                }
                architectTableModel.addRow(objects);
            }
            table.setModel(architectTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset every content of the window
     * Must be called before every display which needs lot of changes
     */
    private void resetWindow() {
        //TABLE
        if (userTableModel.getRowCount() > 0) {
            for (int i = userTableModel.getRowCount() - 1; i > -1; i--) {
                userTableModel.removeRow(i);
            }
        }
        if (architectTableModel.getRowCount() > 0) {
            for (int i = architectTableModel.getRowCount() - 1; i > -1; i--) {
                architectTableModel.removeRow(i);
            }
        }

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