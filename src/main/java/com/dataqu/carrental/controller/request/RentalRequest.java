package com.dataqu.carrental.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalRequest {

    @NotNull(message = "The field dailyCost is required")
    private Double dailyCost;

    @NotNull(message = "The field days is required")
    private Integer days;
}
