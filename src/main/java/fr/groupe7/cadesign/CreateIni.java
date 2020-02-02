package fr.groupe7.cadesign;

import org.ini4j.Wini;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CreateIni {

    static void creation(JTextField mail, JTextField passWord, JCheckBox checkBox) {
        try {
            if (checkBox.isSelected()) {
                File file = new File("infosCo.ini");
                file.createNewFile();
                Wini ini = new Wini(file);
                ini.put("InfosCo", "mail", mail.getText());
                ini.put("InfosCo", "password", passWord.getText());
                ini.store();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
