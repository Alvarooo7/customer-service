package pe.intercorp.indigital.services.customerservice.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerReport {

    private String period;
    private Integer value;

}
