package ecomersDatabase.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Ecomers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ecomersId;
	private String ecomersName;
	private String ecomersLinkAddress;
	private String ecomersAddress;
	private String ecomersCity;
	private String ecomersState;
	private String ecomersZip;
	private String ecomersPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "ecomers", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Customer> customers = new HashSet<>();
}
