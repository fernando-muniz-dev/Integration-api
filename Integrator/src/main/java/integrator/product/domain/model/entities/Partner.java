package integrator.product.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.domain.model.enums.PartnerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partners_id")
    private Long id;

    private String partnerName;
    private String partnerDescription;

    @CpfCnpj
    @JsonProperty("partnerDocument")
    private String partnerDocument;

    @Enumerated(EnumType.ORDINAL)
    private PartnerStatus partnerStatus;
}