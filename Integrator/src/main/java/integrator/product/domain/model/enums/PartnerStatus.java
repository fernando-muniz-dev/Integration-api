package integrator.product.domain.model.enums;

public enum PartnerStatus {
    ACTIVE("Ativo"),
    CONTRACT_SUSPEND("Contrato suspenso"),
    CONTRACT_CANCELLED("Contrato cancelado"),
    PARTNER_CHANGED("Mudança de parceiro"),
    NO_OFFERED("Não oferecemos"),;

    final String statusDescription;

    PartnerStatus(String statusDescription){
        this.statusDescription = statusDescription;
    }
}