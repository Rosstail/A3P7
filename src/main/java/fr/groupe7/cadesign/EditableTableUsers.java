package fr.groupe7.cadesign;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.sql.*;

/**
 * Only for admin, every table text change make an update.
 */
public class EditableTableUsers {

    public void editUsers(TableModelEvent t, JTable table, Connection connection) {
        if (t.getType() == TableModelEvent.UPDATE) {
            int row = t.getFirstRow();
            int column = t.getColumn();
            String newValue = (String)table.getValueAt(row, column);
            System.out.println(newValue);
            String request = "SELECT user_firstname, user_name, user_mail, user_password, user_role, user_signdatetime FROM users WHERE user_id = "+(row+1);
            try {
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(request);
                ResultSetMetaData rsmd = results.getMetaData();
                String columnToEdit = rsmd.getColumnName(column+1);
                String update = "UPDATE users SET "+columnToEdit+" = '"+newValue+"' WHERE user_id = "+(row+1);
                System.out.println(update);
                statement.executeUpdate(update);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
