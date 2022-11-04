package nl.whitehorses.ravenow.repositories;

import nl.whitehorses.ravenow.model.Rave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaveRepo extends JpaRepository<Rave, Long> {
}
