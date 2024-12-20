package enlabs.web.catalog.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class EventResponse {
    private List<SportEvent> items;

}
