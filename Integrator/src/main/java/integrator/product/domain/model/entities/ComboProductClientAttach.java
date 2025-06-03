package integrator.product.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "combo_product_client_attach")
public class ComboProductClientAttach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combo_product_client_attach_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "combo_product_id", nullable = false)
    private ComboProduct comboProduct;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_modification")
    private LocalDateTime lastModification;
}