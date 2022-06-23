package api.ev.repository;

import api.ev.jpa.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyRepository extends JpaRepository<Company, Integer>
{
    @Query(value = "select * from company where parent_company_id =:id", nativeQuery = true)
    List<Company> findByParentId( @Param("id") Integer id );
}
