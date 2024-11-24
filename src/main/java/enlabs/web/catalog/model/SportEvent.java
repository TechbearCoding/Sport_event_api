package enlabs.web.catalog.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sport_event")
@Data
public class SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sport;

    @Column(nullable = false)
    private String status;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    public SportEvent() {
    }
}
