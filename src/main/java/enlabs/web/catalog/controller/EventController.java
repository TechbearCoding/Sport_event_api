package enlabs.web.catalog.controller;

import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.service.SportEventService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
class EventController {

    private final SportEventService service;

    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve a list of all events")
    public sportEventsResponse getEvents() {
        List<SportEvent> filteredEvents = service.getSportEvents("", "");
        return new sportEventsResponse()
                .setItems(new ArrayList<>(filteredEvents));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by Id", description = "Retrieve an event")
    public SportEvent getEventById(@PathVariable int id) {
        return service.getSportEventById(id);
    }

    @PostMapping("/createEvent")
    @Operation(summary = "Create a new event", description = "Create a new sports event")
    public SportEvent createSport(@RequestBody SportEvent event) {
        return service.createService(event);
    }

    @Data
    @Accessors(chain = true)
    public static class sportEventsResponse {
        private List<SportEvent> items;
    }

}
