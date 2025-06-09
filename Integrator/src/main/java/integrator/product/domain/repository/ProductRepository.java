package integrator.product.domain.repository;

import integrator.product.domain.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("Select p from Product p where p.productSku = :sku")
    Optional<Product> getProductBySKU(String sku);

    @Query("Select p from Product p where p.productStatus = 0")
    List<Product> getAllActiveProducts();
}
