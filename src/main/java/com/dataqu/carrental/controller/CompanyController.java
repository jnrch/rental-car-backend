package com.dataqu.carrental.controller;

import com.dataqu.carrental.model.dto.CompanyCustomerDto;
import com.dataqu.carrental.model.dto.CompanyRentalProfitsDto;
import com.dataqu.carrental.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/companies")
@RestController
public class CompanyController {

    private final ICompanyService companyService;

    @Autowired
    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/customers")
    public ResponseEntity<Map<String, List<CompanyCustomerDto>>> findCompanyCustomerSortByLastName() {
        return ResponseEntity.ok(companyService.getCompanyCustomersSortByName());
    }

    @GetMapping("/profits")
    public List<CompanyRentalProfitsDto> findCompanyRentalProfits() {
        return companyService.getCompanyTotalRentalProfits();
    }

    @GetMapping("/rents")
    public Map<String, Long> findCompaniesWithRentsEqualOrOverOneWeek() {
        return companyService.getCompaniesWithRentsEqualOrOverOneWeek();
    }

    @GetMapping("/lessExpenses")
    public Map<Object, Double> findCustomersWithLessExpenses() {
        return companyService.getCustomersWithLessExpense();
    }
}
