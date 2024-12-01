package enlabs.web.catalog.service;

import enlabs.web.catalog.model.SportEvent;

import java.util.List;

public interface ISportEventService {
    List<SportEvent> getSportEvents(String sport, String status);
    SportEvent createService(SportEvent event);
    SportEvent getSportEventById(int id);
    void updateSportEventStatus(Long id, String status);
}
