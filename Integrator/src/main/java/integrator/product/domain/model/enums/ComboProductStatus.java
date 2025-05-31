package integrator.product.domain.model.enums;

public enum ComboProductStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    DISCONTINUED_BY_PARTNER("Descontinuado pelo parceiro"),
    DISCONTINUED_BY_COMPANY("Descontinuado pela empresa"),
    CANCELLED_BY_PARTNER("Cancelado pelo parceiro"),
    CANCELLED_BY_COMPANY("Cancelado pela empresa"),
    PROMOTIONS_ONLY("Apenas promoções"),
    UNIQUE_PRODUCT("Produto único"),;

    final String statusDescription;

    ComboProductStatus (String statusDescription){
        this.statusDescription = statusDescription;
    }
}