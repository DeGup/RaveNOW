package nl.whitehorses.ravenow.repositories;

import nl.whitehorses.ravenow.model.Gabber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GabberRepo extends JpaRepository<Gabber, Long> {
}
