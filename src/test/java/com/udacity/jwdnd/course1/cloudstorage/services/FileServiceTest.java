package com.udacity.jwdnd.course1.cloudstorage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    public void doNotAllowDuplicateFilename(){

        String filename = "notes.txt";
        int userId = 1;
        boolean filenameFound = fileService.checkFilename(userId, filename);

        Assertions.assertFalse(filenameFound);
    }

}