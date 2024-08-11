package ecomersDatabase.controller.model;

import java.util.HashSet;
import java.util.Set;

import ecomersDatabase.entity.Customer;
import ecomersDatabase.entity.Ecomers;
import ecomersDatabase.entity.Orders;
import ecomersDatabase.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
public class EcomersData {
	private Long ecomersId;
	private String ecomersName;
	private String ecomersAddress;
	private String ecomersCity;
	private String ecomersState;
	private String ecomersZip;
	private String ecomersPhone;
	
	Set<EcomersCustomer> customers = new HashSet<>();
	
	public EcomersData(Ecomers ecomers) {
		ecomersId = ecomers.getEcomersId();
		ecomersName = ecomers.getEcomersName();
		ecomersAddress = ecomers.getEcomersAddress();
		ecomersCity = ecomers.getEcomersCity();
		ecomersState = ecomers.getEcomersState();
		ecomersZip = ecomers.getEcomersZip();
		ecomersPhone = ecomers.getEcomersPhone();

		for(Customer customer : ecomers.getCustomers()) {
			customers.add(new EcomersCustomer(customer));
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class EcomersCustomer{
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		private String customerPhoneNumber;
		private String customerAddress;
		private String customerCity;
		private String customerState;
		private String customerCountry;
		private String customerPostalCode;
		
		private Set<EcomersOrders> orderss = new HashSet<>();
		
		public EcomersCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
			customerPhoneNumber = customer.getCustomerPhoneNumber();
			customerAddress = customer.getCustomerAddress();
			customerCity = customer.getCustomerCity();
			customerState = customer.getCustomerState();
			customerCountry = customer.getCustomerCountry();
			customerPostalCode = customer.getCustomerPostalCode();
		
		for(Orders orders : customer.getOrderss()) {
			orderss.add(new EcomersOrders(orders));
			}
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class EcomersOrders{
		private Long ordersId;
		private String ordersNumber;
		private String ordersDate;
		
		private Set<EcomersProduct> products = new HashSet<>();
		
		public EcomersOrders(Orders orders){
			ordersId = orders.getOrdersId();
			ordersNumber = orders.getOrdersNumber();
			ordersDate = orders.getOrdersDate();
			
			for(Product product : orders.getProducts()) {
				products.add(new EcomersProduct(product));		
		}		
	}
}
	
	@NoArgsConstructor
	@Data
	public static class EcomersProduct{
		private Long productId;
		private String productName;
		private String productPrice;
		private String productDescription;
		private String productDimensions;
		private String productMaterialOne;
		private String productMaterialTwo;
		
		public EcomersProduct(Product product){
			productId = product.getProductId();
			productName = product.getProductName();
			productPrice = product.getProductPrice();
			productDescription = product.getProductDescription();
			productDimensions = product.getProductDimensions();
			productMaterialOne = product.getProductMaterialOne();
			productMaterialTwo = product.getProductMaterialTwo();
		}
	}
}