package ee.urbanzen.backoffice.repository;

import ee.urbanzen.backoffice.domain.Studio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Studio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

}
