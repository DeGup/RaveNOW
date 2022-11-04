package nl.whitehorses.ravenow.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Entity
@Data
public class Rave {
    @javax.persistence.Id
    @GeneratedValue
    private Long id;
    private String latitude;
    private String longitude;
    private String name;
    private String tags;
    private String omschrijving;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime datum;
}
