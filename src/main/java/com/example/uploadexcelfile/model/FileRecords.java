package com.example.uploadexcelfile.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "file_record_info")
@NoArgsConstructor
public class FileRecords extends BaseDetailEntity {

    private String recordDescription;
    @ManyToOne
    @JoinColumn(name = "file_id")
    @NotNull
    private UploadedFile uploadedFile;

    @ManyToOne
    @JoinColumn(name = "header_id")
    @NotNull
    private FileHeadersInfo fileHeadersInfo;



}

