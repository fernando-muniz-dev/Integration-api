package integrator.product.domain.repository;

import integrator.product.domain.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("Select p from Product p where p.productSku = :sku")
    Optional<Product> getProductBySKU(String sku);
}
