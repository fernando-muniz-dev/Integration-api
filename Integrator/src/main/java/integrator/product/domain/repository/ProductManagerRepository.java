package integrator.product.domain.repository;

import integrator.product.domain.model.entities.ProductManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductManagerRepository extends JpaRepository<ProductManager, Long> {
}
