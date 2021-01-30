package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.messages.FileServiceEnum;
import com.udacity.jwdnd.course1.cloudstorage.messages.ResultsEnum;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileServiceController {

    private com.udacity.jwdnd.course1.cloudstorage.services.FileService fileService;
    private UserService userService;

    public FileServiceController(com.udacity.jwdnd.course1.cloudstorage.services.FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public List<File> getAllFiles(int userId){
        return fileService.getAllFilesById(userId);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file,
                             Authentication auth,
                             Model model) throws IOException {

        int userId = userService.getUserById(auth.getName());

        int savedIndex = fileService.addFile(userId, file);

        if (savedIndex > 0){
            model.addAttribute("result",
                    new Result(ResultsEnum.SUCCESS.getKey(), FileServiceEnum.FILE_SAVED.getMessage()));
        }

        return "result";
    }
}
