package ecoute;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fenetre.Fenetre;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Listener implements ActionListener {
    Fenetre fenetre;

    public Listener(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        boolean dossier = false;

        // 0 raha fichier, 1 raha dossier
        if (fenetre.getTypeFile().getSelectedIndex() == 1) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);    
            dossier = true;
        }
        
        int result = fileChooser.showOpenDialog(null);
        if (result == fileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();

                if (dossier) {
                    fenetre.getClient().sendDossier(selectedFile.getAbsolutePath());
                }
                else {
                    fenetre.getClient().sendMessage(selectedFile.getAbsolutePath());
                }
                
                System.out.println("Chemin absolu du fichier:  " + selectedFile.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "Le fichier: " + selectedFile.getAbsolutePath() + " a été uploader avec succès", "Information", JOptionPane.INFORMATION_MESSAGE);            
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Echec de l'upload", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}