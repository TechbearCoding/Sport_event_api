package enlabs.web.catalog.service;

import enlabs.web.catalog.model.EventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.repository.SportEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportEventService implements ISportEventService {


    private SportEventRepository sportEventRepository;

    @Autowired
    public SportEventService(SportEventRepository sportEventRepository) {
        this.sportEventRepository = sportEventRepository;
    }

    @Override
    public List<SportEvent> getSportEvents(String sport, String status) {
        return sportEventRepository.findFilteredEvents(
                sport.isEmpty() ? null : sport,
                status.isEmpty() ? null : status
        );
    }

    public SportEvent  createService(SportEvent event) {
        return sportEventRepository.save(event);
    }

    public SportEvent getSportEventById(int id) {
        return sportEventRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("SportEvent not found with ID: " + id));
    }

    public void updateSportEventStatus(Long id, String status) {
        SportEvent event = sportEventRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("SportEvent not found with ID: " + id));

        if(event != null) {
            event.setStatus(status);
            sportEventRepository.save(event);
        }
    }
}
