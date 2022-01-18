package pe.intercorp.indigital.services.customerservice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import pe.intercorp.indigital.services.customerservice.model.entity.Customer;
import pe.intercorp.indigital.services.customerservice.repository.CustomerRepository;
import pe.intercorp.indigital.services.customerservice.service.impl.CustomerServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    private Customer customer;


    @Before
    public void init() {
        customer = new Customer();
        customer.setBirthDate(LocalDate.of(1989, 1, 9));
        customer.setName("Gustavo");
        customer.setLastName("Martinez");
    }

    @Test
    public void getCustomersCorrectTest() {
        List<Customer> customers = Arrays.asList(customer);
        Specification querySpec = mock(Specification.class);

        when(repository.findAll(querySpec)).thenReturn(customers);

        List customersTest = service.get(querySpec);
        assertEquals(customers, customersTest);
    }
}
