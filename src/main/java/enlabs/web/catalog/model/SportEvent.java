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
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sport;

    @Column(nullable = false)
    private String status;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    public SportEvent() {
    }

    public SportEvent(int id, String name, String sport, String status, String startTime) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.status = status;
        this.startTime = startTime;
    }
}
