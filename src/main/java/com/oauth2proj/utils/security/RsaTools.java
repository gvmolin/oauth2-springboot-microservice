package com.oauth2proj.utils.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public record RsaTools() {
    
    public void generateKeyPair() throws IOException, NoSuchAlgorithmException{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

            
            this.manageKeyFile("private", privateKey);
            this.manageKeyFile("public", publicKey);
            
    }

    private void manageKeyFile(String type, String key) throws IOException{
        String currentDir = System.getProperty("user.dir");
        String keysPath = (currentDir + "/keys/").replace("/", "\\");
        String fullPath = keysPath + type.toLowerCase() + "-key.pem";
        String parsedFullPath = fullPath.replace("/", "\\");

        Boolean keysPathExists = new File(keysPath).exists();
        if(!keysPathExists) {
            Files.createDirectory(Paths.get(keysPath));
        }

        Boolean fullPathExists = new File(parsedFullPath).exists();
        if(fullPathExists) return;
        
        writeFile(key, fullPath, type);
    }

    private void writeFile(String key, String path, String type) throws IOException{
        String content = (
            "-----BEGIN " + type.toUpperCase() + " KEY-----\n" + 
            key +
            "\n-----END " + type.toUpperCase() + " KEY-----" 
        );

        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        }
    }


}
