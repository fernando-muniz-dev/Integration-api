package integrator.product.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.enums.PartnerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerDTO {

    private Long id;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("partnerDescription")
    private String partnerDescription;

    @CpfCnpj
    @JsonProperty("partnerDocument")
    private String partnerDocument;

    @JsonProperty("partnerStatus")
    @ValidEnum(enumClass = PartnerStatus.class, ignoreCase = true)
    @Enumerated(EnumType.ORDINAL)
    private PartnerStatus partnerStatus;
}
