package integrator.product.domain.model.enums;

public enum ProductManagerStatus {

    PURCHASED("Comprado"),
    PENDING("Pendente"),
    ACTIVATED("Ativado"),
    DEACTIVATED_BY_NO_PAYMENT("Desativado por falta de pagamento"),
    DEACTIVATED_BY_OWNER("Desativado pelo proprio cliente"),
    CANCELLED_BY_NO_PAYMENT("Cancelado por falta de pagamento"),
    CANCELLED_BY_OWNER("Cancelado pelo proprio cliente"),
    TESTING("Testando produto"),
    TESTING_DEVELOPMENT("Testando para desenvolvimento")
    ;

    final String statusDescription;

    ProductManagerStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}