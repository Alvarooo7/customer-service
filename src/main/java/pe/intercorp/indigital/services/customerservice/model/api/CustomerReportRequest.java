package pe.intercorp.indigital.services.customerservice.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static pe.intercorp.indigital.services.customerservice.utils.Constants.MONTH_CODE;

@Getter
@Setter
@AllArgsConstructor
public class CustomerReportRequest {

    public CustomerReportRequest() {
        this.limit = 1000L;
    }

    @NotBlank(message = "El campo 'reportType' es obligatorio")
    @Pattern(regexp = "1|2", message = "El campo 'reportType' es incorrecto")
    private String reportType;

    @Pattern(regexp = "M|Y", message = "El campo 'periodType' es incorrecto")
    @Builder.Default
    private String periodType = MONTH_CODE;

    private Long limit;

    @Pattern(regexp = "DESC|ASC|desc|asc", message = "El campo 'sort' es incorrecto")
    private String sort;

}
