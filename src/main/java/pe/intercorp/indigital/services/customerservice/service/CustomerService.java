package pe.intercorp.indigital.services.customerservice.service;

import org.springframework.data.jpa.domain.Specification;
import pe.intercorp.indigital.services.customerservice.model.entity.Customer;

import java.util.List;
import java.util.Map;


public interface CustomerService {

    Customer create(Customer customer);

    List<Customer> get(Specification<Customer> spec);

    Map<String, String> getReport(String reportType, String periodType, Long limit, String sort);
}
