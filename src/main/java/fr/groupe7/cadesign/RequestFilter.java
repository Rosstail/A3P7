package fr.groupe7.cadesign;

import javax.swing.*;

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

    public String projectFilter(JTextField userName, JTextField userFirstName, JTextField projectName) {
        String request = "SELECT P.project_id, P.project_name, P.project_customer_id, P.project_architect_id, P.project_start_datetime, " +
                "P.project_delivery_datetime, P.project_quotation, P.project_commentary FROM projects as P";
        if (userName.getText().length() >= 3)
            request = request + ", users as U WHERE U.user_name LIKE '" + userName + "%'";
        if (userFirstName.getText().length() >= 3) {
            if (request.contains("WHERE"))
                request = request + " AND ";
            else
                request = request + " WHERE ";
            request = request + "U.user_firstname LIKE '" + userFirstName.getText() + "%'";
        }
        if (projectName.getText().length() >= 3) {
            if (request.contains("WHERE"))
                request = request + " AND ";
            else
                request = request + " WHERE ";
            request = request + "P.project_name LIKE '" + projectName.getText() + "%'";
        }
        System.out.println(request);

        return request;
    }

    public String stepProjectFilter(JTextField userName, JTextField userFirstName, JTextField projectName) {
        String request = "SELECT S.step_id, P.project_name, S.step_architect_id, S.step_name, S.step_commentary, S.step_start_datetime, S.step_done_datetime" +
                " FROM users AS U, projects AS P, steps as S" +
                " WHERE U.user_id = S.step_architect_id AND P.project_id = S.step_project_id";
        if (userFirstName.getText().length() >= 3)
            request = request + " AND U.user_firstname LIKE '" + userFirstName.getText() + "%'";
        if (userName.getText().length() >= 3)
            request = request + " AND U.user_name LIKE '" + userName.getText() + "%'";
        if (projectName.getText().length() >= 3)
            request = request + " AND P.project_name LIKE '" + projectName.getText() + "%'";
        System.out.println(request);

        return request;
    }

    public String architectProjectFilter(JTextField userName, JTextField userFirstName, JTextField projectName) {
        String request = "SELECT A.architect_id, U.user_firstname, U.user_name, P.project_name, A.architect_assigned_datetime\n" +
                "FROM architects AS A, users AS U, projects AS P WHERE U.user_id = A.architect_id AND P.project_id = A.architect_project_id";
        if (userFirstName.getText().length() >= 3)
            request = request + " AND U.user_firstname LIKE '" + userFirstName.getText() + "%'";
        if (userName.getText().length() >= 3)
            request = request + " AND U.user_name LIKE '" + userName.getText() + "%'";
        if (projectName.getText().length() >= 3)
            request = request + " AND P.project_name LIKE '" + projectName.getText() + "%'";
        System.out.println(request);

        return request;
    }
}