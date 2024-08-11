package ecomersDatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ecomersDatabase.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long>{
	
}
