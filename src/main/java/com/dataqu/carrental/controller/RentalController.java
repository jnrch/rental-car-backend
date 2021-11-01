package com.dataqu.carrental.controller;

import com.dataqu.carrental.controller.request.RentalRequest;
import com.dataqu.carrental.model.dto.RentalDto;
import com.dataqu.carrental.service.IRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rentals")
@RestController
public class RentalController {

    private final IRentalService rentalService;

    @Autowired
    public RentalController(IRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/customer/{customerId}/company/{companyId}")
    public ResponseEntity<RentalDto> createRental(@RequestBody @Valid RentalRequest rentalRequest,
                                                  @PathVariable Long customerId,
                                                  @PathVariable Long companyId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createRental(rentalRequest, customerId, companyId));
    }
}
