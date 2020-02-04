package fr.groupe7.cadesign;

import java.sql.SQLException;

public class App {
    static Display display = new Display();

    public static void main(String[] args) throws SQLException {
        display.setWindow();
    }
}
