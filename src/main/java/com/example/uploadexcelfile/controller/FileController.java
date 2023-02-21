package com.example.uploadexcelfile.controller;


import com.example.uploadexcelfile.model.UploadedFile;
import com.example.uploadexcelfile.service.FileOperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    private final FileOperationService fileOperationService;

    @RequestMapping(method = RequestMethod.GET, path = "/checkStatus/{fileId}")
    public ResponseEntity<?> checkFileStatus(@PathVariable("fileId") int uploadedFileId) {
        try {
            return ResponseEntity.ok(fileOperationService.checkFileStatus(uploadedFileId));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{fileId}")
    public ResponseEntity<?> getUploadedFileById(@PathVariable("fileId") long uploadedFileId) {
        try {
            return ResponseEntity.ok(fileOperationService.getUploadedFileDetailsById(uploadedFileId));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getUploadedFiles")
    public ResponseEntity<List<UploadedFile>> getAllUploadedFiles() {
        return ResponseEntity.ok(fileOperationService.getAllUploadedFiles());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteById/{fileId}")
    public void deleteFileById(@PathVariable("fileId") int uploadedFileId) {
        fileOperationService.deleteFileById(uploadedFileId);
    }

}