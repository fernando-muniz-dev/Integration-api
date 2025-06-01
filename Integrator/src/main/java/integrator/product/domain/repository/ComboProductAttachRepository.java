package integrator.product.domain.repository;

import integrator.product.domain.model.entities.ComboProductAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboProductAttachRepository extends JpaRepository<ComboProductAttach, Long> {
}
