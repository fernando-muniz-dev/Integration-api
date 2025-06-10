package integrator.product.domain.repository;

import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.ComboProductAttach;
import integrator.product.domain.model.entities.ComboProductClientAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComboProductClientAttachRepository extends JpaRepository<ComboProductClientAttach, Long> {
    @Query("select cpca from ComboProductClientAttach cpca where cpca.comboProduct = :comboProduct and cpca.client = :client and cpca.status = 0")
    Optional<ComboProductClientAttach> getAttachmentClientAndComboProduct(ComboProduct comboProduct, Client client);
}
