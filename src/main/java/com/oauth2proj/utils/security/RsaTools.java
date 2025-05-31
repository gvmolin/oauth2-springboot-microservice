package com.oauth2proj.utils.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
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

    public String getPublicKey() throws IOException{
        String currentDir = System.getProperty("user.dir");
        String keysPath = (currentDir + "/keys/").replace("/", "\\");
        String fullPath = keysPath + "public-key.pem";

        String fullKey = Files.readString(Paths.get(fullPath));
        String sanitizedKey = fullKey
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "");

        return sanitizedKey;
    }

    private PrivateKey loadPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        String currentDir = System.getProperty("user.dir");
        String keysPath = (currentDir + "/keys/").replace("/", "\\");
        String fullPath = keysPath + "public-key.pem";

        String fullKey = Files.readString(Paths.get(fullPath));
        String sanitizedKey = fullKey
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "");

        byte[] decodedKey = Base64.getDecoder().decode(sanitizedKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
        
    }

    public String signToken(String token) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, InvalidKeySpecException{
        byte[] signatureBytes;

        Signature signature = Signature.getInstance("SHA256withRSA"); // Algoritmo de assinatura para RS256
        signature.initSign(this.loadPrivateKey());
        signature.update(token.getBytes(StandardCharsets.UTF_8)); // Passa os bytes da string de entrada
        signatureBytes = signature.sign();

        return Arrays.toString(signatureBytes);
    }

    private void manageKeyFile(String type, String key) throws IOException{
        String currentDir = System.getProperty("user.dir");
        String keysPath = (currentDir + "/keys/").replace("/", "\\");
        String fullPath = keysPath + type.toLowerCase() + "-key.pem";
        String parsedFullPath = fullPath.replace("/", "\\");

        Boolean keysPathExists = new File(keysPath).exists();
        if(!keysPathExists) {
            Files.createDirectory(Paths.get(keysPath));
            log.info("/keys folder created successfully.");
        } else {
            log.info("found /keys folder.");
        }

        Boolean fullPathExists = new File(parsedFullPath).exists();
        if(fullPathExists) {
            log.info("Found " + type + " key file.");
            return;
        }
        
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

            log.info(type + " key file created successfully.");
        }
    }

    


}
