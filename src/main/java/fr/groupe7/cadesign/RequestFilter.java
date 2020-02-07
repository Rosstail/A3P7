package fr.groupe7.cadesign;

import javax.swing.*;
import java.sql.Connection;

public class RequestFilter {


    public String anyRoleFilter(JTextField name, JTextField firstName) {
        String request = "SELECT * FROM users";
        if (firstName.getText().length() >= 3 && name.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + name.getText() + "%' AND user_name LIKE '" +
                    name.getText() + "%'";
        } else if (firstName.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + firstName.getText() + "%'";
        } else if (name.getText().length() >= 3) {
            request = request + " WHERE user_name LIKE '" + name.getText() + "%'";
        }
        System.out.println(request);
        return request;
    }

    public String defaultRoleFilter(JTextField name, JTextField firstName) {
        String request = "SELECT * FROM users";
        if (firstName.getText().length() >= 3 && name.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + name.getText() + "%' AND user_name LIKE ' AND " +
                    name.getText() + "%'";
        } else if (firstName.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + firstName.getText() + "%' AND ";
        } else if (name.getText().length() >= 3) {
            request = request + " WHERE user_name LIKE '" + name.getText() + "%' AND ";
        } else {
            request = request + " WHERE ";
        }
        request = request + "user_role = 'default';";
        System.out.println(request);
        return request;
    }

    public String customerRoleFilter(JTextField name, JTextField firstName) {
        String request = "SELECT * FROM users";
        if (firstName.getText().length() >= 3 && name.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + name.getText() + "%' AND user_name LIKE ' AND " +
                    name.getText() + "%'";
        } else if (firstName.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + firstName.getText() + "%' AND ";
        } else if (name.getText().length() >= 3) {
            request = request + " WHERE user_name LIKE '" + name.getText() + "%' AND ";
        } else {
            request = request + " WHERE ";
        }
        request = request + "user_role = 'customer';";
        System.out.println(request);
        return request;
    }

    public String architectRoleFilter(JTextField name, JTextField firstName) {
        String request = "SELECT * FROM users";
        if (firstName.getText().length() >= 3 && name.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + name.getText() + "%' AND user_name LIKE ' AND " +
                    name.getText() + "%'";
        } else if (firstName.getText().length() >= 3) {
            request = request + " WHERE user_firstname LIKE '" + firstName.getText() + "%' AND ";
        } else if (name.getText().length() >= 3) {
            request = request + " WHERE user_name LIKE '" + name.getText() + "%' AND ";
        } else {
            request = request + " WHERE ";
        }
        request = request + "user_role = 'architect';";
        System.out.println(request);
        return request;
    }
}