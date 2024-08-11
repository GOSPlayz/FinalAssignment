package ecomersDatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ecomersDatabase.entity.Orders;

public interface OrdersDao extends JpaRepository<Orders, Long>{

}