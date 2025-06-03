package integrator.product.controller.response;

import integrator.product.controller.validator.utils.MultiStatusResult;
import lombok.Data;

@Data
public class ComboProductCreatedAndAttached implements MultiStatusResult {

        private boolean success;
        private String message;

        @Override
        public boolean hasError() {
            return !success;
        }
}
