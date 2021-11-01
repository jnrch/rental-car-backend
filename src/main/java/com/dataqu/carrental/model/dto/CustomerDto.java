package com.dataqu.carrental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto implements Serializable {

    private Long id;
    private String rut;
    private String name;
    private String lastName;
}
