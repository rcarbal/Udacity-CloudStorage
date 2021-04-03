package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.messages.FileServiceEnum;
import com.udacity.jwdnd.course1.cloudstorage.messages.ResultsEnum;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
            boolean fileNameStores = fileService.checkFilename(userId, fileName);

            if (fileNameStores){
                model.addAttribute("result",
                        new Result(ResultsEnum.ERROR.getKey(), FileServiceEnum.DUPLICATE_FILE.getMessage()));
            } else {
                savedIndex = fileService.addFile(userId, file);
            }
        }

        if (savedIndex > -1 ){
            model.addAttribute("result",
                    new Result(ResultsEnum.SUCCESS.getKey(), FileServiceEnum.FILE_SAVED.getMessage()));
        }

        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable long fileId, Authentication authentication, Model model){

        int numFilesRemoved = fileService.removeFileById(fileId);

        if (numFilesRemoved > 0){
            model.addAttribute("result",
                    new Result(ResultsEnum.SUCCESS.getKey(), FileServiceEnum.FILE_DELETED.getMessage()));
        } else {
            model.addAttribute("result",
                    new Result(ResultsEnum.FAILED.getKey(), FileServiceEnum.FILE_DELETE_ERROR.getMessage()));
        }
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable long fileId, Authentication auth, Model model){
        File file = fileService.getFileById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFileName() + "\"").body(new
                ByteArrayResource(file.getFileData()));
    }
}
