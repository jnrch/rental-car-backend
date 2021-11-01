package com.dataqu.carrental.service.implementation;

import com.dataqu.carrental.exception.ErrorMessages;
import com.dataqu.carrental.exception.ResourceNotFoundException;
import com.dataqu.carrental.model.Customer;
import com.dataqu.carrental.model.Rental;
import com.dataqu.carrental.model.dto.CustomerDto;
import com.dataqu.carrental.model.dto.CustomerTotalRentAmount;
import com.dataqu.carrental.model.dto.RentalDto;
import com.dataqu.carrental.repository.CustomerRepository;
import com.dataqu.carrental.repository.RentalRepository;
import com.dataqu.carrental.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.dataqu.carrental.exception.ErrorMessages.CUSTOMER_NOT_FOUND;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final Long TOTAL_AMOUNT = 40000L;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;
    private final ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, RentalRepository rentalRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        logger.info("Getting all customers");
        return Arrays.asList(mapper.map(customerList, CustomerDto[].class));
    }

    @Override
    public List<CustomerDto> getAllCustomersSortByLastName() {
        List<Customer> customerList = customerRepository.findAllByOrderByLastName();

        logger.info("Getting all customer sorted by last name");
        return Arrays.asList(mapper.map(customerList, CustomerDto[].class));
    }

    @Override
    public List<CustomerTotalRentAmount> getAllCustomersSortByRentExpenses() {
        List<Rental> rentalList = rentalRepository.findAll();
        List<RentalDto> rentalDtoList = Arrays.asList(mapper.map(rentalList, RentalDto[].class));

        Map<CustomerDto, Double> totalAmountInRents =
                rentalDtoList.stream().collect(Collectors.groupingBy(RentalDto::getCustomer, Collectors.summingDouble(RentalDto::getDailyCost)));

        List<CustomerTotalRentAmount> customerTotalRentAmountList = new ArrayList<>();

        for (Map.Entry<CustomerDto, Double> mapEntries: totalAmountInRents.entrySet()) {
            CustomerTotalRentAmount customerTotalRentAmount = new CustomerTotalRentAmount();
            customerTotalRentAmount.setId(mapEntries.getKey().getId());
            customerTotalRentAmount.setName(mapEntries.getKey().getName());
            customerTotalRentAmount.setLastName(mapEntries.getKey().getLastName());
            customerTotalRentAmount.setTotalAmount(mapEntries.getValue());

            customerTotalRentAmountList.add(customerTotalRentAmount);
        }
        customerTotalRentAmountList.sort(Comparator.comparing(CustomerTotalRentAmount::getTotalAmount).reversed());

        logger.info("Getting customers sorted by rent expenses decreasing");
        return customerTotalRentAmountList;
    }

    @Override
    public Map<String, Double> getCustomersSortByTotalAmount(Long companyId) {
        List<Rental> rentalList = rentalRepository.findByCompanyId(companyId);
        List<RentalDto> rentalDtoList = Arrays.asList(mapper.map(rentalList, RentalDto[].class));

        if (rentalDtoList.isEmpty()) {
            logger.error("Cannot find rentals for company id {}", companyId);
            throw new ResourceNotFoundException(ErrorMessages.RENTAL_LIST_NOT_FOUND);
        }
        Map<String, Double> totalDailyCostByRut = rentalDtoList.stream()
                .collect(Collectors.groupingBy(r -> r.getCustomer().getRut(), Collectors.summingDouble(RentalDto::getDailyCost)))
                .entrySet()
                .stream().filter(x -> x.getValue() > TOTAL_AMOUNT)
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        logger.info("Getting total daily cost by rut given company id {}", companyId);
        return totalDailyCostByRut;
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            logger.error("Customer with id {} cannot be found", customerId);
            throw new ResourceNotFoundException(CUSTOMER_NOT_FOUND);
        }
        return mapper.map(customer, CustomerDto.class);
    }
}
