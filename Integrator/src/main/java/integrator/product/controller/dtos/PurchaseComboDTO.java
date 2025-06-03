package integrator.product.controller.dtos;

import integrator.product.controller.validator.constraints.CpfCnpj;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseComboDTO {
    @NotNull(message = "comboId é obrigatório")
    private List<Long> comboId;

    @CpfCnpj @NotNull(message = "Documento é obrigatorio")
    private String document;
}
