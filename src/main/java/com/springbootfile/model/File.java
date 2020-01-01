package com.springbootfile.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class File {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NonNull
    private String fileName;

    @NonNull
    private String fileType;

    @Lob
    @NonNull
    private byte[] data;

}
