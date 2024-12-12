package client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Client {
    Socket serveur;
    int port = 2006;
    String adress = "localhost";
   
    public Client()  {
        try {
            this.serveur = new Socket(adress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // getters
    public Socket getS() {
        return this.serveur;
    }

    // setters
    public void setS(Socket serveur) {
        this.serveur = serveur;
    }

    public void sendMessage(String pathFile){ // Appelen'ny listener Button swing
        try {
            if (serveur.isConnected()) {       
                if (pathFile!=null) {
                    System.out.println("Ok");
                    sendFile(pathFile);
                }
            }    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String path) throws Exception {
        System.out.println("Hello");
        int lastSlash = path.lastIndexOf("\\");
        String toFile = path.substring(0, lastSlash+1);
        String nameFile = path.substring(lastSlash+1,path.length());
        toFile = toFile.replace("\\", "\\\\");
        System.out.println(toFile);
        
        File file = new File(toFile+nameFile);
        if (!file.exists()) {
            throw new Exception("Fichier introuvable");
        }

        // Mandefa anaran'ilay fichier alony
        PrintWriter stylo = new PrintWriter(serveur.getOutputStream(),true);
        stylo.println(nameFile);
        
        //Mandefa contenu an'ilay fichier avy eo
        FileInputStream fIS = new FileInputStream(toFile+nameFile);
        BufferedOutputStream os = new BufferedOutputStream(serveur.getOutputStream());
        byte[] tampon = new byte[1024];
        int byteLue;
        while ( (byteLue = fIS.read(tampon)) != -1 ) {
            os.write(tampon, 0, byteLue);
            System.out.println(byteLue);
        }
        
        //serveur.shutdownOutput(); // Les donnes sont envoye 
        fIS.close();
        os.flush();
        //os.close();
        System.out.println("Meeee");
        
        // this.pathFile =null;
    }

    // Code ChatGPT
    public void sendDossier(String path) throws Exception { // Appelena ary @ Listener
        int lastSlash = path.lastIndexOf("\\");
        String toFile = path.substring(0, lastSlash+1);
        String nameDir = path.substring(lastSlash+1,path.length());
        toFile = toFile.replace("\\", "\\\\");
        
        PrintWriter stylo = new PrintWriter(serveur.getOutputStream(),true);
        stylo.println(nameDir);

        File folderToSend = new File(toFile+nameDir);
        String zipFileName = nameDir+".zip";
        FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        
        zipFolder(folderToSend, folderToSend.getName()+"/", zipOutputStream);
        
        zipOutputStream.close();
        fileOutputStream.close();

        //Mandefa contenu an'ilay fichier avy eo
        FileInputStream fileInputStream = new FileInputStream(zipFileName);
        BufferedOutputStream os = new BufferedOutputStream(serveur.getOutputStream());
        // OutputStream os = serveur.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer))!=-1) {
            os.write(buffer,0,bytesRead);
        }
       
        fileInputStream.close();
        os.flush();
        //os.close();
       
        //serveur.shutdownOutput();
        
        // Supprimena le .zip avy eo ao anaty dossier courant
        File fichier = new File(zipFileName);
        if (fichier.exists()) {
            fichier.delete();
            System.out.println("Voafafa");    
        }
        
    }

    private void zipFolder(File folder,String parentFolder,ZipOutputStream zipOutputStream) throws Exception {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(file, parentFolder+file.getName() +"/" , zipOutputStream);
                continue; // Tonga dia mandeha @ itération manaraka
            }
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipentry = new ZipEntry(parentFolder + file.getName());
            zipOutputStream.putNextEntry(zipentry);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zipOutputStream.write(buffer,0,bytesRead);
            }
            fis.close();
        }
    }
}