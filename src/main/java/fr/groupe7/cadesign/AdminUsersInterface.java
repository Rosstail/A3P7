package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;


public class AdminUsersInterface {

    JButton buttonDelete = new JButton("Supprimer un utilisateur");
    JButton buttonAdd = new JButton("Ajouter un utilisateur");
    JButton validateAdd = new JButton("Valider");
    JButton validateDel = new JButton("/!\\ Valider");
    JLabel userNameLabel = new JLabel("Nom");
    JLabel userFirstNameLabel = new JLabel("Prénom");
    JLabel userMailLabel = new JLabel("Adresse mail");
    JLabel userPasswordLabel = new JLabel("Mot de passe");
    JLabel userRoleLabel = new JLabel("Rôle");
    JTextField newUserName = new JTextField(15);
    JTextField newUserFirstName = new JTextField(15);
    JTextField newUserMail = new JTextField(15);
    JTextField newUserPassWord = new JTextField(15);
    JTextField newUserRole = new JTextField(15);
    JPanel addUserPanel = new JPanel();
    JPanel delUserPanel = new JPanel();

    public void adminInterface(JFrame window, Connection connection) {

        window.getContentPane().removeAll();
        window.add(buttonAdd);
        window.add(buttonDelete);
        window.setVisible(true);

        /*mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backMenu();
            }
        });*/

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAddUser(window);
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelDelUser(window);
            }
        });

        validateAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Architecte ajouté");
                if (newUserRole.getText().equals("architect") || newUserRole.getText().equals("customer")) {
                    try {
                        String request = "INSERT INTO users (user_name, user_firstname, user_mail, user_password," +
                                " user_role, user_signdatetime) VALUES ('" + newUserName.getText() + "'," + " '" +
                                newUserFirstName.getText() + "', '" + newUserMail.getText() + "','" +
                                newUserPassWord.getText() + "'," + " '" + newUserRole.getText() + "', NOW())";
                        Statement statement = connection.createStatement();
                        int results = statement.executeUpdate(request);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    System.out.println("role must be \"architect\" or \"customer\".");
            }
        });

        validateDel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String request = "DELETE " +
                            "FROM users WHERE user_name = '" + newUserName.getText() + "' AND user_firstname = '" +
                            newUserFirstName.getText() + "';";
                    Statement statement = connection.createStatement();
                    int results = statement.executeUpdate(request);
                    System.out.println("User deleted.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void panelAddUser(JFrame window) {
        window.getContentPane().removeAll();
        //window.add(buttonDelete);
        window.getContentPane().add(addUserPanel);
        addUserPanel.add(userNameLabel);
        addUserPanel.add(newUserName);
        addUserPanel.add(userFirstNameLabel);
        addUserPanel.add(newUserFirstName);
        addUserPanel.add(userMailLabel);
        addUserPanel.add(newUserMail);
        addUserPanel.add(userPasswordLabel);
        addUserPanel.add(newUserPassWord);
        addUserPanel.add(userRoleLabel);
        addUserPanel.add(newUserRole);
        addUserPanel.add(validateAdd);
        window.setVisible(true);
    }

    public void panelDelUser(JFrame window) {
        window.getContentPane().removeAll();
        //window.add(buttonAdd);
        window.getContentPane().add(delUserPanel);
        delUserPanel.add(userNameLabel);
        delUserPanel.add(newUserName);
        delUserPanel.add(userFirstNameLabel);
        delUserPanel.add(newUserFirstName);
        delUserPanel.add(validateDel);
        window.setVisible(true);
    }
}
