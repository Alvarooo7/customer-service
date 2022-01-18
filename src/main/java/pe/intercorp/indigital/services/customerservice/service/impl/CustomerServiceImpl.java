package pe.intercorp.indigital.services.customerservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pe.intercorp.indigital.services.customerservice.model.entity.Customer;
import pe.intercorp.indigital.services.customerservice.repository.CustomerRepository;
import pe.intercorp.indigital.services.customerservice.service.CustomerService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.StreamSupport.stream;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static pe.intercorp.indigital.services.customerservice.utils.Constants.MONTH_CODE;
import static pe.intercorp.indigital.services.customerservice.utils.Constants.QUANTITY_CUSTOMERS_CODE;
import static pe.intercorp.indigital.services.customerservice.utils.Constants.SORT_DESC;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final EntityManager em;

    @Override
    public Customer create(Customer customer) {
        if (existsCustomerByIdentificationOrEmail(customer.getIdentificationNumber(), customer.getEmail()))
            throw new EntityExistsException("El cliente ya ha sido registrado");
        return repository.save(customer);
    }

    Boolean existsCustomerByIdentificationOrEmail(String identificationNumber, String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> customer = cq.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.or(
                cb.equal(customer.get("identificationNumber"), identificationNumber),
                cb.equal(customer.get("email"), email)));

        cq.where(predicates.toArray(new Predicate[0]));

        return !em.createQuery(cq).getResultList().isEmpty();
    }

    @Override
    public List<Customer> get(Specification<Customer> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Map<String, String> getReport(String reportType, String periodType, Long limit, String sort) {
        Spliterator<Customer> customerIterator = repository.findAll().spliterator();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        long totalCustomers = stream(customerIterator, false).count();

        Stream<LocalDate> customers = stream(customerIterator, false)
                .map(Customer::getBirthDate);

        Map<String, Long> reportByPeriod = customers
                .collect(groupingBy((x) -> MONTH_CODE.equalsIgnoreCase(periodType)
                        ? Month.from(x).name()
                        : Year.from(x).toString(), counting()));

        if (isNotBlank(sort)) {
            reportByPeriod = sortReport(sort, reportByPeriod);
        }

        return reportByPeriod.entrySet().stream()
                .limit(limit)
                .collect(getMapCollector(reportType, numberFormat, (double) totalCustomers));
    }

    private LinkedHashMap<String, Long> sortReport(String sort, Map<String, Long> reportByPeriod) {
        return reportByPeriod.entrySet().stream()
                .sorted(SORT_DESC.equalsIgnoreCase(sort)
                        ? reverseOrder(comparingByValue())
                        : comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
    }

    private Collector<Map.Entry<String, Long>, ?, LinkedHashMap<String, String>> getMapCollector(String reportType,
                                                                                                 DecimalFormat numberFormat,
                                                                                                 double totalCustomers) {
        return toMap(
                Map.Entry::getKey,
                s -> QUANTITY_CUSTOMERS_CODE.equals(reportType)
                        ? s.getValue().toString()
                        : numberFormat.format(s.getValue() / totalCustomers),
                (e1, e2) -> e2,
                LinkedHashMap::new);
    }

}
