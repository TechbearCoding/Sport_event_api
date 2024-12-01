package enlabs.web.catalog.controller;

import enlabs.web.catalog.model.EventResponse;
import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.service.SportEventService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
class EventController {

    private final SportEventService service;

    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve a list of all events")
    public EventResponse getEvents(
            @RequestParam(required = false, defaultValue = "") String sport,
            @RequestParam(required = false, defaultValue = "") String status) {
        List<SportEvent> filteredEvents = service.getSportEvents(sport, status);
        return new EventResponse()
                .setItems(new ArrayList<>(filteredEvents));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by Id", description = "Retrieve an event")
    public ResponseEntity<SportEvent> getEventById(@PathVariable int id) {
        try {
            SportEvent event = service.getSportEventById(id);
            return ResponseEntity.ok(event);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/createEvent")
    @Operation(summary = "Create a new event", description = "Create a new sports event")
    public ResponseEntity<SportEvent> createSport(@RequestBody SportEvent event) {
        try{
            SportEvent createdEvent = service.createService(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        }catch(DateTimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update sport event status", description = "Update the status of a specific sport event")
    public ResponseEntity<String> updateEventStatus(
            @PathVariable int id,
            @RequestParam String status) {
        try{
            service.updateSportEventStatus(id, status);
            return ResponseEntity.ok("Status updated successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch(IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

}
