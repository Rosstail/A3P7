package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateProfile {
    String newMail;
    String passWord;
    String newPass;
    Connection connection;
    public void updateMenu(int userID, JTextField jtfNewMail, JPasswordField jtfConfirmPass, JTextField jtfNewPass, Connection connection) {
        newMail = jtfNewMail.getText();
        passWord = jtfConfirmPass.getText();
        newPass = jtfNewPass.getText();
        if (getPass(userID, connection).equals(passWord)) {
            if (checkNewMail())
                changesMail(userID, connection);
            if (checkNewPassWord())
                changesPass(userID, connection);
        }
    }

    public String getPass(int id, Connection connection) {
        String strUserPass = "SELECT user_password FROM users WHERE '" + id +
                "' = user_id";
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(strUserPass);
            if (results.next()) {
                return results.getString("user_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changesMail(int userID, Connection connection) {
        String query = "UPDATE users set user_mail = ? WHERE user_id = ?";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, newMail);
            preparedStmt.setInt(2, userID);
            preparedStmt.executeUpdate();
            System.out.println("Mail changed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changesPass(int userID, Connection connection) {
        String query = "UPDATE users set user_password = ? WHERE user_id = ?";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString   (1, newPass);
            preparedStmt.setInt(2, userID);
            preparedStmt.executeUpdate();
            System.out.println("Password changed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkNewMail() {
        int nbLowCase = 0;

        if ( newMail.length() >= 6 && newMail.contains("@") && newMail.contains(".")) {
            for (int i = 0; i < newMail.length(); i++) {
                if (newMail.charAt(i) >= 'a' && newMail.charAt(i) <= 'z')
                    nbLowCase++;
            }
            return nbLowCase >= newMail.length() - 2;
        }
        return false;
    }

    private boolean checkNewPassWord() {
        int nbLowCase = 0;
        int nbUpCase = 0;
        int nbInt = 0;
        int nbSpecial = 0;

        if (newPass.length() >= 12) {
            for (int i = 0; i < newPass.length(); i++) {
                if (newPass.charAt(i) >= 'a' && newPass.charAt(i) <= 'z')
                    nbLowCase++;
                else if (newPass.charAt(i) >= 'A' && newPass.charAt(i) <= 'Z')
                    nbUpCase++;
                else if (newPass.charAt(i) >= '0' && newPass.charAt(i) <= '9')
                    nbInt++;
                else
                    nbSpecial++;
            }
            return nbInt >= 3 && nbLowCase >= 3 && nbUpCase >= 2 && nbSpecial >= 2;
        }
        return false;
    }

}