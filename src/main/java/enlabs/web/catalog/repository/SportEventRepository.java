package enlabs.web.catalog.repository;

import enlabs.web.catalog.model.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SportEventRepository extends JpaRepository<SportEvent, Long>, JpaSpecificationExecutor<SportEvent> {
    Optional<SportEvent> findById(long id);

}