package fr.groupe7.cadesign;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Read {

    /**
     * Permet de vérifier que l'adresse mail n'est pas erronée
     * Return TRUE si au l'adresse possède au moins 4 minuscules, un "." et un "@".
     * @return
     */
    public boolean checkEmail(JTextField userMail) {
        String mail = userMail.getText();
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

    /**
     * Vérifie que le mot de passe est suffisament sécurisé.
     * Returne TRUE si c'est le cas
     * @return
     */
    public boolean checkPassWord(JPasswordField passWord) {
        String pass = passWord.getText();
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
    }

    /**
     * Récupération via requête du mot de passe actuel de l'utilisateur
     * @param id
     * @param co
     * @return
     */
    public String getPass(int id, Connection co) {
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

}
