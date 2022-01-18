package pe.intercorp.indigital.services.customerservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 60)
    @NotBlank(message = "El campo name no puede estar en blanco")
    private String name;

    @Column(nullable = false, length = 60)
    @NotBlank(message = "El campo lastName no puede estar en blanco")
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(min = 8, max = 8, message = "El campo identificationNumber debe ser de 8 digitos")
    @NotBlank(message = "El campo identificationNumber no puede estar en blanco")
    private String identificationNumber;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "El campo email no puede estar en blanco")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "El campo birthDate no puede ser nulo")
    private LocalDate birthDate;

    @CreatedDate
    private LocalDate createDate;

}
