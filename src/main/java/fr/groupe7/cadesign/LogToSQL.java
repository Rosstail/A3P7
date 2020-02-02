package fr.groupe7.cadesign;

import javax.swing.*;
import java.sql.*;

public class LogToSQL extends JFrame {

    Menu menu = new Menu();

    public void logIn(JFrame window, JTextField userJTF, JPasswordField passWordJTF) {
        String user = userJTF.getText();
        String pass = passWordJTF.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/bdd_ca_design",
                    "anthony", "Atelier2");
            String strUser = "SELECT user_id, user_role FROM users WHERE '" + user +
                    "' = user_mail AND '" + pass + "' = user_password";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(strUser);
            if (results.next()) {
                int userID = results.getInt("user_id");
                String userRole = results.getString("user_role");
                System.out.println("Connexion succeed ! ID = " + userID + " ROLE = " + userRole);
                menu.menu(window, userID, connection, userRole);
            }
            else
                System.out.println("Connexion failed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}