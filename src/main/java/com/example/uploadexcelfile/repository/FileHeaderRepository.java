package com.example.uploadexcelfile.repository;

import com.example.uploadexcelfile.model.FileHeadersInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileHeaderRepository extends PagingAndSortingRepository<FileHeadersInfo, Long>  {

    List<FileHeadersInfo> findByFileId(long id);
}
