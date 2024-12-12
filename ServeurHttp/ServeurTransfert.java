package serveur;

import traitement.TraitementTransfert;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServeurTransfert extends ServerSocket implements Runnable {
    static int port = 2006;
    Thread serverThread;

    public ServeurTransfert() throws Exception {
        super(port);
        this.startServer();
    }

    public void startServer() {
        try {
            this.serverThread = new Thread(this);
            this.serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!this.isClosed()) {
                Socket client = this.accept();

                System.out.println("Client socket " + client.getInetAddress() + " est connecté");

                new Thread(new TraitementTransfert(client)).start();
            }
        } catch (SocketException se) {
            // SocketException est levée lors de la fermeture du socket
            System.out.println("Le serveur de transfert de fichier a été fermé");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Code pour fermer le socket serveur si nécessaire
            if (!this.isClosed()) {
                try {
                    this.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}