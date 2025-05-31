package integrator.product.domain.repository;

import integrator.product.domain.model.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long> {

    @Query("select p from Partner p where p.partnerDocument = :partnerDocument")
    Optional<Partner> getPartnerByPartnerDocument(@Param("partnerDocument") String partnerDocument);
}
