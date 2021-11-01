package com.dataqu.carrental.service;

import com.dataqu.carrental.model.dto.CompanyCustomerDto;
import com.dataqu.carrental.model.dto.CompanyDto;
import com.dataqu.carrental.model.dto.CompanyRentalProfitsDto;

import java.util.List;
import java.util.Map;

public interface ICompanyService {

    Map<String, List<CompanyCustomerDto>> getCompanyCustomersSortByName();
    List<CompanyRentalProfitsDto> getCompanyTotalRentalProfits();
    Map<String, Long> getCompaniesWithRentsEqualOrOverOneWeek();
    Map<Object, Double> getCustomersWithLessExpense();
    CompanyDto getCompanyById(Long companyId);

}
