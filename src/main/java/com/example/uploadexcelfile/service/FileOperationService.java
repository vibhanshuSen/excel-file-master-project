package com.example.uploadexcelfile.service;


import com.example.uploadexcelfile.exception.FileException;
import com.example.uploadexcelfile.dto.UploadedFileAttributeDTO;
import com.example.uploadexcelfile.model.FileHeadersInfo;
import com.example.uploadexcelfile.model.FileRecords;
import com.example.uploadexcelfile.model.UploadedFile;
import com.example.uploadexcelfile.repository.FileHeaderRepository;
import com.example.uploadexcelfile.repository.FileRecordsRepository;
import com.example.uploadexcelfile.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileOperationService extends BaseUserService {

    private FileRepository fileRepository;
    private FileHeaderRepository fileHeaderRepository;
    private FileRecordsRepository fileRecordsRepository;
    private FileRecordsService fileRecordsService;
    private FileHeaderService fileHeaderService;

    @Transactional
    public UploadedFile save(final UploadedFile uploadedFile) {
        log.info("Save uploaded-file");
        return fileRepository.save(uploadedFile);
    }

    @Transactional(readOnly = true)
    public List<UploadedFile> getAllUploadedFiles() {
        log.info("fetch all uploaded file list");
        return (List<UploadedFile>) fileRepository.findAll();
    }

    @Transactional
    public void deleteFileById(long uploadedFileId) {
        log.info("Delete uploaded-file");
        UploadedFile uploadedFile = getByFileId(uploadedFileId);
        List<FileHeadersInfo> fileHeadersInfoList = fileHeaderRepository.findByFileId(uploadedFileId);
        List<FileHeadersInfo> updatedHeaderList = fileHeadersInfoList
                .stream()
                .map(fileHeader -> fileHeader
                        .toBuilder().deleted(Boolean.TRUE)
                        .updatedDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        List<FileRecords> fileRecordsList = fileRecordsRepository.findByFileId(uploadedFileId);
        List<FileRecords> updatedRecordsList = fileRecordsList
                .stream()
                .map(fileHeader -> fileHeader
                        .toBuilder().deleted(Boolean.TRUE)
                        .updatedDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        fileHeaderService.saveAll(updatedHeaderList);
        fileRecordsService.saveAll(updatedRecordsList);
        save(uploadedFile.toBuilder().deleted(Boolean.TRUE).updatedDate(LocalDateTime.now()).build());
    }

    @Transactional(readOnly = true)
    public UploadedFile getByFileId(Long id) throws FileException {
        log.info("Fetch file by Id");
        return fileRepository
                .findById(id)
                .orElseThrow(() -> new FileException(HttpStatus.NOT_FOUND.value(), "File details not found."));
    }

    @Transactional(readOnly = true)
    public UploadedFileAttributeDTO getUploadedFileDetailsById(Long fileId) throws FileException {
        log.info("Fetch file attributes by Id");
        UploadedFile dbUploadedFile = getByFileId(fileId);
        saveUserDetails(dbUploadedFile);
        final List<FileHeadersInfo> fileHeadersInfoList = fileHeaderRepository.findByFileId(fileId);
        final List<FileRecords> fileRecordsList = fileRecordsRepository.findByFileId(fileId);
        return new UploadedFileAttributeDTO().toBuilder().fileName(dbUploadedFile.getFileName()).totalRecords(dbUploadedFile.getTotalRecords())
                .totalHeaders(dbUploadedFile.getTotalHeaders())
                .status(dbUploadedFile.getStatus())
                .deleted(dbUploadedFile.getDeleted())
                .uploadBy(dbUploadedFile.getUploadBy()).totalUploaded(dbUploadedFile.getTotalUploaded()).
                lastReviewedBy(dbUploadedFile.getLastReviewedBy()).lastReviewedTime(dbUploadedFile.getLastReviewedTime()).
                fileHeadersInfoList(fileHeadersInfoList).fileRecordsList(fileRecordsList).
                build();
    }

    private void saveUserDetails(UploadedFile dbUploadedFile) {

        UploadedFile uploadedFile = dbUploadedFile
                .toBuilder()
                .lastReviewedBy(Math.toIntExact(getAuthenticatedUser().getId()))
                .lastReviewedTime(LocalDateTime.now())
                .build();
        fileRepository.save(uploadedFile);
    }

    public UploadedFile checkFileStatus(long fileId) {
        log.info("Checking file status");
        return fileRepository
                .findById(fileId)
                .orElseThrow(() -> new FileException(HttpStatus.NOT_FOUND.value(),
                        "File details not found."));
    }
}



