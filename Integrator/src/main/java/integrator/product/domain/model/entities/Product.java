package integrator.product.domain.model.entities;

import integrator.product.domain.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productSku;

    @ManyToOne
    @JoinColumn(name = "product_partner")
    private Partner productPartner;

    private BigDecimal productPrice;

    @Enumerated(EnumType.ORDINAL)
    private ProductStatus productStatus;
}