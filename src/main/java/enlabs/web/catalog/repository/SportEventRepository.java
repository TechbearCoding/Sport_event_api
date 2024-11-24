package enlabs.web.catalog.repository;

import enlabs.web.catalog.model.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SportEventRepository extends JpaRepository<SportEvent, Long>, JpaSpecificationExecutor<SportEvent> {
    Optional<SportEvent> findById(int id);

//    @Query("SELECT DISTINCT v.country FROM SportEvent v WHERE v.country IS NOT NULL")
//    List<String> findDistinctCountries();
//
//    @Query("SELECT DISTINCT v.vatStatus FROM SportEvent v WHERE v.vatStatus IS NOT NULL")
//    List<String> findDistinctVat();
}