package nl.whitehorses.ravenow.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Gabber {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String preferences;
    private String location;
}
