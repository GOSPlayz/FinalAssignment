package ecomersDatabase.service;



import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ecomersDatabase.controller.model.EcomersData;
import ecomersDatabase.controller.model.EcomersData.EcomersCustomer;
import ecomersDatabase.controller.model.EcomersData.EcomersOrders;
import ecomersDatabase.controller.model.EcomersData.EcomersProduct;
import ecomersDatabase.dao.CustomerDao;
import ecomersDatabase.dao.EcomersDao;
import ecomersDatabase.dao.OrdersDao;
import ecomersDatabase.dao.ProductDao;
import ecomersDatabase.entity.Customer;
import ecomersDatabase.entity.Ecomers;
import ecomersDatabase.entity.Orders;
import ecomersDatabase.entity.Product;

@Service
public class EcomersService {
	@Autowired
	private EcomersDao ecomersDao;
	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ProductDao productDao;
	
	@Transactional(readOnly = false)
	public EcomersData saveEcomers(EcomersData ecomersData) {
		Long ecomersId = ecomersData.getEcomersId();
		Ecomers ecomers = findOrCreateEcomers(ecomersId);
		
		copyEcomersFields(ecomers, ecomersData);
		return new EcomersData(ecomersDao.save(ecomers));
	}

	private void copyEcomersFields(Ecomers ecomers, EcomersData ecomersData) {
		ecomers.setEcomersId(ecomersData.getEcomersId());
		ecomers.setEcomersName(ecomersData.getEcomersName());
		ecomers.setEcomersAddress(ecomersData.getEcomersAddress());
		ecomers.setEcomersCity(ecomersData.getEcomersCity());
		ecomers.setEcomersState(ecomersData.getEcomersState());
		ecomers.setEcomersZip(ecomersData.getEcomersZip());
		ecomers.setEcomersPhone(ecomersData.getEcomersPhone());
	}

	private Ecomers findOrCreateEcomers(Long ecomersId) {
		Ecomers ecomers;
		
		if(Objects.isNull(ecomersId)) {
			ecomers = new Ecomers();
		}
		else {
			ecomers = findEcomersById(ecomersId);
		}
		return ecomers;
	}

	private Ecomers findEcomersById(Long ecomersId) {
		return ecomersDao.findById(ecomersId)
				.orElseThrow(() -> new NoSuchElementException("Ecomers with ID=" + ecomersId + " was not found."));
	}
	
	@Transactional(readOnly = true)
	public EcomersData retrieveEcomersById(Long ecomersId) {
		return new EcomersData(findEcomersById(ecomersId));
	}

	public void deleteEcomersById(Long ecomersId) {
		Ecomers ecomers = findEcomersById(ecomersId);
		ecomersDao.delete(ecomers);
	}
	
	//employee info
	//------------------------------------------------------------------------------------------------------------
	
	@Transactional(readOnly = false)
	public EcomersOrders saveOrders(Long customerId, EcomersOrders ecomersOrders) {
		Customer customer = findCustomerById(customerId);
		Long orderId = ecomersOrders.getOrdersId();
		Orders orders = findOrCreateOrders(customerId, orderId);
		
		copyOrdersFields(orders, ecomersOrders);
		
		orders.setCustomer(customer);
		customer.getOrderss().add(orders);
		
		Orders dbOrders = ordersDao.save(orders);
		return new EcomersOrders(dbOrders);
	}
	
	private Customer findCustomerById(Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
		return customer;
	}

	private Orders findOrCreateOrders(Long ecomersId, Long ordersId) {
		Orders orders;
		
		if(Objects.isNull(ordersId)) {
			orders = new Orders();
		} else {
			orders = findOrdersById(ecomersId, ordersId);
		}
		return orders;
	}
	
	private Orders findOrdersById(Long customerId, Long ordersId) {
		Orders orders = ordersDao.findById(ordersId)
				.orElseThrow(() -> new NoSuchElementException("Ordesr with ID=" + ordersId + " was not found."));
				if(orders.getCustomer().getCustomerId() != customerId) {
					throw new IllegalArgumentException("Orders with this ID:" + ordersId + " isn't a current orders in our database");
				}
				return orders;
	}
	
	private void copyOrdersFields(Orders orders, EcomersOrders ecomersOrders) {
		orders.setOrdersId(ecomersOrders.getOrdersId());
		orders.setOrdersNumber(ecomersOrders.getOrdersNumber());
		orders.setOrdersDate(ecomersOrders.getOrdersDate());
	}
	
	
	//customer info
	//------------------------------------------------------------------------------------------------------------
	
	@Transactional(readOnly = false)
	public EcomersCustomer saveCustomer(Long ecomersId, EcomersCustomer ecomersCustomer) {
		Ecomers ecomers = findEcomersById(ecomersId);
		Long customerId = ecomersCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(ecomersId, customerId);
		
		copyCustomerFields(customer, ecomersCustomer);
		
		customer.setEcomers(ecomers);
		ecomers.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		return new EcomersCustomer(dbCustomer);
	}
	
	private Customer findOrCreateCustomer(Long ecomersId, Long customerId) {
		Customer customer;
		
		if(Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(ecomersId, customerId);
		}
		return customer;
	}
	
	private Customer findCustomerById(Long ecomersId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
				if(customer.getEcomers().getEcomersId() != ecomersId) {
					throw new IllegalArgumentException("Customer with this ID:" + customerId + " doesn't have an account with out company");
				}
				return customer;
	}
	
	private void copyCustomerFields(Customer customer, EcomersCustomer ecomersCustomer) {
		customer.setCustomerId(ecomersCustomer.getCustomerId());
		customer.setCustomerFirstName(ecomersCustomer.getCustomerFirstName());
		customer.setCustomerLastName(ecomersCustomer.getCustomerLastName());
		customer.setCustomerEmail(ecomersCustomer.getCustomerEmail());
		customer.setCustomerPhoneNumber(ecomersCustomer.getCustomerPhoneNumber());
		customer.setCustomerAddress(ecomersCustomer.getCustomerAddress());
		customer.setCustomerCity(ecomersCustomer.getCustomerCity());
		customer.setCustomerState(ecomersCustomer.getCustomerState());
		customer.setCustomerCountry(ecomersCustomer.getCustomerCountry());
		customer.setCustomerPostalCode(ecomersCustomer.getCustomerPostalCode());
	}
	
	//orders info
	//------------------------------------------------------------------------------------------------------------
	
	@Transactional(readOnly = false)
	public EcomersProduct saveProduct(Long ordersId, EcomersProduct ecomersProduct) {
		Orders orders = findOrdersById(ordersId);
		Long productId = ecomersProduct.getProductId();
		Product product = findOrCreateProduct(ordersId, productId);

		copyProductFields(product, ecomersProduct);
		product.getOrderss().add(orders);
		orders.getProducts().add(product);
		
		Product dbProduct = productDao.save(product);
		return new EcomersProduct(dbProduct);
	}

	private Orders findOrdersById(Long ordersId) {
		Orders orders = ordersDao.findById(ordersId)
				.orElseThrow(() -> new NoSuchElementException("Orders with ID=" + ordersId + " was not found."));
		return orders;
	}

	private Product findOrCreateProduct(Long ordersId, Long productId) {
		Product product;
		
		if(Objects.isNull(productId)) {
			product = new Product();
		} else {
			product = findProductById(ordersId, productId);
		}
		return product;
	}
	
	private Product findProductById(Long ordersId, Long productId) {
		Product product =  productDao.findById(productId).orElseThrow(() -> new NoSuchElementException("Product with ID=" + productId + " was not found."));
		
		boolean found = false;
		
		for(Orders orders: product.getOrderss()) {
			if(orders.getOrdersId().equals(ordersId)) {
				found = true;
				break;
			}
		}
		if(!found) {
			throw new IllegalArgumentException("No product with this " + productId + " has been ordered");
		}
		return product;
		
	}
	
	private void copyProductFields(Product product, EcomersProduct ecomersProduct) {
		product.setProductId(ecomersProduct.getProductId());
		product.setProductName(ecomersProduct.getProductName());
		product.setProductPrice(ecomersProduct.getProductPrice());
		product.setProductDescription(ecomersProduct.getProductDescription());
		product.setProductDimensions(ecomersProduct.getProductDimensions());
		product.setProductMaterialOne(ecomersProduct.getProductMaterialOne());
		product.setProductMaterialTwo(ecomersProduct.getProductMaterialTwo());
	}

	public void deleteProductById(Long productId) {
		Product product = findProductById(productId);
		
		productDao.delete(product);
	}

	private Product findProductById(Long productId) {
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product with ID=" + productId + " was not found."));
		return product;
	}
	
	@Transactional(readOnly = true)
	public List<EcomersOrders> retrieveAllOrderss() {
		List<Orders> orderss = ordersDao.findAll();
		List<EcomersOrders> result = new LinkedList<>();
		
		for(Orders orders : orderss) {
			EcomersOrders psd = new EcomersOrders(orders);
			
			result.add(psd);
		}
		return result;
	}
	@Transactional(readOnly = true)
	public List<EcomersProduct> retrieveAllProducts() {
		List<Product> products = productDao.findAll();
		List<EcomersProduct> result = new LinkedList<>();
		
		for(Product product : products) {
			EcomersProduct pd = new EcomersProduct(product);
			
			result.add(pd);
		}
		return result;
	}
	
	public EcomersCustomer saveCustomer(EcomersCustomer ecomersCustomer) {
		Long customerId = ecomersCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId);
		
		copyCustomerFields(customer, ecomersCustomer);
		return new EcomersCustomer(customerDao.save(customer));
	}

	private Customer findOrCreateCustomer(Long customerId) {
		Customer customer;
		
		if(Objects.isNull(customerId)) {
			customer = new Customer();
		}
		else {
			customer = findCustomerById(customerId);
		}
		return customer;
	}

	@Transactional(readOnly = true)
	public List<EcomersCustomer> retrieveAllCustomer() {
		List<Customer> customers = customerDao.findAll();
		List<EcomersCustomer> result = new LinkedList<>();
		
		for(Customer customer : customers) {
			EcomersCustomer pd = new EcomersCustomer(customer);
			
			result.add(pd);
		}
		return result;
	}

/*	@Transactional(readOnly = true)
	public List<EcomersData> retrieveAllEcomers() {
		List<Ecomers> ecomerss = ecomersDao.findAll();
		List<EcomersData> result = new LinkedList<>();
		
		for(Ecomers ecomers : ecomerss) {
			EcomersProduct pd = new EcomersData(product);
			
			result.add(pd);
		}
		return result;
	}
*/
}