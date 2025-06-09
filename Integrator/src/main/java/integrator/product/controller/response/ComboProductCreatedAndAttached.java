package integrator.product.controller.response;

import integrator.product.domain.model.exceptions.MultiStatusResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboProductCreatedAndAttached implements MultiStatusResult {

        private boolean success;
        private String message;

        @Override
        public boolean hasError() {
            return !success;
        }
}
