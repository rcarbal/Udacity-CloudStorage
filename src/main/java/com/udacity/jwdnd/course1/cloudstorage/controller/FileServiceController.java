package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("files")
public class FileServiceController {

    private FileService fileService;

    public FileServiceController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<File> getAllFiles(int userId){
        return fileService.getAllFiles(userId);
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file,
                             Authentication auth,
                             Model model){
        fileService.addFile(auth.getName(), file);
        return null;
    }
}
