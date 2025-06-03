package integrator.product.domain.model.enums;

import lombok.Getter;

@Getter
public enum ComboProductAttachStatus {
    LINKED("Ativado"),
    UNLINKED("Inativado"),
    UNIQUE_PRODUCT("Produto único"),;

    final String statusDescription;

    ComboProductAttachStatus(String statusDescription){
        this.statusDescription = statusDescription;
    }
}