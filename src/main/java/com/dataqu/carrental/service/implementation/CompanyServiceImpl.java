package com.dataqu.carrental.service.implementation;

import com.dataqu.carrental.exception.ResourceNotFoundException;
import com.dataqu.carrental.model.Company;
import com.dataqu.carrental.model.Rental;
import com.dataqu.carrental.model.dto.*;
import com.dataqu.carrental.repository.CompanyRepository;
import com.dataqu.carrental.repository.RentalRepository;
import com.dataqu.carrental.service.ICompanyService;
import com.dataqu.carrental.service.IRentalService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.dataqu.carrental.exception.ErrorMessages.COMPANY_NOT_FOUND;
import static java.util.stream.Collectors.counting;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final Long QUANTITY_OF_RENTS_EQUAL_OR_OVER_DAYS = 7L;
    private final RentalRepository rentalRepository;
    private final IRentalService rentalService;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    public CompanyServiceImpl(RentalRepository rentalRepository, IRentalService rentalService, CompanyRepository companyRepository, ModelMapper mapper) {
        this.rentalRepository = rentalRepository;
        this.rentalService = rentalService;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public Map<String, List<CompanyCustomerDto>> getCompanyCustomersSortByName() {
        List<Rental> rentalList = rentalRepository.findAll();

        List<CompanyCustomerDto> rentalDtoList2 = Arrays.asList(mapper.map(rentalList, CompanyCustomerDto[].class));

        Map<String, List<CompanyCustomerDto>> groupByCompany =
                rentalDtoList2.stream().distinct()
                        .collect(Collectors.groupingBy(CompanyCustomerDto::getCompanyName));

        logger.info("Getting all company customers");
        return groupByCompany;
    }

    @Override
    public List<CompanyRentalProfitsDto> getCompanyTotalRentalProfits() {
        List<RentalDto> rentalDtoList = rentalService.findAllRentals();

        Map<String, Double> totalRentalProfits =
                rentalDtoList.stream()
                        .collect(Collectors.groupingBy(r -> r.getCompany().getName(), Collectors.summingDouble(RentalDto::getDailyCost)))
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<CompanyRentalProfitsDto> companyRentalProfitsList = new ArrayList<>();

        for (Map.Entry<String, Double> mapEntries: totalRentalProfits.entrySet()) {
            CompanyRentalProfitsDto companyRentalProfits = new CompanyRentalProfitsDto();
            companyRentalProfits.setName(mapEntries.getKey());
            companyRentalProfits.setTotalAmount(mapEntries.getValue());

            companyRentalProfitsList.add(companyRentalProfits);
        }

        logger.info("Getting company total rental profits");
        return companyRentalProfitsList;
    }

    @Override
    public Map<String, Long> getCompaniesWithRentsEqualOrOverOneWeek() {
        List<RentalDto> rentalDtoList = rentalService.findAllRentals();


        logger.info("Getting rentals over one week for companies");
        return rentalDtoList.stream()
                .filter(value -> value.getDays() >= QUANTITY_OF_RENTS_EQUAL_OR_OVER_DAYS)
                .collect(Collectors.groupingBy(r -> r.getCompany().getName(),counting()));
    }

    @Override
    public Map<Object, Double> getCustomersWithLessExpense() {
        List<RentalDto> rentalDtoList = rentalService.findAllRentals();

        return rentalDtoList.stream()
                .collect(Collectors.groupingBy(r -> Pair.of(r.getCompany(), r.getCustomer()),
                        Collectors.summingDouble(RentalDto::getDailyCost)));
    }

    @Override
    public CompanyDto getCompanyById(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);

        if (company.isEmpty()) {
            logger.info("Company with id {} cannot be found", companyId);
            throw new ResourceNotFoundException(COMPANY_NOT_FOUND);
        }
        return mapper.map(company, CompanyDto.class);
    }
}
