package ecomersDatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ecomersDatabase.entity.Ecomers;

public interface EcomersDao extends JpaRepository<Ecomers, Long>{
	
}
