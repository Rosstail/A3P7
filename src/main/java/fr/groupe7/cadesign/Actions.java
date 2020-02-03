package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Création de la fenêtre et interface d'accueil (login / register)
 */
public class Actions {
    RegisterToSQL registerToSQL = new RegisterToSQL();
    LogToSQL logToSQL = new LogToSQL();

    public void checkLogIn(JTextField userMail, JPasswordField passWord) {
        if (userMail.getText().length() > 0 && passWord.getPassword().length > 0 )
            logToSQL.logIn(userMail, passWord);
    }

    public void checkRegister(JTextField firstName, JTextField lastName, JTextField userMail, JPasswordField passWord) {
        if (firstName.getText().length() >= 3 && lastName.getText().length() >= 3 &&
                checkEmail(userMail) && checkPassWord(passWord))
            registerToSQL.register(firstName, lastName, userMail, passWord);
    }
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

}
