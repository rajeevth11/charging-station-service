package api.ev.controller;

import api.ev.dto.CompanyDTO;
import api.ev.dto.CompanyResponseDTO;
import api.ev.dto.ResponseWrapper;
import api.ev.jpa.Company;
import api.ev.service.ChargingStationService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChargingStationControllerTest
{

    //    @Mock
    //    private CompanyRepository companyRepository;
    //
    //    @Mock
    //    private StationRepository stationRepository;

    @Mock
    private ChargingStationService chargingStationService;

    @InjectMocks
    private ChargingStationController chargingStationController;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void createCompanyTest()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );
        CompanyDTO companyDTO = new CompanyDTO( 1, null, "Test Company" );
        //Company returnCompany = new Company(1, null,"Test Company", null, null, null );
        ResponseEntity<ResponseWrapper<CompanyDTO>> companyResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyDTO>( ResponseWrapper.SUCCESS, "Company created successfully.", companyDTO ), HttpStatus.CREATED );
        when( chargingStationService.createCompany( any( Company.class ) ) ).thenReturn( companyResponseEntity );

        ResponseEntity<ResponseWrapper<CompanyDTO>> response = chargingStationController.createCompany( new CompanyDTO( null, null, "Test Company" ) );
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response).isEqualTo(companyResponseEntity);
    }

    @Test
    public void getCompanyByIdSuccessTest()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );
        CompanyResponseDTO companyDTO = new CompanyResponseDTO( 1, null, "Test Company", null, new HashSet<>() );
        //Company returnCompany = new Company(1, null,"Test Company", null, null, null );
        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> companyResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.SUCCESS, "Company retrieved successfully.", companyDTO ), HttpStatus.OK );
        when( chargingStationService.getCompanyById( 1 )).thenReturn( companyResponseEntity );

        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> response = chargingStationController.getCompanyById( 1 );
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response).isEqualTo(companyResponseEntity);
    }

    @Test
    public void getCompanyByIdNotFoundTest()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );
        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> companyResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Company not found for Id : 1." ), HttpStatus.NOT_FOUND );
        when( chargingStationService.getCompanyById( 1 )).thenReturn( companyResponseEntity );

        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> response = chargingStationController.getCompanyById( 1 );
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response).isEqualTo(companyResponseEntity);
    }


}
