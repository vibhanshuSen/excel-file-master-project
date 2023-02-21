package com.example.uploadexcelfile.service;


import com.example.uploadexcelfile.model.FileHeadersInfo;
import com.example.uploadexcelfile.repository.FileHeaderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FileHeaderService extends BaseUserService {

    private FileHeaderRepository fileHeaderRepository;

    @Transactional
    public FileHeadersInfo save(final FileHeadersInfo FileHeader) {
        log.info("Save file-header-info");
        return fileHeaderRepository.save(FileHeader);
    }

    @Transactional
    public void saveAll(final List<FileHeadersInfo> FileHeadersInfo) {
        log.info("Bulk save file-header-info");
        fileHeaderRepository.saveAll(FileHeadersInfo);
    }
}
