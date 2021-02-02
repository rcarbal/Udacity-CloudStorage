package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.messages.FileServiceEnum;
import com.udacity.jwdnd.course1.cloudstorage.messages.ResultsEnum;
import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file,
                             Authentication auth,
                             Model model) throws IOException {

        int userId = userService.getUserById(auth.getName());
        int savedIndex = -1;

        String fileName = file.getOriginalFilename();

        if (fileName.equals("")){
            model.addAttribute("result",
                    new Result(ResultsEnum.FAILED.getKey(), FileServiceEnum.NO_FILE_FOUND.getMessage()));
        } else {
            savedIndex = fileService.addFile(userId, file);
        }

        if (savedIndex > -1 ){
            model.addAttribute("result",
                    new Result(ResultsEnum.SUCCESS.getKey(), FileServiceEnum.FILE_SAVED.getMessage()));
        }

        return "result";
    }
}
