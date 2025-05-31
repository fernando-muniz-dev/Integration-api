package integrator.product.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.domain.model.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clients_id")
    private Long id;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientEmail")
    private String clientEmail;

    @JsonProperty("clientDocument")
    private String clientDocument;

    @JsonProperty("clientAddress")
    private String clientAddress;

    @JsonProperty("clientCep")
    private String clientCep;

    @JsonProperty("clientStatus")
    @Enumerated(EnumType.ORDINAL)
    private ClientStatus clientStatus;
}