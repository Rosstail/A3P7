package fr.groupe7.cadesign;

import java.sql.*;

public class Filters {

    /*public int filterGetCount(String request, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            System.out.println(meta);
            return meta.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Object[] filterUsers(String request, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(request);
            ResultSetMetaData meta = results.getMetaData();
            int colCount = meta.getColumnCount();
            Object[] objects = new Object[colCount];
            while (results.next()) {
                for(int i = 0; i < colCount; i++) {
                    objects[i] = results.getObject(i + 1);
                }
            }
            return objects;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
