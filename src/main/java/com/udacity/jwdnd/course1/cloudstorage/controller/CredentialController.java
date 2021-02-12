package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.messages.CredentialEnum;
import com.udacity.jwdnd.course1.cloudstorage.messages.ResultsEnum;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    private String addCredentials(@ModelAttribute("cred")Credential cred, Authentication auth, Model model){

        int userId = userService.getUserById(auth.getName());

        cred.setUsername(auth.getName());
        cred.setUserId(userId);

        int i = credentialService.addCredentials(cred);

        if (i > 0){
            model.addAttribute("result", new Result(ResultsEnum.SUCCESS.getKey(), CredentialEnum.SAVED.get()));
        }

        return "result";
    }
}
