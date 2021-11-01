package com.dataqu.carrental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalLessExpensesDto {

    private Long id;
    private CustomerDto customer;
    private CompanyDto company;
    private Double dailyCost;
}
