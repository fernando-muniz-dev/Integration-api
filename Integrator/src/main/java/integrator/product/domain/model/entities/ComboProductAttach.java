package integrator.product.domain.model.entities;

import integrator.product.domain.model.enums.ComboProductAttachStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "combo_product_attach")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboProductAttach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combo_product_attach_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "combo_product_id")
    private ComboProduct comboProduct;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.ORDINAL)
    private ComboProductAttachStatus comboProductAttachStatus;
}