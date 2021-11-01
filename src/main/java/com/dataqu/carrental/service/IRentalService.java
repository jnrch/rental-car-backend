package com.dataqu.carrental.service;

import com.dataqu.carrental.controller.request.RentalRequest;
import com.dataqu.carrental.model.dto.RentalDto;

import java.util.List;

public interface IRentalService {

    List<RentalDto> findAllRentals();
    RentalDto createRental(RentalRequest rentalRequest, Long customerId, Long companyId);
}
