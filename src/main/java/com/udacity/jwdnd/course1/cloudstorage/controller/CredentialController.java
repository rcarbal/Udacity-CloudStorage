package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.messages.CredentialEnum;
import com.udacity.jwdnd.course1.cloudstorage.messages.ResultsEnum;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(UserService userService,
                                CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    private String addCredentials(@ModelAttribute("cred")Credential cred, Authentication auth, Model model){

        Integer credentialId = cred.getCredentialId();

        int userId = userService.getUserById(auth.getName());
        cred.setUserId(userId);

        if (credentialId != null){
            cred.setCredentialId(credentialId);
            int i = credentialService.updateCredentials(cred);

            if (i > 0){
                model.addAttribute("result", new Result(ResultsEnum.SUCCESS.getKey(), CredentialEnum.UPDATE.get()));
            }

        }
        else {
            int i = credentialService.addCredentials(cred);

            if (i > 0){
                model.addAttribute("result", new Result(ResultsEnum.SUCCESS.getKey(), CredentialEnum.SAVED.get()));
            }
        }
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    private String deleteCredentials(@PathVariable long credentialId, Authentication auth, Model model){

        int i = credentialService.deleteCredential(credentialId);
        if (i > 0){
            model.addAttribute("result", new Result(ResultsEnum.SUCCESS.getKey(), CredentialEnum.DELETED.get()));
        }

        return "result";
    }
}
