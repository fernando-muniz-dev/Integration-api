package integrator.product.domain.model.entities;

import integrator.product.controller.validator.constraints.ValidEnum;
import integrator.product.domain.model.enums.ComboProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @ValidEnum(enumClass = ComboProductStatus.class, ignoreCase = true)
    private ComboProductStatus comboProductStatus;
}