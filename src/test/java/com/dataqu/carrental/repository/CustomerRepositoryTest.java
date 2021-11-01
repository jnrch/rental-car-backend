package com.dataqu.carrental.repository;

import com.dataqu.carrental.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldFindAllCustomerOrderedByLastName() {

        List<Customer> customerList = Arrays.asList(
                new Customer(1L, "33333-x", "Steve", "Arnold"),
                new Customer(2L, "22222-x", "Mark", "Smith"),
                new Customer(3L, "444444-x", "Kevin", "Love")
        );

        customerRepository.saveAll(customerList);

        List<Customer> customers = customerRepository.findAllByOrderByLastName();

        assertThat(customers).hasSize(3);
    }
}