package enlabs.web.catalog.service;

import enlabs.web.catalog.model.EventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.repository.SportEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return sportEventRepository.findAll();
    }

    public SportEvent  createService(SportEvent event) {
        return sportEventRepository.save(event);
    }

    public SportEvent getSportEventById(int id) {
        return sportEventRepository.findById(id).orElse(null);
    }
}
