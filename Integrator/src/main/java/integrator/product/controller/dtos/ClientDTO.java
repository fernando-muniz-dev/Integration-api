package integrator.product.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.enums.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {

    private Long id;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientEmail")
    private String clientEmail;

    @CpfCnpj
    @JsonProperty("clientDocument")
    private String clientDocument;

    @JsonProperty("clientAddress")
    private String clientAddress;

    @JsonProperty("clientCep")
    private String clientCep;

    @ValidEnum(enumClass = ClientStatus.class, ignoreCase = true)
    @JsonProperty("clientStatus")
    private ClientStatus clientStatus;
}