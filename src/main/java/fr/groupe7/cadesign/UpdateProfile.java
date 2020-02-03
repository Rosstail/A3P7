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

    /*JLabel actualMailLabel = new JLabel("Change mail adress");
    JLabel confirmPassLabel = new JLabel("Actual password *");
    JLabel newPassLabel = new JLabel("New password");
    JTextField newMail = new JTextField(15);
    JTextField confirmPass = new JTextField(15);
    JTextField newPass = new JTextField(15);
    JButton confirm = new JButton("Confirm");
    int id;
    Connection co;*/

    public void userMenu(/*JFrame window, int user_id, Connection connection*/) {
        /*id = user_id;
        co = connection;
        window.getContentPane().removeAll();
        window.add(actualMailLabel);
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
                if (getPass(id, co).equals(confirmPass.getText()))
                    changes();
            }
        });*/
        System.out.println("TEST3");
    }

    /*private String getPass(int id, Connection co) {
        String strUserPass = "SELECT user_password FROM users WHERE '" + id +
                "' = user_id";
        try {
            Statement statement = co.createStatement();
            ResultSet results = statement.executeQuery(strUserPass);
            if (results.next()) {
                String pass = results.getString("user_password");
                return pass;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changes() {
        if (checkNewMail()) {
            String query = "UPDATE users set user_mail = ? WHERE user_id = ?";
            try {
                PreparedStatement preparedStmt = co.prepareStatement(query);
                preparedStmt.setString   (1, newMail.getText());
                preparedStmt.setInt(2, id);
                preparedStmt.executeUpdate();
                System.out.println("Mail changed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Mail too short !");

        if (checkNewPassWord()) {
            String query = "UPDATE users set user_password = ? WHERE user_id = ?";
            try {
                PreparedStatement preparedStmt = co.prepareStatement(query);
                preparedStmt.setString   (1, newPass.getText());
                preparedStmt.setInt(2, id);
                preparedStmt.executeUpdate();
                System.out.println("Password changed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("password too short !");
        }
    }

    private boolean checkNewMail() {
        String mail = newMail.getText();
        int nbLowCase = 0;

        if ( mail.length() >= 6 && mail.contains("@") && mail.contains(".")) {
            for (int i = 0; i < mail.length(); i++) {
                if (mail.charAt(i) >= 'a' && mail.charAt(i) <= 'z')
                    nbLowCase++;
            }
            return nbLowCase >= mail.length() - 2;
        }
        return false;
    }

    private boolean checkNewPassWord() {
        String pass = newPass.getText();
        int nbLowCase = 0;
        int nbUpCase = 0;
        int nbInt = 0;
        int nbSpecial = 0;

        if (pass.length() >= 12) {
            for (int i = 0; i < pass.length(); i++) {
                if (pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z')
                    nbLowCase++;
                else if (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z')
                    nbUpCase++;
                else if (pass.charAt(i) >= '0' && pass.charAt(i) <= '9')
                    nbInt++;
                else
                    nbSpecial++;
            }
            return nbInt >= 3 && nbLowCase >= 3 && nbUpCase >= 2 && nbSpecial >= 2;
        }
        return false;
    }*/

}