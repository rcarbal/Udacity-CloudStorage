package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFilesById(int id) {
        return fileMapper.getFilesByUserId(id);
    }

    public int addFile(int userId, MultipartFile file) throws IOException {

        File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), userId,
                file.getBytes());

        int i = fileMapper.addFileById(newFile);
        System.out.println("File saved in index: " + i);
        return i;
    }

    public int removeFileById(long fileId) {
        return fileMapper.removeFileById(fileId);
    }

    public File getFileById(long fileId) {
        return fileMapper.getFileById(fileId);
    }
}
