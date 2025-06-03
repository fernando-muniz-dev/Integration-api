package integrator.product.domain.repository;

import integrator.product.domain.model.entities.ComboProductClientAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboProductClientAttachRepository extends JpaRepository<ComboProductClientAttach, Long> {
}
