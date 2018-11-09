package com.playground.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileDto {

    private String owner;
    private String fileName;
    private byte[] bytes;
}
