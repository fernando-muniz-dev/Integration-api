package integrator.product.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductStatusChangerDTO {
    @NotNull(message = "productSku é um campo obrigatório")
    @JsonProperty("productSku")
    private String productSku;

    @NotNull(message = "productStatus é um campo obrigatório")
    @JsonProperty("productStatus")
    @ValidEnum(enumClass = ProductStatus.class, ignoreCase = true)
    private ProductStatus productStatus;
}
