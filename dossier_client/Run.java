package aff;
import fenetre.*;
import client.Client;
public class Run {
    public static void main(String[] args) {
        Client client = new Client();
        if (client.getS().isConnected()) {
            new Fenetre(client);    
        }
            
    }
    
}
