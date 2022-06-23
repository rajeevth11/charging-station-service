package api.ev.mapper;

import api.ev.dto.CompanyDTO;
import api.ev.dto.CompanyResponseDTO;
import api.ev.dto.StationDTO;
import api.ev.jpa.Company;
import api.ev.jpa.Station;
import org.hibernate.annotations.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CriteriaMapper
{
    CriteriaMapper INSTANCE = Mappers.getMapper( CriteriaMapper.class );

    Company mapCompany( CompanyDTO companyDTO );

    CompanyDTO mapCompany( Company company );

    @Mapping(target = "childCompany", source = "children")
    CompanyResponseDTO mapCompanyResponse( Company company );

    Station mapStation( StationDTO station );

    StationDTO mapStation( Station stationResult );
}
