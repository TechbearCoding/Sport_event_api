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
        when(sportEventRepository.findById(anyLong())).thenReturn(Optional.of(mockEvent));

        SportEvent result = sportEventService.getSportEventById(1);

        assertNotNull(result);
        assertEquals("Football Match", result.getName());
        verify(sportEventRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionIfEventNotFound() {
        when(sportEventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sportEventService.getSportEventById(1));
    }


    @Test
    void shouldUpdateSportEventStatus() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "inactive",
                LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        doReturn(Optional.of(mockEvent)).when(sportEventRepository).findById(1L);

        sportEventService.updateSportEventStatus(1L, "active");

        verify(sportEventRepository, times(1)).save(mockEvent);
        assertEquals("active", mockEvent.getStatus());
    }

    @Test
    void shouldNotActivateEventWithPastStartTime() {
        SportEvent mockEvent = new SportEvent(
                1,
                "Football Match",
                "Football",
                "inactive",
                LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        when(sportEventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        assertThrows(IllegalArgumentException.class, () -> sportEventService.updateSportEventStatus(1L, "active"));
    }
}

