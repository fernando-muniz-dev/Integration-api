package integrator.product.domain.repository;

import integrator.product.domain.model.entities.ComboProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComboProductRepository extends JpaRepository<ComboProduct, Long> {

    @Query("select cp from ComboProduct cp where cp.id = :id")
    Optional<ComboProduct> getComboProductById(Long id);

    @Query("Select cp from ComboProduct cp")
    List<ComboProduct> getAllCombos();
}
