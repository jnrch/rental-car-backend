package com.dataqu.carrental.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    private String timestamp;
    private Integer status;
    private List<String> error;
    private String message;
    private String path;
}
