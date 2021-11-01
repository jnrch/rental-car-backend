package com.dataqu.carrental.repository;

import com.dataqu.carrental.model.Rental;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCompanyId(Long companyId);

    @EntityGraph(value = "rentals-all-graph", type = EntityGraph.EntityGraphType.LOAD)
    List<Rental> findAll();
}
