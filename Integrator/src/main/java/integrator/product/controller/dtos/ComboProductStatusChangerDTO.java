package integrator.product.controller.dtos;

import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.enums.ComboProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboProductStatusChangerDTO {

    @NotNull(message = "comboProductId é obrigatório")
    private Long comboProductId;

    @NotNull(message = "comboProductStatus é obrigatório")
    @ValidEnum(enumClass = ComboProductStatus.class, ignoreCase = true)
    private ComboProductStatus comboProductStatus;
}
