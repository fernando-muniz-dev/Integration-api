package integrator.product.domain.repository;

import integrator.product.domain.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("select c from Client c where c.clientDocument = :clientDocument")
    Optional<Client> getClientByClientDocument(@Param("clientDocument") String clientDocument);
}
