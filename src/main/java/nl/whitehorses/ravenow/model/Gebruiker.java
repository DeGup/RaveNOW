package nl.whitehorses.ravenow.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Gebruiker {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
