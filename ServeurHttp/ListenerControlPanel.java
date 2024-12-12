package ecoute;

import fenetre.ControlPanel;
import serveur.ServeurHttp;
import serveur.ServeurTransfert;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerControlPanel implements ActionListener {
    ControlPanel controlPanel;
    JButton currentBtn;

    public ListenerControlPanel() {

    }
    public ListenerControlPanel(ControlPanel controlPanel, JButton currentBtn) {
        this.setControlPanel(controlPanel);
        this.setCurrentBtn(currentBtn);
    }

    // getters
    public ControlPanel getControlPanel() {
        return this.controlPanel;
    }
    public JButton getCurrentBtn() {
        return this.currentBtn;
    }    

    // setters
    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }
    public void setCurrentBtn(JButton currentBtn) {
        this.currentBtn = currentBtn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (this.getControlPanel().geButtons()[0].equals(this.getCurrentBtn())) {
                // Launching HTTP server

                this.getControlPanel().setServeurHttp(new ServeurHttp());

                this.getControlPanel().geButtons()[0].setEnabled(false);
                this.getControlPanel().geButtons()[1].setEnabled(true);
                
                System.out.println("Serveur HTTP démarré...");
            }
            else if (this.getControlPanel().geButtons()[1].equals(this.getCurrentBtn())) {
                // Stopping HTTP server

                this.getControlPanel().getServeurHttp().close();
                this.getControlPanel().setServeurHttp(null);

                this.getControlPanel().geButtons()[1].setEnabled(false);
                this.getControlPanel().geButtons()[0].setEnabled(true);
            }
            else if (this.getControlPanel().geButtons()[2].equals(this.getCurrentBtn())){
                // Launching file transfer server

                this.getControlPanel().setServeurTransfert(new ServeurTransfert());

                this.getControlPanel().geButtons()[2].setEnabled(false);
                this.getControlPanel().geButtons()[3].setEnabled(true);

                System.out.println("Serveur de transfert de fichier démarré...");
            }
            else if(this.getControlPanel().geButtons()[3].equals(this.getCurrentBtn())){
                // Stopping file transfer server

                this.getControlPanel().getServeurTransfert().close();
                this.getControlPanel().setServeurTransfert(null);

                this.getControlPanel().geButtons()[3].setEnabled(false);
                this.getControlPanel().geButtons()[2].setEnabled(true);
            }    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}