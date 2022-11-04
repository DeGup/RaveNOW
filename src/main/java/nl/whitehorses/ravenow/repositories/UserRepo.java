package nl.whitehorses.ravenow.repositories;

import nl.whitehorses.ravenow.model.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Gebruiker, Long> {

    Optional<Gebruiker> findByUsernameAndPassword(String username, String password);
}
