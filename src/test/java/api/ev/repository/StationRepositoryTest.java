package api.ev.repository;

import api.ev.jpa.Company;
import api.ev.jpa.Station;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StationRepositoryTest
{
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void createStationTest()
    {

        Company company = new Company( null, null, "Test Company", null, new HashSet<>(), new HashSet<>() );
        Company returnCompany = companyRepository.save( company );

        Station station = new Station( null, "Station 1", 6.695075564451507, 79.90842740343547,returnCompany.getId() , returnCompany );
        Station returnStation = stationRepository.save(station);

        Iterable<Station> stations = stationRepository.findAll();
        Assertions.assertThat( stations ).extracting( Station::getName ).contains( "Station 1" );
    }

    @Test
    public void createStationWithoutCompanyTest()
    {
        Station station = new Station( null, "Station 1", 6.695075564451507, 79.90842740343547, Integer.MAX_VALUE , null );
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows( DataIntegrityViolationException.class, () -> stationRepository.save(station));
        Assertions.assertThat(exception.getMessage().contains( "could not execute statement" ));
    }

    @Test
    public void updateStationTest()
    {
        Company company = new Company( null, null, "Test Company", null, new HashSet<>(), new HashSet<>() );
        Company returnCompany = companyRepository.save( company );

        Station station = new Station( null, "Station 1", 6.695075564451507, 79.90842740343547,returnCompany.getId() , returnCompany );
        stationRepository.save(station);

        Iterable<Station> stations = stationRepository.findAll();
        Assertions.assertThat( stations ).extracting( Station::getName ).contains( "Station 1" );

        //update
        station.setName( "Station 1 Updated" );

        stationRepository.save(station);

        Iterable<Station> returnStationsAfterUpdate = stationRepository.findAll();
        Assertions.assertThat( returnStationsAfterUpdate ).extracting( Station::getName ).contains( "Station 1 Updated" );
    }

    @Test
    public void deleteStationTest()
    {
        Company company = new Company( null, null, "Test Company", null, new HashSet<>(), new HashSet<>() );
        Company returnCompany = companyRepository.save( company );

        Station station = new Station( null, "Station 1", 6.695075564451507, 79.90842740343547,returnCompany.getId() , returnCompany );
        Station station1 = stationRepository.save(station);

        Iterable<Station> stations = stationRepository.findAll();
        Assertions.assertThat( stations ).extracting( Station::getName ).contains( "Station 1" );

        //delete station
        stationRepository.deleteById(station1.getId());

        Iterable<Station> returnStationsAfterUpdate = stationRepository.findAll();
        Assertions.assertThat( returnStationsAfterUpdate ).extracting( Station::getName ).isNotIn( "Station 1" );
    }

    @Test
    public void deleteStationWithCompanyTest()
    {
        Company company = new Company( null, null, "Test Company", null, new HashSet<>(), new HashSet<>() );
        Company returnCompany = companyRepository.save( company );

        Station station = new Station( null, "Station 1", 6.695075564451507, 79.90842740343547,returnCompany.getId() , returnCompany );
        Station station1 = stationRepository.save(station);

        Iterable<Station> stations = stationRepository.findAll();
        Assertions.assertThat( stations ).extracting( Station::getName ).contains( "Station 1" );

        //delete station
        companyRepository.deleteById(returnCompany.getId());

        Iterable<Station> returnStationsAfterUpdate = stationRepository.findAll();
        Assertions.assertThat( returnStationsAfterUpdate ).extracting( Station::getName ).isNotIn( "Station 1" );
    }
}
