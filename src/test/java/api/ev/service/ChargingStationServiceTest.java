package api.ev.service;

import api.ev.dto.CompanyResponseDTO;
import api.ev.dto.ResponseWrapper;
import api.ev.dto.StationDTO;
import api.ev.jpa.Company;
import api.ev.jpa.Station;
import api.ev.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChargingStationServiceTest
{

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ChargingStationService chargingStationService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void getCompanyByIdTest()
    {
        Optional<Company> company = Optional.of( new Company( 1, null, "Charge Net", null, null, null ) );
        when( companyRepository.findById( 1 ) ).thenReturn( company );
        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> responseWrapperResponseEntity = chargingStationService.getCompanyById( 1 );
        assertEquals( responseWrapperResponseEntity.getBody().getData().size(), 1 );
        assertEquals( ( responseWrapperResponseEntity.getBody().getData().get( 0 ) ).getName(), "Charge Net" );
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
    }

    @Test
    public void getCompanyByIdNotFoundTest()
    {
        Optional<Company> company = Optional.empty();
        when( companyRepository.findById( 1 ) ).thenReturn( company );
        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> responseWrapperResponseEntity = chargingStationService.getCompanyById( 1 );
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.NOT_FOUND ) );
    }

    @Test
    public void searchStationTest1()
    {
        Set<Station> stations = new HashSet<>();
        Company company = new Company( 1, null, "Charge Net", null, new HashSet<>(), stations );
        stations.add( new Station(1, "Station 1", 6.896206869866274, 79.9203476035877, 1, company) ); //bathramulla
        stations.add( new Station(2, "Station 2", 6.031201908960236, 80.21627641781993, 1, company) ); //Galle
        Set<Station> childStations = new HashSet<>();
        Company childCompany = new Company( 2, 1, "Charge Net Sub", company, new HashSet<>(), childStations );
        childStations.add( new Station(3, "Station 3", 6.140855443547163, 80.10083414181895, 1, childCompany) ); //hikkaduwa
        childStations.add( new Station(4, "Station 4", 6.885663190290811, 79.91728007790157, 1, childCompany) ); //kotte

        Set<Company> childCompanies = new HashSet<>();
        childCompanies.add( childCompany );
        company.setChildren( childCompanies );
        Optional<Company> companyObj = Optional.of(  company );
        when( companyRepository.findById( 1 ) ).thenReturn( companyObj );
        ResponseEntity<ResponseWrapper<StationDTO>> responseWrapperResponseEntity  = chargingStationService.searchStation( 1,  80.0068873166711, 6.835896998536912, 50.0 ); //homagama
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
        assertEquals( ( responseWrapperResponseEntity.getBody().getData().get( 0 ) ).getName(), "Station 4" );
    }

    @Test
    public void searchStationTest2()
    {
        Set<Station> stations = new HashSet<>();
        Company company = new Company( 1, null, "Charge Net", null, new HashSet<>(), stations );
        stations.add( new Station(1, "Station 1", 6.896206869866274, 79.9203476035877, 1, company) ); //bathramulla
        stations.add( new Station(2, "Station 2", 6.835896998536912, 80.0068873166711, 1, company) ); //Homagama
        Set<Station> childStations = new HashSet<>();
        Company childCompany = new Company( 2, 1, "Charge Net Sub", company, new HashSet<>(), childStations );
        childStations.add( new Station(3, "Station 3", 6.140855443547163, 80.10083414181895, 1, childCompany) ); //hikkaduwa
        childStations.add( new Station(4, "Station 4", 6.885663190290811, 79.91728007790157, 1, childCompany) ); //kotte

        Set<Company> childCompanies = new HashSet<>();
        childCompanies.add( childCompany );
        company.setChildren( childCompanies );
        Optional<Company> companyObj = Optional.of(  company );
        when( companyRepository.findById( 1 ) ).thenReturn( companyObj );
        ResponseEntity<ResponseWrapper<StationDTO>> responseWrapperResponseEntity  = chargingStationService.searchStation( 1, 80.21627641781993 , 6.031201908960236, 50.0 ); //Galle
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
        assertEquals( ( responseWrapperResponseEntity.getBody().getData().get( 0 ) ).getName(), "Station 3" );
    }

    @Test
    public void searchStationFromOnlyChildCompanyStationsTest()
    {
        Set<Station> stations = new HashSet<>();
        Company company = new Company( 1, null, "Charge Net", null, new HashSet<>(), stations );
        Set<Station> childStations = new HashSet<>();
        Company childCompany = new Company( 2, 1, "Charge Net Sub", company, new HashSet<>(), childStations );
        stations.add( new Station(1, "Station 3", 6.140855443547163, 80.10083414181895, 1, childCompany) ); //hikkaduwa
        stations.add( new Station(2, "Station 4", 6.885663190290811, 79.91728007790157, 1, childCompany) ); //kotte

        Set<Company> childCompanies = new HashSet<>();
        childCompanies.add( childCompany );
        company.setChildren( childCompanies );
        Optional<Company> companyObj = Optional.of(  company );
        when( companyRepository.findById( 1 ) ).thenReturn( companyObj );
        ResponseEntity<ResponseWrapper<StationDTO>> responseWrapperResponseEntity  = chargingStationService.searchStation( 1, 80.21627641781993 , 6.031201908960236, 50.0 ); //Galle
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
        assertEquals( ( responseWrapperResponseEntity.getBody().getData().get( 0 ) ).getName(), "Station 3" );
    }

    @Test
    public void searchStationFromOnlyParentCompanyStationsTest()
    {
        Set<Station> stations = new HashSet<>();
        Company company = new Company( 1, null, "Charge Net", null, new HashSet<>(), stations );
        Set<Station> childStations = new HashSet<>();
        Company childCompany = new Company( 2, 1, "Charge Net Sub", company, new HashSet<>(), childStations );
        childStations.add( new Station(1, "Station 3", 6.140855443547163, 80.10083414181895, 1, childCompany) ); //hikkaduwa
        childStations.add( new Station(2, "Station 4", 6.885663190290811, 79.91728007790157, 1, childCompany) ); //kotte

        Set<Company> childCompanies = new HashSet<>();
        childCompanies.add( childCompany );
        company.setChildren( childCompanies );
        Optional<Company> companyObj = Optional.of(  company );
        when( companyRepository.findById( 1 ) ).thenReturn( companyObj );
        ResponseEntity<ResponseWrapper<StationDTO>> responseWrapperResponseEntity  = chargingStationService.searchStation( 1, 80.21627641781993 , 6.031201908960236, 50.0 ); //Galle
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
        assertEquals( ( responseWrapperResponseEntity.getBody().getData().get( 0 ) ).getName(), "Station 3" );
    }

    @Test
    public void searchStationNotFoundTest()
    {
        Set<Station> stations = new HashSet<>();
        Company company = new Company( 1, null, "Charge Net", null, new HashSet<>(), stations );
        stations.add( new Station(1, "Station 1", 6.896206869866274, 79.9203476035877, 1, company) ); //bathramulla
        stations.add( new Station(2, "Station 2", 6.835896998536912, 80.0068873166711, 1, company) ); //Homagama
        Set<Station> childStations = new HashSet<>();
        Company childCompany = new Company( 2, 1, "Charge Net Sub", company, new HashSet<>(), childStations );
        childStations.add( new Station(3, "Station 3", 6.140855443547163, 80.10083414181895, 1, childCompany) ); //hikkaduwa
        childStations.add( new Station(4, "Station 4", 6.885663190290811, 79.91728007790157, 1, childCompany) ); //kotte

        Set<Company> childCompanies = new HashSet<>();
        childCompanies.add( childCompany );
        company.setChildren( childCompanies );
        Optional<Company> companyObj = Optional.of(  company );
        when( companyRepository.findById( 1 ) ).thenReturn( companyObj );
        ResponseEntity<ResponseWrapper<StationDTO>> responseWrapperResponseEntity  = chargingStationService.searchStation( 1, 80.535278 ,  6.986498, 50.0 ); //Nuwara eliya
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.NOT_FOUND ) );
    }
}