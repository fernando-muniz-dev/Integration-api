package integrator.product.controller.response;

import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComboProductAttachResponse {
    private boolean success;
    private String message;
}
