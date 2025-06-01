package integrator.product.controller.response;

import integrator.product.controller.validator.utils.MultiStatusResult;

public class ComboProductCreatedAndAttached implements MultiStatusResult {

        private boolean success;
        private String message;

        // construtores/getters/setters

        @Override
        public boolean hasError() {
            return !success;
        }
}
