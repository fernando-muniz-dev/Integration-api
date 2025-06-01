package integrator.product.domain.repository;

import integrator.product.domain.model.entities.ComboProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboProductRepository extends JpaRepository<ComboProduct, Long> {

    @Query("Select cp from ComboProduct")
    public List<ComboProduct> getAllCombos();
}
