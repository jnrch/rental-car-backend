package com.dataqu.carrental.service.implementation;

import com.dataqu.carrental.controller.request.RentalRequest;
import com.dataqu.carrental.model.Company;
import com.dataqu.carrental.model.Customer;
import com.dataqu.carrental.model.Rental;
import com.dataqu.carrental.model.dto.RentalDto;
import com.dataqu.carrental.repository.CompanyRepository;
import com.dataqu.carrental.repository.CustomerRepository;
import com.dataqu.carrental.repository.RentalRepository;
import com.dataqu.carrental.service.ICustomerService;
import com.dataqu.carrental.service.IRentalService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RentalServiceImpl implements IRentalService {

    private final Logger logger = LoggerFactory.getLogger(RentalServiceImpl.class);
    private final RentalRepository rentalRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final ICustomerService customerService;

    public RentalServiceImpl(RentalRepository rentalRepository, CompanyRepository companyRepository, CustomerRepository customerRepository, ModelMapper mapper, ICustomerService customerService) {
        this.rentalRepository = rentalRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.customerService = customerService;
    }

    @Override
    public List<RentalDto> findAllRentals() {
        logger.info("Getting all rentals");
        return Arrays.asList(mapper.map(rentalRepository.findAll(), RentalDto[].class));
    }

    @Override
    public RentalDto createRental(RentalRequest rentalRequest, Long customerId, Long companyId) {

        Company currentCompany = companyRepository.getById(companyId);
        Customer currentCustomer = customerRepository.getById(customerId);

        Rental rental = new Rental();
        rental.setCustomer(currentCustomer);
        rental.setCompany(currentCompany);
        rental.setDailyCost(rentalRequest.getDailyCost());
        rental.setDays(rentalRequest.getDays());

        logger.info("Creating rentals for customer id {} and company id {}", customerId, companyId);
        rentalRepository.save(rental);

        return mapper.map(rental, RentalDto.class);
    }
}
