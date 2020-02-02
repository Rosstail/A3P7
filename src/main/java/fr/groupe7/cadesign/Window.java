package fr.groupe7.cadesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Création de la fenêtre et interface d'accueil (login / register)
 */
public class Window implements ActionListener {
    JFrame window = new JFrame();
    JMenu accessMenu = new JMenu("Se connecter / S'enregistrer");
    JMenuBar cho = new JMenuBar();
    JMenuItem connexion = new JMenuItem("Se connecter");
    JMenuItem registration = new JMenuItem("S'inscrire");
    JLabel firstNameLabel = new JLabel("Prénom");
    JLabel lastNameLabel = new JLabel("Nom");
    JLabel mailLabel = new JLabel("E-Mail");
    JLabel passLabel = new JLabel("Password");
    JButton logIn = new JButton("Connexion");
    JButton signUp = new JButton("Inscription");
    JPanel registerPanel = new JPanel();
    JPanel connexionPanel = new JPanel();

    LogToSQL loginToSQL = new LogToSQL();
    RegisterToSQL registerToSQL = new RegisterToSQL();
    JTextField firstName = new JTextField(10);
    JTextField lastName = new JTextField(10);
    JTextField userMail = new JTextField(10);
    JPasswordField passWord = new JPasswordField(10);
    JCheckBox checkbox = new JCheckBox("Retenir mes informations");
    CheckIni checkIni = new CheckIni();

    Box l1 = Box.createHorizontalBox();
    Box l2 = Box.createHorizontalBox();
    Box l3 = Box.createHorizontalBox();
    Box l4 = Box.createHorizontalBox();
    Box c1 = Box.createVerticalBox();

    /**
     * Démarre la création de la fenêtre de connection
     */
    public void window() {
        JPanel logInPanel = new JPanel();

        window.setTitle("CA DESIGN - HOME");
        window.setSize(1200, 1000);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout()); //Adapter taille des boutons
        //Ajouter Connexion/s'enregistrer sur le menu
        connexion.addActionListener(this);
        registration.addActionListener(this);
        accessMenu.add(connexion);
        accessMenu.add(registration);
        cho.add(accessMenu);
        l1.add(cho);
        c1.add(l1);
        window.add(c1);
        checkIni.check(userMail, passWord);
        window.getContentPane().add(logInPanel);
        window.setVisible(true);
    }

    /**
     * S'exécute lorsque l'utilisateur choisis de s'inscrire
     */
    public void userRegistration(){

        window.getContentPane().remove(connexionPanel);
        l2.add(firstNameLabel);
        l2.add(lastNameLabel);
        l2.add(mailLabel);
        l2.add(passLabel);
        l3.add(firstName);
        l3.add(lastName);
        l3.add(userMail);
        l3.add(passWord);
        l4.add(signUp);
        signUp.addActionListener(this);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        registerPanel.add(c1);
        window.add(registerPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Modifie la fenêtre pour permettre à l'utilisateur de se connecter
     */
    public void userConnexion(){
        window.getContentPane().remove(registerPanel);
        l2.add(mailLabel);
        l2.add(passLabel);
        l3.add(userMail);
        l3.add(passWord);
        l3.add(checkbox);
        l4.add(logIn);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        connexionPanel.add(c1);

        logIn.addActionListener(this);

        window.add(connexionPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Permet de vérifier que l'adresse mail n'est pas erronée
     * Return TRUE si au l'adresse possède au moins 4 minuscules, un "." et un "@".
     * @return
     */
    private boolean checkEmail() {
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
    private boolean checkPassWord() {
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
     * Se déclenche lors d'un clic sur un bouton connexion/inscription ou sur choix en accueil
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == connexion)
            userConnexion();
        else if (actionEvent.getSource() == registration)
            userRegistration();
        else if (actionEvent.getSource() == signUp) {
            if (firstName.getText().length() >= 3 && lastName.getText().length() >= 3) {
                if (checkEmail() && checkPassWord()) {
                    registerToSQL.register(firstName, lastName, userMail, passWord);
                }
            }
        }
        else if (actionEvent.getSource() == logIn)
            if (userMail.getText().length() > 0 && passWord.getPassword().length > 0)
                loginToSQL.logIn(window, userMail, passWord);
    }
}
