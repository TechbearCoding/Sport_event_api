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


    @Autowired
    private SportEventRepository sportEventRepository;

    @Override
    public List<SportEvent> getSportEvents(String sport, String status) {

        List<SportEvent> sportEvents = sportEventRepository.findAll();

        return sportEvents;
    }

    public EventResponse createService(SportEvent event) {
        return new EventResponse();
    }
}
