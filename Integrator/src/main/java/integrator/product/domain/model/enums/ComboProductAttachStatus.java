package integrator.product.domain.model.enums;

import lombok.Getter;

@Getter
public enum ComboProductAttachStatus {
    LINKED("Contrato ativado"),
    UNLINKED("Contrato inativado"),
    UNIQUE_PRODUCT("Produto único"),;

    final String statusDescription;

    ComboProductAttachStatus(String statusDescription){
        this.statusDescription = statusDescription;
    }
}