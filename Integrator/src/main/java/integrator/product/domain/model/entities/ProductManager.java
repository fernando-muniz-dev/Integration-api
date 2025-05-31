package integrator.product.domain.model.entities;

import integrator.product.domain.model.enums.ProductManagerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_manager_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "combo_product_id")
    private ComboProduct comboProduct;

    @Enumerated(EnumType.ORDINAL)
    private ProductManagerStatus productManagerStatus;

    private LocalDateTime productActivateDate;

    private String clientActivatedEmail;
}