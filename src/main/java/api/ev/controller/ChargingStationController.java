package api.ev.controller;

import api.ev.dto.CompanyDTO;
import api.ev.dto.CompanyResponseDTO;
import api.ev.dto.ResponseWrapper;
import api.ev.dto.StationDTO;
import api.ev.mapper.CriteriaMapper;
import api.ev.service.ChargingStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class will handle the all the requests for the Charging Station service.
 * @Author : rajeevth11@gmail.com
 */
@RestController
@RequestMapping(value = "charging-station-service/api")
@Api("ChargingStationController - REST APIs related to Charging Station Entity")
public class ChargingStationController
{
    @Autowired
    private ChargingStationService chargingStationService;

    @ApiOperation(value = "Create Company entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.POST}, value = "/companies", produces = "application/json")
    public ResponseEntity<ResponseWrapper<CompanyDTO>> createCompany( @RequestBody(required = true) CompanyDTO company )
    {
        return chargingStationService.createCompany( CriteriaMapper.INSTANCE.mapCompany( company ) );
    }

    @ApiOperation(value = "Retrieve Company entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.GET}, value = "/companies/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper<CompanyResponseDTO>> getCompanyById( @ApiParam(value = "Company ID") @PathVariable Integer id )
    {
        return chargingStationService.getCompanyById( id );
    }

    @ApiOperation(value = "Update Company entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Updated."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.PUT}, value = "/companies/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper<CompanyDTO>> updateCompany( @ApiParam(value = "Company ID") @PathVariable Integer id
            , @RequestBody(required = true) CompanyDTO company )
    {
        return chargingStationService.updateCompany( id, CriteriaMapper.INSTANCE.mapCompany( company ) );
    }

    @ApiOperation(value = "Delete Company entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.DELETE}, value = "/companies/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper<CompanyResponseDTO>> deleteCompany( @ApiParam(value = "Company ID") @PathVariable Integer id )
    {
        return chargingStationService.deleteCompany( id );
    }

    @ApiOperation(value = "Create Station entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.POST}, value = "/stations", produces = "application/json")
    public ResponseEntity<ResponseWrapper<StationDTO>> createStation( @RequestBody(required = true) StationDTO station )
    {
        return chargingStationService.createStation( CriteriaMapper.INSTANCE.mapStation( station ) );
    }

    @ApiOperation(value = "Update Station entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.PUT}, value = "/stations/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper<StationDTO>> updateStation( @ApiParam(value = "Station ID") @PathVariable Integer id,
                                                                      @RequestBody(required = true) StationDTO station )
    {
        return chargingStationService.updateStation( id, CriteriaMapper.INSTANCE.mapStation( station ) );
    }

    @ApiOperation(value = "Search nearest Station.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.GET}, value = "/stations", produces = "application/json")
    public ResponseEntity<ResponseWrapper<StationDTO>> searchStation( @ApiParam(value = "Company") @RequestParam Integer company,
                                                                      @ApiParam(value = "Longitude") @RequestParam double longitude,
                                                                      @ApiParam(value = "Latitude") @RequestParam double latitude,
                                                                      @ApiParam(value = "Preferred Distance") @RequestParam double preferredDistance )
    {
        return chargingStationService.searchStation( company, longitude, latitude, preferredDistance );
    }

    @ApiOperation(value = "Delete Station entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.DELETE}, value = "/stations/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper<StationDTO>> deleteStation( @ApiParam(value = "Station ID") @PathVariable Integer id )
    {
        return chargingStationService.deleteStation( id );
    }

}
