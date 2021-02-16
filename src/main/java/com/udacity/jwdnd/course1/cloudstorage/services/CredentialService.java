package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialsById(int userId) {

        List<Credential> credentials = credentialsMapper.getCredentialsByUserId(userId);

        for (Credential credential: credentials){
            String password = credential.getPassword();
            String key = credential.getKey();

            String decryptedPassword = encryptionService.decryptValue(password, key);
            credential.setPassword(decryptedPassword);
            System.out.println();
        }

        return credentials;
    }

    public int addCredentials(Credential cred) {

        String password = cred.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        cred.setPassword(encryptedPassword);
        cred.setKey(encodedKey);

        return credentialsMapper.addCredential(cred);
    }
}
