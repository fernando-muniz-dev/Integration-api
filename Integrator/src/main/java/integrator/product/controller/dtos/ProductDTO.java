package integrator.product.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.entities.Partner;
import integrator.product.domain.model.enums.ClientStatus;
import integrator.product.domain.model.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    @NotNull(message = "productSku é um campo obrigatório")
    @JsonProperty("productSku")
    private String productSku;

    @JsonProperty("productPartnerDocument")
    private String productPartnerDocument;

    @JsonProperty("productPrice")
    private BigDecimal productPrice;

    @JsonProperty("productStatus")
    @ValidEnum(enumClass = ProductStatus.class, ignoreCase = true)
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus productStatus;
}
