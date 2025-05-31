package integrator.product.domain.model.entities;

import integrator.product.domain.model.enums.ComboProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "combo_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combo_product_id")
    private Long id;

    private String comboName;
    private String comboDescription;
    private BigDecimal comboProductFinalPrice;

    @Enumerated(EnumType.ORDINAL)
    private ComboProductStatus comboProductStatus;
}