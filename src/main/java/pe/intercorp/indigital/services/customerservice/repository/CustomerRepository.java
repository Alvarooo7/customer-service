package pe.intercorp.indigital.services.customerservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pe.intercorp.indigital.services.customerservice.model.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

}
