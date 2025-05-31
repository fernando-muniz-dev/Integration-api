package integrator.product.domain.model.enums;

import lombok.Getter;

@Getter
public enum ClientStatus{
    ACTIVE("Ativo"),
    SUSPENDED_BY_NO_PAYMENT("Suspenso por falta de pagamento"),
    SUSPENDED_BY_OWNER("Suspenso pelo próprio cliente"),
    CANCELLED_BY_NO_PAYMENT("Cancelado por falta de pagamento"),
    CANCELLED_BY_OWNER("Cancelado pelo próprio cliente"),
    REACTIVATE_BY_COMPANY("Reativado pela empresa"),
    ACTIVATED_BLACKLISTED("Cliente ativado mas em lista negra"),;

    final String statusDescription;

    public boolean canBeReactivated() {
        return this != CANCELLED_BY_NO_PAYMENT && this != CANCELLED_BY_OWNER;
    }

    public boolean canBeCancelled() {
        return this != CANCELLED_BY_NO_PAYMENT && this != CANCELLED_BY_OWNER;
    }

    ClientStatus(String statusDescription){
        this.statusDescription =  statusDescription;
    }

}