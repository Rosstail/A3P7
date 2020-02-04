package fr.groupe7.cadesign;

import javax.swing.*;
import java.sql.*;

public class LogToSQL extends JFrame {

    /**
     * Retourne un tableau String contenant l'ID et le role de l'utilisateur si celui-ci existe
     * @param userJTF
     * @param passWordJTF
     * @return
     */
    public String[] logIn(JTextField userJTF, JPasswordField passWordJTF) {
        String user = userJTF.getText();
        String pass = passWordJTF.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/bdd_ca_design",
                    "anthony", "Atelier2");
            String strUser = "SELECT user_id, user_firstname, user_name, user_role FROM users WHERE '" + user +
                    "' = user_mail AND '" + pass + "' = user_password";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(strUser);
            if (results.next()) {
                int userID = results.getInt("user_id");
                String userRole = results.getString("user_role");
                String userFirstName = results.getString("user_firstname");
                String userLastName = results.getString("user_name");
                String[] userIdRole = new String[] {"a", "b", "c", "d"};
                userIdRole[0] = Integer.toString(userID);
                userIdRole[1] = userFirstName;
                userIdRole[2] = userLastName;
                userIdRole[3] = userRole;
                return userIdRole;
            }
            else {
                System.out.println("Connexion failed.");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}