package fr.groupe7.cadesign;

import javax.swing.*;
import java.sql.*;
import java.util.Calendar;

/**
 * Classe pour créer l'utilisateur
 */
public class RegisterToSQL {

    /**
     * INSERT les données de l'utilisateur dans la table USERS de la BDD avec date d'inscription et rôle par défaut
     * à valider par l'admin.
     * Utilise les paramètres fournis et vérifiés lors de la tentative d'inscription.
     * @param firstNameJTF
     * @param lastNameJTF
     * @param userMailJTF
     * @param passWordJPF
     */
    public void register(JTextField firstNameJTF, JTextField lastNameJTF, JTextField userMailJTF, JPasswordField passWordJPF, Connection connection) {
        String firstName = firstNameJTF.getText();
        String lastName = lastNameJTF.getText();
        String mail = userMailJTF.getText();
        String pass = passWordJPF.getText();
        String sameMail = "SELECT user_mail FROM users WHERE user_mail = '" + mail + "'";
        Calendar calendar = Calendar.getInstance();
        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sameMail);
            String query = "INSERT INTO users (user_name, user_firstname, user_mail, user_password, user_role, user_signdatetime) " +
                    "VALUES  (?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, lastName);
            preparedStmt.setString (2, firstName);
            preparedStmt.setString   (3, mail);
            preparedStmt.setString(4, pass);
            preparedStmt.setString    (5, "default");
            preparedStmt.setDate    (6, startDate);
            if (!results.next()) {
                System.out.println("SUCCEED");
                preparedStmt.execute();
            }
            else
                System.out.println("SAME MAIL !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
