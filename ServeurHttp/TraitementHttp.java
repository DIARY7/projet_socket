package traitement;

import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TraitementHttp implements Runnable {
    Socket client;

    public TraitementHttp() {

    }
    public TraitementHttp(Socket client) {
        this.setClient(client);
    }

    // getter
    public Socket getClient() {
        return this.client;
    }

    // setter
    public void setClient(Socket client) {
        this.client = client;
    }

    /* ----------------------------- */
    public boolean isImgOrGif(File file, String extension) {
        boolean valiny = false;

        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("webp") || extension.equalsIgnoreCase("gif")) {
            valiny = true;
        }

        return valiny;
    }

    public String getFileContentType(File file) throws Exception {
        String valiny = new String();

        String extension = file.getName().split("\\.(?=[^.]*$)")[1]; // Ilay dernier point no alainy , ?= dia contraire @ ?!
        
        if (isImgOrGif(file, extension)) {
            valiny = "Content-type : image/" + extension + "\r\n";
        }
        else {
            if (extension.compareToIgnoreCase("html")==0 || extension.compareToIgnoreCase("php") == 0) {
                valiny = "Content-type : text/html\r\n";      
            }
            else if (extension.compareToIgnoreCase("css")==0) {
                valiny = "Content-type : text/css\r\n";
            }
            else if (extension.compareToIgnoreCase("js")==0) {
                valiny = "Content-type : text/javascript\r\n";
            }
            else if (extension.compareToIgnoreCase("mp4")==0) {
                valiny = "Content-type : video/mp4\r\n";
            }
        }

        return valiny;
    }

    // Pour recuperer le contenu d'un file
    public byte[] getFileContent(File file, String[] params, String statusCode,String method) throws Exception {
        String header = new String();
        if (statusCode.compareToIgnoreCase("404") == 0) {
            header = "HTTP/1.1 404 Not Found\r\n";
        }
        else if (statusCode.compareToIgnoreCase("200") == 0) {
            header = "HTTP/1.1 200 OK\r\n";
        }

        header += this.getFileContentType(file);
        header += "\r\n";

        byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
        byte[] data;

        if (this.isPHPFile(file)) {
            data = this.getPHPFileContent(file, params,method);
        } else {
            FileInputStream fileInputStream = new FileInputStream(file);
            data = new byte[(int) file.length()];
            fileInputStream.read(data);
            fileInputStream.close();
        }

        byte[] valiny = new byte[headerBytes.length + data.length];
        System.arraycopy(headerBytes, 0, valiny, 0, headerBytes.length);
        System.arraycopy(data, 0, valiny, headerBytes.length, data.length);

        return valiny;
    }

    public File getIndex(File dir) throws Exception {
        File file = new File(dir.getPath() + "/index.html");
        return file;
    }

    public String getRelativePath(String path) {
        String valiny = new String();

        if (path.strip().equals("root")) {
            return valiny = "/";
        }

        valiny = path.split("root")[1];
        valiny = valiny.replaceAll("\\\\", "/");

        return valiny;
    }

    public String assetsRelativePath(String ressource) {
        String valiny = new String();

        int nbSlash = 0;
        for (int i = 0; i < ressource.length(); i++) {
            if (ressource.charAt(i) == '/') {
                nbSlash++;
            }
        }
        nbSlash++; // On doit mettre ca car on inclut pas le premier "/" dans le relativePath

        for (int i = 0; i < nbSlash; i++) {
            valiny += "../";
        }

        return valiny;
    }

    // Contenu d'un directory mais en format Html
    public String generateDirContent(File dir, String ressource, String relativePath) throws Exception { 
        String valiny = new String();

        File[] contenus = dir.listFiles(); // Les contenus: Dossiers ou fichiers

        /* Naviguer directement vers l'index si il y a index.html */
        for (int i = 0; i < contenus.length; i++) {
            if (contenus[i].getName().compareTo("index.html") == 0 ) {
                return null;
            }
        }
        /* ------ */

        valiny = "<h1>Index of " + relativePath + "</h1>";

        valiny += "<table>\r\n";
        valiny += "<tr>\r\n";
        valiny += "<th valign=\"top\"></th>\r\n";
        valiny += "<th>Name</th>\r\n";    
        valiny += "<th>Last modified</th>\r\n";    
        valiny += "<th>Size</th>\r\n";    
        valiny += "</tr>\r\n";
        valiny += "<tr>\r\n";
        valiny += "<th colspan=\"4\"><hr></th>\r\n";
        valiny += "</tr>\r\n";

        if (dir.getParent() != null) { // Root est le plus grand, Root n'a pas de parent
            String iconPath = this.assetsRelativePath(ressource) + "utilitaire/icons/";
            
            valiny += "<tr>";
            valiny += "<td></td>";
            valiny += "<td class=\"fileNameTd\"><a href=\"../\" class=\"parentDirLink\">";
            valiny += "<img src=\"" + iconPath + "retour.png\" alt=\"\" class=\"icon\">";
            valiny += "Parent Directory</a></td>"; // Revenir vers le repertoire parent
            valiny += "</tr>";
        }

        for (int i = 0; i < contenus.length; i++) {
            valiny += "<tr>";

            /* Les icones fichier et dossier */
            valiny += "<td class=\"iconTd\">";
            String iconPath = this.assetsRelativePath(ressource) + "utilitaire/icons/";
            if (contenus[i].isFile()) {
                valiny += "<img src=\"" + iconPath + "fichier.png\" alt=\"\" class=\"icon\">";
            }
            else {
                valiny += "<img src=\"" + iconPath + "dossier.png\" alt=\"\" class=\"icon\">";
            }
            valiny += "</td>";
            /* ------------- */
           
            valiny += "<td class=\"fileNameTd\"><a href=\"";
            valiny += contenus[i].getName() + "\">" + contenus[i].getName() + "</a></td>";

            LocalDateTime lastModified = LocalDateTime.ofInstant(Instant.ofEpochMilli(contenus[i].lastModified()), ZoneId.systemDefault());

            // Formatter la date au format 'yyyy-MM-dd HH:mm'
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = lastModified.format(formatter);

            valiny += "<td>" + formattedDateTime + "</td>";
            valiny += "<td>" + contenus[i].length() + "</td>";

            valiny += "<tr>";
        };
        valiny += "<th colspan=\"4\"><hr></th>\r\n";
        valiny += "</table>\r\n";

        return valiny;
    }

    public String generateDirHtmlStructure(File dir, String ressource) throws Exception {
        String valiny = new String();

        String relativePath = this.getRelativePath(dir.getPath());
        String cssRelativePath = this.assetsRelativePath(ressource) + "utilitaire/css/style.css";

        valiny = "<!DOCTYPE html>\r\n";
        valiny += "<html lang=\"en\">\r\n";
        valiny += "<head>\r\n";
        valiny += "<meta charset=\"UTF-8\">\r\n";
        valiny += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n";
        valiny += "<link rel=\"stylesheet\" href=\"" + cssRelativePath + "\">\r\n";
        valiny += "<title>Index of " + relativePath + "</title>\r\n";
        valiny += "</head>\r\n";
        valiny += "<body>\r\n";

        String dirContent = this.generateDirContent(dir, ressource, relativePath);
        if (dirContent == null) { // On a trouve index.html
            return null;
        }
        valiny += dirContent;

        valiny += "</body>\r\n";
        valiny += "</html>\r\n";

        return valiny;
    }

    

    /* PHP */
    boolean isPHPFile(File file) {
        if (file.getName().endsWith(".php")) {
            return true;
        }
        return false;
    }
    private String getParamPost(BufferedReader bfr)  {
        /* Maka an'ilay body an'ilay message */
        StringBuilder requestBody = new StringBuilder();
        try {
            String entete = "";
            String line = null;
            
            while ( (line = bfr.readLine()) !=null && !line.isEmpty() ) {
                entete += line;     
            }
            
            /* Tsy maintsy zao satria le readLine mamaky jusqu'a ce qu'il trouve \n */
            while (bfr.ready()) {
                char c = (char) bfr.read();
                requestBody.append(c);
            }    
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    
        return requestBody.toString();
            
    } 
    String[] getParametersFromRessource(String ressource,String method,BufferedReader bfr) {
        
        try {
            String query = null;
            //Methode POST
            if (method.strip().compareToIgnoreCase("POST") == 0 ) {
                query = getParamPost(bfr);
            }

            //Methode GET
            else if (method.strip().compareToIgnoreCase("GET") == 0) {
                URI uri = new URI(ressource);
                query = uri.getQuery();    
            }
            
            System.out.println("La variable ressource=  " + ressource);
            System.out.println("La variable query=" + query);
            if (query != null) {
                String[] params = query.split("&");

                return params;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    String getRealRessource(String ressource) {
        if (ressource.contains("?")) {
            return ressource.split("\\?")[0];
        } else {
            return ressource;
        }
    }

    public byte[] getPHPFileContent(File file, String[] params,String method) throws IOException,Exception {
        List<String> command = new ArrayList<>();
        ProcessBuilder processBuilder = null;
        Process process = null;
        if (method.strip().compareToIgnoreCase("GET") == 0 ) {
            command.add("php-cgi");
            command.add("-f");
            command.add(file.getAbsolutePath());
            if (params != null) {
                for (String param : params) {
                    String[] keyValue = param.split("=", 2);
                    if (keyValue.length == 2) {
                        command.add(keyValue[0] + "=" + keyValue[1]);
                    } else {
                        System.err.println("Paramètre invalide : " + param);
                    }
                }
            }
            System.out.println(command);
        
            processBuilder = new ProcessBuilder(command);
            process = processBuilder.start();    
        }
        else if (method.strip().compareToIgnoreCase("POST") == 0 ) {
            
            command.add("php-cgi");
            command.add("-q");
            command.add("-f");
            command.add(file.getAbsolutePath());
            command.add("REQUEST_METHOD=POST");

            System.out.println(command);

            processBuilder = new ProcessBuilder(command);
            process = processBuilder.start();

            // Write parameters to the process's output stream
            if (params != null && params.length > 0) {
                try (OutputStream outputStream = process.getOutputStream()) {
                    // Specify the content type and content length in the header
                    String paramString = String.join("&", params);
                    System.out.println(paramString);
                    String header = "Content-Type: application/x-www-form-urlencoded\r\n";
                    header += "Content-Length: " + paramString.length() + "\r\n\r\n";
        
                    outputStream.write(header.getBytes());
                    outputStream.write(paramString.getBytes());

                    outputStream.flush();
                    
                    
                } catch (IOException e) {
                    throw new IOException("Error writing parameters to the process", e);
                }
            }
        }
        
        // Lire la sortie du script PHP
        InputStream phpOutput = process.getInputStream();
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = phpOutput.read(buffer)) != -1) {
            outputBuffer.write(buffer, 0, bytesRead);
        }

        return outputBuffer.toByteArray(); 
        
    }
    /* ---------------------- */

    public byte[] getResponse(BufferedReader bfr ) throws Exception {
        String url = bfr.readLine();
        System.out.println("La ressource demander: " + url);
        byte[] valiny = null;

        String[] splittedUrl = url.split("\\s"); 

        String ressource = splittedUrl[1]; // La ressource a rechercher
        String method = splittedUrl[0];
        String[] params = this.getParametersFromRessource(ressource,method,bfr); // Les parametres s'il y en a
        ressource = this.getRealRessource(ressource);
        System.out.println(ressource);

        File file = null;
        if (ressource.startsWith("/utilitaire")) { // Les css ou les icones personnalisees du serveur
            file = new File(ressource.substring(1));
        }
        else if (ressource.strip().equals("/")) { // Pas de precision sur le dossier qu'on veut voir donc on lui montre le root
            file = new File("root");
        }
        else {
            file = new File("root" + ressource);
        }

        if (!file.exists()) {
            file = new File("utilitaire/404.html");
            valiny = this.getFileContent(file, null, "404",method);
        }

        if (file.isFile()) {
            valiny = this.getFileContent(file, params, "200",method);
        }
        else if (file.isDirectory()) {
            String stringResponse = new String();

            if (!ressource.endsWith("/")) {
                ressource += "/";
                stringResponse = "HTTP/1.1 301 Moved Permanently\r\nLocation: " + ressource + "\r\n\r\n";
            }
            else {
                stringResponse = this.generateDirHtmlStructure(file, ressource);
                if (stringResponse == null) {
                    file = this.getIndex(file);
                    return this.getFileContent(file, params, "200",method);
                }

                stringResponse = "HTTP/1.1 200 OK\r\n\r\n" + stringResponse;
            }

            valiny = stringResponse.getBytes(StandardCharsets.UTF_8);
        }

        return valiny;
    }

    @Override
    public void run() {
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // if (url != null) {
                byte[] reponse = this.getResponse(bufferedReader);

                OutputStream outputStream = client.getOutputStream();
                outputStream.write(reponse);
                outputStream.flush();
            //}
            bufferedReader.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}