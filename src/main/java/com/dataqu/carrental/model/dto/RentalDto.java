package com.dataqu.carrental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalDto implements Serializable {

    private Long id;
    private CustomerDto customer;
    private CompanyDto company;
    private Double dailyCost;
    private Integer days;
}
