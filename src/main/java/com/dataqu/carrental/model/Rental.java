package com.dataqu.carrental.model;

import javax.persistence.*;

@NamedEntityGraph(
        name = "rentals-all-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "customer"),
                @NamedAttributeNode(value = "company"),
                @NamedAttributeNode(value = "dailyCost"),
                @NamedAttributeNode(value = "days")
        }
)
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "daily_cost")
    private Double dailyCost;

    @Column(name = "days")
    private Integer days;

    public Rental() {
    }

    public Rental(Long id, Customer customer, Company company, Double dailyCost, Integer days) {
        this.id = id;
        this.customer = customer;
        this.company = company;
        this.dailyCost = dailyCost;
        this.days = days;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(Double dailyCost) {
        this.dailyCost = dailyCost;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
