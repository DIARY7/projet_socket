package fenetre;

import client.Client;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import ecoute.Listener;

public class Fenetre extends JFrame {
    Client client;
    JComboBox typeFile;

    public Fenetre(Client client) {
        this.client = client;
        this.setTitle("Choix du type du fichier");
        this.getContentPane().setLayout( new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS ));
        JLabel titre = new JLabel("Tranférer un fichier ");
        titre.setFont(new Font("Impact",Font.BOLD,25));
        JPanel pTitre = new JPanel(new FlowLayout());
        pTitre.add(titre);
        JButton btn = new JButton("Uploader");
        JPanel pBtn = new JPanel(new FlowLayout());
        pBtn.add(btn);
        btn.addActionListener(new Listener(this));
        
        this.getContentPane().add(pTitre);
        this.getContentPane().add(prepareComboBox());
        this.getContentPane().add(pBtn);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);        
    }

    public JPanel prepareComboBox(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel label =  new JLabel("Type de tranfert");
        String[] elements = {"Fichier","Dossier"};
        this.typeFile = new JComboBox(elements);
        panel.add(label);
        panel.add(this.typeFile);
        return panel;
        
    }

    // getters
    public JComboBox getTypeFile() {
        return typeFile;
    }
    public Client getClient() {
        return client;
    }

    // setters
    public void setTypeFile(JComboBox typeFile) {
        this.typeFile = typeFile;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}