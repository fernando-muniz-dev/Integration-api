package integrator.product.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.enums.ClientStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReactivateDTO {

    @JsonProperty("document")
    @CpfCnpj
    @NotBlank(message = "Documento é um campo obrigatório")
    private final String document;

    @NotNull(message = "clientStatus é um campo obrigatório")
    @ValidEnum(enumClass = ClientStatus.class, ignoreCase = true)
    @JsonProperty("clientStatus")
    private final ClientStatus clientStatus;
}
