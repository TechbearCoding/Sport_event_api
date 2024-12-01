package enlabs.web.catalog.repository;

import enlabs.web.catalog.model.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SportEventRepository extends JpaRepository<SportEvent, Integer>, JpaSpecificationExecutor<SportEvent> {
    Optional<SportEvent> findById(int id);

    @Query("SELECT e FROM SportEvent e " +
            "WHERE (:sport IS NULL OR LOWER(e.sport) = LOWER(:sport)) " +
            "AND (:status IS NULL OR LOWER(e.status) = LOWER(:status))")
    List<SportEvent> findFilteredEvents(@Param("sport") String sport, @Param("status") String status);

}