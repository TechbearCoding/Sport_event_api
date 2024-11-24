package enlabs.web.catalog.service;

import enlabs.web.catalog.model.SportEvent;

import java.util.List;

public interface ISportEventService {
    public List<SportEvent> getSportEvents(
            String sport,
            String status);

}
