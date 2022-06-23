package api.ev.repository;

import api.ev.jpa.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface StationRepository extends JpaRepository<Station, Integer>
{

}
