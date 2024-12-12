package traitement;

import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TraitementTransfert implements Runnable {
    Socket client;

    public TraitementTransfert() {

    }
    public TraitementTransfert(Socket client) {
        this.client = client;
    }

    // Dossier azo avy any @ client dia .zip, donc il faut unzip
    // Genre de fonction recursive
    public static void unzip(String zipPath, String destDirectory) throws IOException {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipPath));

            byte[] buffer = new byte[1024];
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                String fullPath = destDirectory + File.separator + fileName;

                // Créer les répertoires si nécessaire
                new File(new File(fullPath).getParent()).mkdirs();

                try {
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fullPath));

                    int bytesRead;
                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    throw e;
                }

                // Passer à l'entrée ZIP suivante
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void transfertToRootDir(String fileName) throws Exception {
        boolean isDir = false;
        File file;

        if (fileName.contains(".")) {
            file = new File("root" + fileName);
            file.createNewFile();
        }
        else {
            file = new File("root" + fileName + ".zip");
            isDir = true;
        }

        InputStream inputStream = client.getInputStream();

        byte[] tampon = new byte[1024];
        int byteLue;
        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());

        while (( byteLue = inputStream.read(tampon)) != -1 ) { // Ny isan'ny byte lue dia mankao @ byteLue, -1 raha hoe fin an'ilay donnée
            fileOutputStream.write(tampon, 0 , byteLue);
            if (byteLue < 1024) { // Raha inférieur dia tokony dernier byte no vakiany
                break;
            }
        }
        // inputStream.close();
        fileOutputStream.close();

        System.out.println("Downloading...");
        if (isDir) {
            String zipPath = "root" + fileName + ".zip";
            String destination = "root";

            TraitementTransfert.unzip(zipPath, destination);
            file.delete();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream())); // PrintWritter no mandefa côté client, avy eo alain'ito BufferedReader ito

            String fileName = bufferedReader.readLine();
            if (fileName != null) {
                this.transfertToRootDir(fileName);
            }

            bufferedReader.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}