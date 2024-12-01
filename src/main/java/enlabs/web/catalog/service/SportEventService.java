package enlabs.web.catalog.service;

import enlabs.web.catalog.model.EventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.repository.SportEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        LocalDateTime startTime = LocalDateTime.parse(event.getStartTime());
        if (startTime.toLocalTime().equals(LocalTime.MIN)) {
            startTime = startTime.with(LocalTime.MIDNIGHT);
            event.setStartTime(String.valueOf(startTime));
        }

        return sportEventRepository.save(event);
    }

    public SportEvent getSportEventById(int id) {
        return sportEventRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("SportEvent not found with ID: " + id));
    }

    public void updateSportEventStatus(Long id, String status) {
        SportEvent event = sportEventRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("SportEvent not found with ID: " + id));

        String currentStatus = event.getStatus();
        LocalDateTime startTime = LocalDateTime.parse(event.getStartTime());

        if (currentStatus.equals("Finished")) {
            throw new IllegalArgumentException("Cannot change status from 'Finished' to any other status.");
        }
        if (currentStatus.equals("Inactive") && status.equals("Finished")) {
            throw new IllegalArgumentException("Cannot change status from 'Inactive' to 'Finished'.");
        }
        if (status.equals("Active") && startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot activate an event if the start time is in the past.");
        }
        if (currentStatus.equals("Inactive") && !status.equals("Active")) {
            throw new IllegalArgumentException("Can only change status from 'Inactive' to 'Active'.");
        }
        if (currentStatus.equals("Active") && !status.equals("Finished")) {
            throw new IllegalArgumentException("Can only change status from 'Active' to 'Finished'.");
        }

        event.setStatus(status);
        sportEventRepository.save(event);
    }
}
