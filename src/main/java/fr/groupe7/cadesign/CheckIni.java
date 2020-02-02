package fr.groupe7.cadesign;

import org.ini4j.Wini;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CheckIni {

    public void check(JTextField user, JPasswordField passWord) {
        File file = new File("infosCo.ini");
        if (file.exists()) {
            try {
                Wini ini = new Wini(file);
                String stringMail = ini.get("InfosCo", "mail");
                String stringPassWord = ini.get("InfosCo", "password");
                user.setText(stringMail);
                passWord.setText(stringPassWord);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
