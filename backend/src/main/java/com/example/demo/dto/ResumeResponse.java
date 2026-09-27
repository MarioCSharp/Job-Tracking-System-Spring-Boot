package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResumeResponse {

    private Long id;
    private String fileName;
    private String contentType;
    private byte[] file;
}
