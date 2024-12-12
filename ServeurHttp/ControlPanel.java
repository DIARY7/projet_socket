package fenetre;

import ecoute.ListenerControlPanel;
import serveur.ServeurHttp;
import serveur.ServeurTransfert;
import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JFrame {    
    ServeurHttp serveurHttp;
    ServeurTransfert serveurTransfert;
    JButton[] buttons;

    public ControlPanel() {
        this.initComponents();
    }

    public void initComponents() {
        /* Panels */
        JPanel panelTitle = new JPanel();
        JPanel panelSubtitle = new JPanel();
        JPanel panelHttp = new JPanel();
        JPanel panelTransfer = new JPanel();
        /* Labels */
        JLabel labelTitle = new JLabel();
        JLabel labelSubtitle = new JLabel();
        JLabel labelHttp = new JLabel();
        JLabel labelTransfer = new JLabel();
        /* Buttons */
        JButton startBtn1 = new JButton();
        JButton stopBtn1 = new JButton();
        JButton startBtn2 = new JButton();
        JButton stopBtn2 = new JButton();
        this.setButtons(startBtn1, stopBtn1, startBtn2, stopBtn2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        /* Designing the frame */
        panelTitle.setPreferredSize(new Dimension(400, 45));

        labelTitle.setFont(new Font("Arial Black", 1, 28));
        labelTitle.setText("Control Panel");
        labelTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        panelTitle.add(labelTitle);

        panelSubtitle.setPreferredSize(new Dimension(40, 35));
        panelSubtitle.setLayout(new FlowLayout(FlowLayout.LEFT, 35, 5));

        labelSubtitle.setFont(new Font("Arial", 0, 20));
        labelSubtitle.setText("Available Servers: ");
        labelSubtitle.setHorizontalTextPosition(SwingConstants.CENTER);
        panelSubtitle.add(labelSubtitle);

        panelHttp.setPreferredSize(new Dimension(40, 42));

        labelHttp.setFont(new Font("Arial", 0, 16));
        labelHttp.setText("Http");

        startBtn1.setFont(new Font("Arial", 1, 18));
        startBtn1.setText("Start");
        startBtn1.setPreferredSize(new Dimension(100, 35));

        stopBtn1.setFont(new Font("Arial", 1, 18));
        stopBtn1.setText("Stop");
        stopBtn1.setPreferredSize(new Dimension(100, 35));

        GroupLayout panelHttpLayout = new GroupLayout(panelHttp);
        panelHttp.setLayout(panelHttpLayout);
        panelHttpLayout.setHorizontalGroup(
            panelHttpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelHttpLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(labelHttp)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startBtn1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(stopBtn1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        panelHttpLayout.setVerticalGroup(
            panelHttpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelHttpLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(panelHttpLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHttp)
                    .addComponent(startBtn1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopBtn1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        panelTransfer.setPreferredSize(new Dimension(40, 42));

        labelTransfer.setFont(new Font("Arial", 0, 16));
        labelTransfer.setText("Transfer");

        startBtn2.setFont(new Font("Arial", 1, 18));
        startBtn2.setText("Start");
        startBtn2.setPreferredSize(new Dimension(100, 35));

        stopBtn2.setFont(new Font("Arial", 1, 18));
        stopBtn2.setText("Stop");
        stopBtn2.setPreferredSize(new Dimension(100, 35));

        GroupLayout panelTransferLayout = new GroupLayout(panelTransfer);
        panelTransfer.setLayout(panelTransferLayout);
        panelTransferLayout.setHorizontalGroup(
            panelTransferLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelTransferLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(labelTransfer)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startBtn2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(stopBtn2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        panelTransferLayout.setVerticalGroup(
            panelTransferLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelTransferLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(panelTransferLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTransfer)
                    .addComponent(startBtn2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopBtn2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelSubtitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(panelHttp, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(panelTransfer, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(panelSubtitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(panelHttp, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTransfer, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        /* -------------------------- */

        this.pack();
        this.setLocationRelativeTo(null);

        // Asiana Listener daholo ireo boutons
        for (int i = 0; i < this.geButtons().length; i++) {
            JButton btn = this.geButtons()[i];

            btn.addActionListener(new ListenerControlPanel(this, btn));

            if (i % 2 == 1) {
                btn.setEnabled(false);
            }
        }
    }
    
    // getter
    public ServeurHttp getServeurHttp() {
        return this.serveurHttp;
    }
    public ServeurTransfert getServeurTransfert() {
        return this.serveurTransfert;
    }
    public JButton[] geButtons() {
        return this.buttons;
    }

    // setter
    public void setServeurHttp(ServeurHttp serveurHttp) {
        this.serveurHttp = serveurHttp;
    }
    public void setServeurTransfert(ServeurTransfert serveurTransfert) {
        this.serveurTransfert = serveurTransfert;
    }
    public void setButtons(JButton startBtn1, JButton stopBtn1, JButton startBtn2, JButton stopBtn2) {
        this.buttons = new JButton[4];
        buttons[0] = startBtn1;
        buttons[1] = stopBtn1;
        buttons[2] = startBtn2;
        buttons[3] = stopBtn2;
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlPanel().setVisible(true);
            }
        });
    }            
}