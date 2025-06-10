package integrator.product.controller.response;

import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.ComboProductAttach;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboProductCreated {
    ComboProduct comboProduct;
    List<ComboProductAttachResponse> comboProductAttachList;
}
