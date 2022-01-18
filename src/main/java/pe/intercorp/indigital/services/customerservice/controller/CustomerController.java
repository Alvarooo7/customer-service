package pe.intercorp.indigital.services.customerservice.controller;

import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.intercorp.indigital.services.customerservice.model.api.CustomerReportRequest;
import pe.intercorp.indigital.services.customerservice.model.entity.Customer;
import pe.intercorp.indigital.services.customerservice.service.CustomerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Customer createCustomer(@RequestBody Customer customer) {
        return service.create(customer);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> get(
            @And({
                    @Spec(path = "identificationNumber", params = "identificationNumber", spec = Equal.class),
                    @Spec(path = "email", params = "email", spec = Equal.class),
            }) Specification<Customer> spec) {
        return service.get(spec);
    }

    @GetMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> get(@Valid CustomerReportRequest request) {
        return service.getReport(request.getReportType(), request.getPeriodType(), request.getLimit(), request.getSort());
    }

}
