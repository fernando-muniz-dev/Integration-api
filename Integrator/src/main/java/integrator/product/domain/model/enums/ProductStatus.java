package integrator.product.domain.model.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    AVAILABLE("Disponivel"),
    UNAVAILABLE_BY_COMPANY("Indisponivel pela empresa"),
    UNAVAILABLE_BY_PARTNER("Indisponivel pelo parceiro"),
    DISCONTINUED_BY_COMPANY("Descontinuado pela empresa"),
    DISCONTINUED_BY_PARTNER("Descontinuado pelo parceiro"),
    ;

    public boolean canBeCancelled() {
        return this == DISCONTINUED_BY_COMPANY || this == DISCONTINUED_BY_PARTNER;
    }

    public boolean isProductSuspended(){
        return this == UNAVAILABLE_BY_COMPANY || this == UNAVAILABLE_BY_PARTNER;
    }

    final String statusDescription;

    ProductStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}