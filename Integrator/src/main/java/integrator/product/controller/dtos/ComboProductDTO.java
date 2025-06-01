package integrator.product.controller.dtos;

import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.entities.ComboProductAttach;
import integrator.product.domain.model.enums.ComboProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboProductDTO {

    private Long id;

    private String comboName;
    private String comboDescription;
    private BigDecimal comboProductFinalPrice;

    @Enumerated(EnumType.ORDINAL)
    @ValidEnum(enumClass = ComboProductStatus.class, ignoreCase = true)
    private ComboProductStatus comboProductStatus;

    private List<ComboProductAttach> comboProductAttaches;
}
