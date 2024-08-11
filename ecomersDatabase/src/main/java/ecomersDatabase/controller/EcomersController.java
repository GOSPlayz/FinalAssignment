package ecomersDatabase.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecomersDatabase.controller.model.EcomersData;
import ecomersDatabase.controller.model.EcomersData.EcomersCustomer;
import ecomersDatabase.controller.model.EcomersData.EcomersOrders;
import ecomersDatabase.controller.model.EcomersData.EcomersProduct;
import ecomersDatabase.service.EcomersService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ecomers_data")
@Slf4j
public class EcomersController {
	
	@Autowired
	private EcomersService ecomersService;
	
// Ecomers CRUD
// ---------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/ecomers")
	public EcomersData insertEcomers(@RequestBody EcomersData ecomersData) {
		log.info("Creating new ecomers {}", ecomersData);
		return ecomersService.saveEcomers(ecomersData);
	}
	
	@PutMapping("/ecomers/{ecomersId}")
	public EcomersData updateEcomers(@PathVariable Long ecomersId, @RequestBody EcomersData ecomersData) {
		ecomersData.setEcomersId(ecomersId);
		log.info("Updating ecomers {}", ecomersData);
		return ecomersService.saveEcomers(ecomersData);
	}

	@GetMapping("/{ecomersId}")
	public EcomersData listEcomersById(@PathVariable Long ecomersId) {
		return ecomersService.retrieveEcomersById(ecomersId);
	}
	
	
// Customer CRUD
// ---------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/{ecomersId}/customer") 
	@ResponseStatus(code = HttpStatus.CREATED)
	public EcomersCustomer insertCustomer(@PathVariable Long ecomersId, @RequestBody EcomersCustomer ecomersCustomer) {
		log.info("Creating customer {}", ecomersCustomer);
		return ecomersService.saveCustomer(ecomersId, ecomersCustomer);
	}
	
	@GetMapping("/customer")
	public List<EcomersCustomer> listCustomer() {
		return ecomersService.retrieveAllCustomer();
	}
	
	@PutMapping("/{ecomersId}/customer")
	public EcomersCustomer updateCustomer(@PathVariable Long ecomersId, @RequestBody EcomersCustomer ecomersCustomer) {
		ecomersCustomer.setCustomerId(ecomersId);
		log.info("Updating customer {}", ecomersCustomer);
		return ecomersService.saveCustomer(ecomersCustomer);
	}
	
// Orders CRUD
// ---------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/{customerId}/orders") 
	@ResponseStatus(code = HttpStatus.CREATED)
	public EcomersOrders insertOrder(@PathVariable Long customerId, @RequestBody EcomersOrders ecomersOrders) {
		log.info("Creating Orders {}", ecomersOrders);
		return ecomersService.saveOrders(customerId, ecomersOrders);
	}
	
	@GetMapping("/orders/{odersId}")
	public List<EcomersOrders> listOrders() {
		return ecomersService.retrieveAllOrderss();
	}
	
	@PutMapping("/{ordersId}/orders")
	public EcomersOrders updateOrders(@PathVariable Long customerId, @RequestBody EcomersOrders ecomersOrders) {
		ecomersOrders.setOrdersId(customerId);
		log.info("Updating orders {}", ecomersOrders);
		return ecomersService.saveOrders(customerId, ecomersOrders);
	}
// Product CRUD
// ---------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/{ordersId}/product") 
	@ResponseStatus(code = HttpStatus.CREATED)
	public EcomersProduct insertProduct(@PathVariable Long ordersId, @RequestBody EcomersProduct ecomersProduct) {
		log.info("Adding new product {}", ecomersProduct);
		return ecomersService.saveProduct(ordersId, ecomersProduct);
	}
	
	@GetMapping("/ecomers")
	public List<EcomersProduct> listProduct() {
		return ecomersService.retrieveAllProducts();
	}
	
	@PutMapping("/{ecomersId}/product")
	public EcomersProduct updateProducts(@PathVariable Long ordersId, @RequestBody EcomersProduct ecomersProduct) {
		ecomersProduct.setProductId(ordersId);
		log.info("Updating ecomers {}", ecomersProduct);
		return ecomersService.saveProduct(ordersId, ecomersProduct);
	}
	
	@DeleteMapping("/products/{productId}")
	public Map<String, String> deleteProductById(@PathVariable Long productId) {
		log.info("Deleting product {}", productId);
		ecomersService.deleteProductById(productId);
		return Map.of("Message", "Product with this ID:" + productId + " was deleted");
	}
}
