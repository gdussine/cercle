package bot.inhouse.event;

import java.time.LocalDateTime;

import jakarta.persistence.Id;

public class InHouseEvent {

    @Id
    private String id;

    private String name;

    private LocalDateTime creationDate;
}
