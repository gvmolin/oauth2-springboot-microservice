package com.oauth2proj.utils.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

            
            this.writeKeyFile("private", privateKey);
            this.writeKeyFile("public", publicKey);
            
    }

    private void writeKeyFile(String type, String key) throws IOException{
        String currentDir = System.getProperty("user.dir");
        String fullPath = currentDir + "/keys/" + type.toLowerCase() + "-key.pem";
        String parsedFullPath = fullPath.replace("/", "\\");
        Boolean exists = new File(parsedFullPath).exists();
        if(exists) return;
        
        String content = (
            "-----BEGIN " + type.toUpperCase() + " KEY-----\n" + 
            key +
            "\n-----END " + type.toUpperCase() + " KEY-----" 
        );

        try (FileWriter fileWriter = new FileWriter(parsedFullPath, false)) {
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        }
    }


}
