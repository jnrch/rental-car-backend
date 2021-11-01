package com.dataqu.carrental.service;

import com.dataqu.carrental.model.dto.CustomerDto;
import com.dataqu.carrental.model.dto.CustomerTotalRentAmount;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    List<CustomerDto> getAllCustomers();
    List<CustomerDto> getAllCustomersSortByLastName();
    List<CustomerTotalRentAmount> getAllCustomersSortByRentExpenses();
    Map<String, Double> getCustomersSortByTotalAmount(Long companyId);
    CustomerDto getCustomerById(Long customerId);
}
