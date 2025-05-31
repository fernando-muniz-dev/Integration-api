package integrator.product.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_manager_cancelled")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductManagerCancelled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_manager_cancelled_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "combo_product_id")
    private ComboProduct comboProduct;

    private LocalDateTime productActivateDate;
    private LocalDateTime productCancelledDate;

    private String clientActivatedEmail;
}