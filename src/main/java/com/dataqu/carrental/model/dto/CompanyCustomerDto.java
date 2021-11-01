package com.dataqu.carrental.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyCustomerDto implements Serializable {

    @JsonIgnore
    private String companyName;
    private CustomerDto customer;
}
