package ecomersDatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ecomersDatabase.entity.Customer;

public interface CustomerDao  extends JpaRepository<Customer, Long>{

}