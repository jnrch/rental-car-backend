package com.dataqu.carrental.controller;

import com.dataqu.carrental.model.dto.CustomerDto;
import com.dataqu.carrental.model.dto.CustomerTotalRentAmount;
import com.dataqu.carrental.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/sortByLastName")
    public ResponseEntity<List<CustomerDto>> findAllCustomerSortByLastName() {
        return ResponseEntity.ok(customerService.getAllCustomersSortByLastName());
    }

    @GetMapping("/company/{companyId}")
    public Map<String, Double> findCustomersAmountByCompany(@PathVariable Long companyId) {
        return customerService.getCustomersSortByTotalAmount(companyId);
    }

    @GetMapping("/rentExpenses")
    public List<CustomerTotalRentAmount> findCustomersSortByRentExpenses() {
        return customerService.getAllCustomersSortByRentExpenses();
    }
}
