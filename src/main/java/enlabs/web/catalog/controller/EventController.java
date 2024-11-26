package enlabs.web.catalog.controller;

import enlabs.web.catalog.model.EventResponse;
import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.service.SportEventService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
class EventController {

    private final SportEventService service;

    @GetMapping("/api/events/")
    public SportEventResponse  findSport() {
        List<SportEvent> filteredVehicles = service.getSportEvents("", "");
        return new SportEventResponse()
                .setItems(new ArrayList<>(filteredVehicles));
    }

    @PostMapping("/api/createEvent")
    public EventResponse createSport(@RequestBody SportEvent event) {
        return service.createService(event);
    }

    @Data
    @Accessors(chain = true)
    public static class SportEventResponse {
        private List<SportEvent> items;
    }

}
