package tests.service;

import enlabs.web.catalog.model.SportEvent;
import enlabs.web.catalog.repository.SportEventRepository;
import enlabs.web.catalog.service.SportEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SportEventServiceTest {

    @Mock
    private SportEventRepository sportEventRepository;

    @InjectMocks
    private SportEventService sportEventService;

    @Test
    void shouldGetSportEventById() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "inactive",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(1)).thenReturn(Optional.of(mockEvent));

        SportEvent result = sportEventService.getSportEventById(1);

        assertNotNull(result);
        assertEquals("Football Match", result.getName());
        verify(sportEventRepository, times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionIfEventNotFound() {
        when(sportEventRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sportEventService.getSportEventById(1));
    }


    @Test
    void shouldUpdateSportEventStatus() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "Inactive",
                LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(eq(1))).thenReturn(Optional.of(mockEvent));

        sportEventService.updateSportEventStatus(1, "Active");

        verify(sportEventRepository, times(1)).save(mockEvent);
        assertEquals("Active", mockEvent.getStatus());
    }

    @Test
    void shouldNotActivateEventWithPastStartTime() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "Inactive",
                LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(eq(1))).thenReturn(Optional.of(mockEvent));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                sportEventService.updateSportEventStatus(1, "Active")
        );

        assertEquals("Cannot activate an event if the start time is in the past.", exception.getMessage());
    }

    @Test
    void shouldChangeStatusFromInactiveToActive() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "Inactive",
                LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(eq(1))).thenReturn(Optional.of(mockEvent));

        sportEventService.updateSportEventStatus(1, "Active");

        verify(sportEventRepository, times(1)).save(mockEvent);
        assertEquals("Active", mockEvent.getStatus());
    }

    @Test
    void shouldChangeStatusFromActiveToFinished() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "Active",
                LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(eq(1))).thenReturn(Optional.of(mockEvent));

        sportEventService.updateSportEventStatus(1, "Finished");

        verify(sportEventRepository, times(1)).save(mockEvent);
        assertEquals("Finished", mockEvent.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenChangingStatusFromFinishedToDifferentOtherStatus() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "Finished",
                LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(eq(1))).thenReturn(Optional.of(mockEvent));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sportEventService.updateSportEventStatus(1, "Active");
        });

        assertEquals("Cannot change status from 'Finished' to any other status.", exception.getMessage());
        verify(sportEventRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenChangingStatusFromInactiveToFinished() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "Inactive",
                LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(eq(1))).thenReturn(Optional.of(mockEvent));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sportEventService.updateSportEventStatus(1, "Finished");
        });

        assertEquals("Cannot change status from 'Inactive' to 'Finished'.", exception.getMessage());
        verify(sportEventRepository, never()).save(any());
    }

}

