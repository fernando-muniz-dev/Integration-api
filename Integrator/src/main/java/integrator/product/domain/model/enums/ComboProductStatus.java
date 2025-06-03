package integrator.product.domain.model.enums;

import integrator.product.controller.dtos.ComboProductStatusChangerDTO;

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

    public boolean checkingStatusToCancelOrReactive(){
        return this == CANCELLED_BY_COMPANY || this == CANCELLED_BY_PARTNER;
    }

    public boolean verifyStatusToReactivate(ComboProductStatus comboProductStatus){
        return comboProductStatus.checkingStatusToCancelOrReactive() &&
                !comboProductStatus.equals(ComboProductStatus.ACTIVE);
    }



    ComboProductStatus (String statusDescription){
        this.statusDescription = statusDescription;
    }
}