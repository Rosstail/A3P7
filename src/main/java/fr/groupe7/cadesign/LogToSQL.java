package fr.groupe7.cadesign;

import javax.swing.*;
import java.sql.*;

public class LogToSQL extends JFrame {

    /**
     * Retourne un tableau String contenant l'ID et le role de l'utilisateur si celui-ci existe
     * @param userJTF
     * @param passWordJTF
     * @param checkbox
     * @return
     */
    public String[] logIn(JTextField userJTF, JPasswordField passWordJTF, Connection connection, JCheckBox checkbox) {
        String user = userJTF.getText();
        String pass = passWordJTF.getText();
        CreateIni createIni = new CreateIni();

        try {
            String strUser = "SELECT user_id, user_firstname, user_name, user_mail, user_role, user_signdatetime FROM users WHERE '" + user +
                    "' = user_mail AND '" + pass + "' = user_password";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(strUser);
            if (results.next()) {
                if (checkbox.isSelected())
                    createIni.creation(userJTF, passWordJTF);
                int userID = results.getInt("user_id");
                String userFirstName = results.getString("user_firstname");
                String userLastName = results.getString("user_name");
                String userMailAdress = results.getString("user_mail");
                String userRole = results.getString("user_role");
                String userSignDateTime = results.getString("user_signdatetime");
                String[] userIdRole = new String[] {"a", "b", "c", "d", "e", "f"};
                userIdRole[0] = Integer.toString(userID);
                userIdRole[1] = userFirstName;
                userIdRole[2] = userLastName;
                userIdRole[3] = userMailAdress;
                userIdRole[4] = userRole;
                userIdRole[5] = userSignDateTime;
                return userIdRole;
            }
            else {
                System.out.println("Connexion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}