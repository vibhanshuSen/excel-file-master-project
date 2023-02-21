package com.example.uploadexcelfile.service;


import com.example.uploadexcelfile.model.FileRecords;
import com.example.uploadexcelfile.repository.FileRecordsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FileRecordsService extends BaseUserService {

    private FileRecordsRepository fileRecordsRepository;

    @Transactional
    public void saveAll(final List<FileRecords> fileRecordsList) {
        log.info("Bulk save file records");
        fileRecordsRepository.saveAll(fileRecordsList);
    }
}
