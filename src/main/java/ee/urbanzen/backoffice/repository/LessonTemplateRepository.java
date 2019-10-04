package ee.urbanzen.backoffice.repository;

import ee.urbanzen.backoffice.domain.LessonTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LessonTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonTemplateRepository extends JpaRepository<LessonTemplate, Long> {

}
