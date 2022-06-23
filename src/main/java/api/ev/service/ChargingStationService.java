package api.ev.service;

import api.ev.dto.CompanyDTO;
import api.ev.dto.CompanyResponseDTO;
import api.ev.dto.ResponseWrapper;
import api.ev.dto.StationDTO;
import api.ev.jpa.Company;
import api.ev.jpa.Station;
import api.ev.mapper.CriteriaMapper;
import api.ev.repository.CompanyRepository;
import api.ev.repository.StationRepository;
import api.ev.utility.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class will handle the service layer logics for Charging Station Entity.
 * @Author : rajeevth11@gmail.com
 */
@Service
@Transactional
public class ChargingStationService
{
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StationRepository stationRepository;

    /**
     * Create company entity.
     *
     * @param company of type Company
     * @return CompanyDTO
     */
    public ResponseEntity<ResponseWrapper<CompanyDTO>> createCompany( Company company )
    {
        ResponseEntity<ResponseWrapper<CompanyDTO>> droneResponseEntity = null;
        try
        {
            Optional<Company> parentCompany;
            if( company.getParentCompanyId() != null )
            {
                parentCompany = companyRepository.findById( company.getParentCompanyId() );
                if( !parentCompany.isPresent() )
                {
                    return new ResponseEntity<>( new ResponseWrapper<CompanyDTO>( ResponseWrapper.ERROR, "Invalid parent Id : " + company.getParentCompanyId() ), HttpStatus.BAD_REQUEST );
                }
                company.setParent( parentCompany.get() );
            }

            Company companyResult = companyRepository.save( company );
            droneResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyDTO>( ResponseWrapper.SUCCESS, "Company created successfully.", CriteriaMapper.INSTANCE.mapCompany( companyResult ) ), HttpStatus.CREATED );
        }
        catch( Exception e )
        {
            droneResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return droneResponseEntity;
    }

    /**
     * Retrieve company by id.
     *
     * @param id of type Integer
     * @return
     */
    public ResponseEntity<ResponseWrapper<CompanyResponseDTO>> getCompanyById( Integer id )
    {
        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> responseWrapperResponseEntity = null;
        try
        {
            Optional<Company> returnObj = companyRepository.findById( id );
            if( returnObj.isPresent() )
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.SUCCESS, "Company retrieved successfully.", CriteriaMapper.INSTANCE.mapCompanyResponse( returnObj.get() ) ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Company not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Update company by id.
     *
     * @param id      of type Integer
     * @param company of type Company
     * @return
     */
    public ResponseEntity<ResponseWrapper<CompanyDTO>> updateCompany( Integer id, Company company )
    {
        ResponseEntity<ResponseWrapper<CompanyDTO>> responseWrapperResponseEntity = null;
        try
        {
            Optional<Company> returnObj = companyRepository.findById( id );
            if( returnObj.isPresent() )
            {
                Optional<Company> parentCompany = null;
                if( company.getParentCompanyId() != null )
                {
                    parentCompany = companyRepository.findById( company.getParentCompanyId() );
                    if( !parentCompany.isPresent() )
                    {
                        return new ResponseEntity( new ResponseWrapper<CompanyDTO>( ResponseWrapper.ERROR, "Invalid parent Id : " + company.getParentCompanyId() ), HttpStatus.BAD_REQUEST );
                    }
                    company.setParent( parentCompany.get() );
                }

                Company companyResult = companyRepository.save( company );
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.SUCCESS, "Company updated successfully.", CriteriaMapper.INSTANCE.mapCompanyResponse( companyResult ) ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Company not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Delete company entity.
     *
     * @param id of type Integer
     * @return
     */
    public ResponseEntity<ResponseWrapper<CompanyResponseDTO>> deleteCompany( Integer id )
    {
        ResponseEntity<ResponseWrapper<CompanyResponseDTO>> responseWrapperResponseEntity = null;
        try
        {
            Optional<Company> returnObj = companyRepository.findById( id );
            if( returnObj.isPresent() )
            {
                companyRepository.deleteById( id );
                List<Company> childCompanies = companyRepository.findByParentId( id );
                //delete child companies along with the parent companies
                String msg = null;
                if( !childCompanies.isEmpty() )
                {
                    companyRepository.deleteAll( childCompanies );
                    msg = "Company deleted successfully along with the child companies.";
                }
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.SUCCESS, msg != null ? msg : "Company deleted successfully." ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Company not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<CompanyResponseDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Create station entity.
     *
     * @param station
     * @return
     */
    public ResponseEntity<ResponseWrapper<StationDTO>> createStation( Station station )
    {
        ResponseEntity<ResponseWrapper<StationDTO>> stationResponseEntity = null;
        try
        {
            Optional<Company> company = null;
            if( station.getCompanyId() != null )
            {
                company = companyRepository.findById( station.getCompanyId() );
                if( !company.isPresent() )
                {
                    return new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Invalid Company Id : " + station.getCompanyId() ), HttpStatus.BAD_REQUEST );
                }
                station.setCompany( company.get() );
            }
            else
            {
                return new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Mandatory parameter Company not found." ), HttpStatus.BAD_REQUEST );
            }
            Station stationResult = stationRepository.save( station );
            stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.SUCCESS, "Station created successfully.", CriteriaMapper.INSTANCE.mapStation( stationResult ) ), HttpStatus.CREATED );
        }
        catch( Exception e )
        {
            stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return stationResponseEntity;
    }


    /**
     * Update station entity.
     * .
     *
     * @param stationId
     * @param station
     * @return
     */
    public ResponseEntity<ResponseWrapper<StationDTO>> updateStation( Integer stationId, Station station )
    {
        ResponseEntity<ResponseWrapper<StationDTO>> stationResponseEntity = null;
        try
        {
            Optional<Station> stationObj = stationRepository.findById( stationId );
            if( stationObj.isPresent() )
            {
                if( station.getCompanyId() != null )
                {
                    Optional<Company> company = companyRepository.findById( station.getCompanyId() );
                    if( !company.isPresent() )
                    {
                        return new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Invalid Company Id : " + station.getCompanyId() ), HttpStatus.BAD_REQUEST );
                    }
                    station.setCompany( company.get() );
                    Station stationResult = stationRepository.save( station );
                    stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.SUCCESS, "Station updated successfully.", CriteriaMapper.INSTANCE.mapStation( stationResult ) ), HttpStatus.CREATED );
                }
                else
                {
                    return new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Company ID not found." ), HttpStatus.BAD_REQUEST );
                }
            }
            else
            {
                return new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Invalid Station Id : " + stationId ), HttpStatus.BAD_REQUEST );
            }
        }
        catch( Exception e )
        {
            stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return stationResponseEntity;
    }

    /**
     * Delete station entity.
     *
     * @param id of type Integer
     * @return
     */
    public ResponseEntity<ResponseWrapper<StationDTO>> deleteStation( Integer id )
    {
        ResponseEntity<ResponseWrapper<StationDTO>> responseWrapperResponseEntity = null;
        try
        {
            Optional<Station> returnObj = stationRepository.findById( id );
            if( returnObj.isPresent() )
            {
                stationRepository.deleteById( id );
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.SUCCESS, "Station deleted successfully." ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Station not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Search the nearest station for given company family.
     *
     * @param companyId
     * @param longitude
     * @param latitude
     * @param preferredDistance
     * @return
     */
    public ResponseEntity<ResponseWrapper<StationDTO>> searchStation( Integer companyId, double longitude, double latitude, double preferredDistance )
    {
        ResponseEntity<ResponseWrapper<StationDTO>> stationResponseEntity = null;
        try
        {
            Optional<Company> company = companyRepository.findById( companyId );
            if( !company.isPresent() )
            {
                return new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Invalid Company Id : " + companyId ), HttpStatus.BAD_REQUEST );
            }
            Station station = getNearestStation( company.get(), longitude, latitude, preferredDistance );
            if( station != null )
            {
                stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.SUCCESS, "Nearest Station found.", CriteriaMapper.INSTANCE.mapStation( station ) ), HttpStatus.OK );
            }
            else
            {
                stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "No matching station found withing the preferred distance." ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            stationResponseEntity = new ResponseEntity( new ResponseWrapper<StationDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return stationResponseEntity;
    }

    /**
     * Calculate distance and identify the nearest station within preferred distance of a given company and child companies.
     *
     * @param company
     * @param longitude
     * @param latitude
     * @param preferredDistance
     * @return Nearest station
     */
    private Station getNearestStation( Company company, double longitude, double latitude, double preferredDistance )
    {
        Station nearestStation = null;
        Set<Station> stations = company.getStations();
        for( Station station : stations )
        {
            double distance = DistanceCalculator.calculateDistance( latitude, station.getLatitude(), longitude, station.getLongitude() );
            if( distance <= preferredDistance )
            {
                nearestStation = station;
                preferredDistance = distance;
            }
        }
        if( !company.getChildren().isEmpty() )
        {
            for( Company childCompany : company.getChildren() )
            {
                Station station = getNearestStation( childCompany, longitude, latitude, preferredDistance );
                if( station != null )
                {
                    nearestStation = station;
                }
            }
        }
        return nearestStation;
    }
}
