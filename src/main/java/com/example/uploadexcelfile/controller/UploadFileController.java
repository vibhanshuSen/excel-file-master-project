package com.example.uploadexcelfile.controller;


import com.example.uploadexcelfile.exception.FileException;
import com.example.uploadexcelfile.constants.ConstantsUtils;
import com.example.uploadexcelfile.model.UploadedFile;
import com.example.uploadexcelfile.service.FileOperationService;
import com.example.uploadexcelfile.service.UploadFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.uploadexcelfile.enums.FileStatus.ERROR_OCCURRED;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@Slf4j
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;
    private FileOperationService fileOperationService;

    @RequestMapping(method = RequestMethod.POST, path = "/uploadFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadedFile> uploadExcelFile(@RequestPart("file") MultipartFile file)  {
        try {
            if (file != null) {
                log.info("Uploading file...!");
                return ResponseEntity.ok(uploadFileService.readExcelFile(file.getInputStream(), file.getOriginalFilename()));
            } else {
                log.info("File not found");
                throw new FileException(HttpStatus.NOT_FOUND.value(), "File not found.");
            }
        } catch (Exception exception) {
            if (file != null) {
                fileOperationService.save(uploadFileService.prepareFileDetails(file.getOriginalFilename(), ERROR_OCCURRED));
            }
            log.error("Error occurred due to :",exception.getCause());
            throw new FileException(HttpStatus.BAD_REQUEST.value(), String.format(ConstantsUtils.FILE_UPLOAD_FAILED, exception.getCause()));
        }
    }
}