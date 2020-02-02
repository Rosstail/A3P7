package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Création de l'écran CRUD
 */
public class Menu {
    Box l1 = Box.createHorizontalBox();
    Box l2 = Box.createHorizontalBox();
    Box l3 = Box.createHorizontalBox();
    Box c1 = Box.createVerticalBox();
    JPanel crudPanel = new JPanel();
    JMenu crudMenu = new JMenu("CRUD MENU");
    JMenuBar crudBar = new JMenuBar();
    JMenu createMenu = new JMenu("Create");
    JMenu readMenu = new JMenu("Read");
    JMenu updateMenu = new JMenu("Update");
    JMenu deleteMenu = new JMenu("Delete");
    JMenuItem projects = new JMenuItem("Projects");
    JMenuItem account = new JMenuItem("Account");
    JMenuItem users = new JMenuItem("Users");
    JButton disconnect = new JButton("Disconnect");
    Request req = new Request();
    Insert insert = new Insert();
    Delete del = new Delete();
    Update up = new Update();
    UpdateProfile updateProfile = new UpdateProfile();

    /**
     * Lance le check des permissions et ajoute les menus à l'affichage
     * @param window
     * @param id
     * @param co
     * @param role
     */
    public void menu(JFrame window, int id, Connection co, String role){
        window.setTitle("CA DESIGN - Welcome " + role + "!");
        window.getContentPane().removeAll();
        window.revalidate();
        window.repaint();
        crudMenu.add(createMenu);
        crudMenu.add(readMenu);
        crudMenu.add(updateMenu);
        crudMenu.add(deleteMenu);
        crudBar.add(crudMenu);
        l1.add(crudBar);
        l3.add(disconnect);
        setMenuPermissions(role);

        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("USERS");
                //up.Update(window, co, bmenu, id, role);
            }
        });
        projects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PROJEEETS");
                //req.Request(window, id, co, bmenu);
            }
        });
        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile.userMenu(window, id, co);
            }
        });
        /*insertion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("INSEEEEEERT");
                //insert.Insert(window, co, bmenu, id, role);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DELEEEEEETE");
                //el.Delete(window, co, bmenu, id, role);
            }
        });*/
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    co.close();
                    System.out.println("Successfully disconnected.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        crudPanel.add(c1);
        window.getContentPane().add(crudPanel);
        window.setVisible(true);
    }

    /**
     * Ajoute les actions disponibles selon le rôle de l'utilisateur
     * @param role
     */
    private void setMenuPermissions(String role) {
        updateMenu.add(account);
        switch (role) {
            case "customer":
                readMenu.add(projects);
                break;
            case "architect":
                createMenu.add(projects);
                break;
            case "admin":
                createMenu.add(users);
                createMenu.add(projects);
                createMenu.add(users);
                break;
        }
    }
}