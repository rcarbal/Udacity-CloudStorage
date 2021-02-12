package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialsMapper credentialsMapper;

    public CredentialService(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    public List<Credential> getAllCredentialsById(int userId) {
        return credentialsMapper.getCredentialsByUserId(userId);
    }

    public int addCredentials(Credential cred) {
        return credentialsMapper.addCredential(cred);
    }
}
