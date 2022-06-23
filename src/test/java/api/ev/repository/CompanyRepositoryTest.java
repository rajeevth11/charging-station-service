package api.ev.repository;

import api.ev.jpa.Company;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest
{
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void createCompanyTest()
    {
        Company company = new Company( null, null, "My Charge Net", null, new HashSet<>(), new HashSet<>() );
        companyRepository.save( company );
        Iterable<Company> companies = companyRepository.findAll();
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "My Charge Net" );
    }

    @Test
    public void createCompanyWithParentTest()
    {
        Company parentCompany = new Company( null, null, "Parent Company", null, new HashSet<>(), new HashSet<>() );
        Company returnParent = companyRepository.save( parentCompany );

        Company company = new Company( null, returnParent.getId(), "Child Company", returnParent, new HashSet<>(), new HashSet<>() );
        companyRepository.save( company );

        Iterable<Company> companies = companyRepository.findAll();
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Child Company" );
    }

    @Test
    public void findChildCompaniesAssignedToParentTest()
    {
        Company parentCompany = new Company( null, null, "Parent Company", null, new HashSet<>(), new HashSet<>() );
        Company returnParent = companyRepository.save( parentCompany );

        Company company1 = new Company( null, returnParent.getId(), "Child Company1", returnParent, new HashSet<>(), new HashSet<>() );
        companyRepository.save( company1 );

        Company company2 = new Company( null, returnParent.getId(), "Child Company2", returnParent, new HashSet<>(), new HashSet<>() );
        companyRepository.save( company2 );

        Iterable<Company> companies = companyRepository.findAll();
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Parent Company" );
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Child Company1" );
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Child Company2" );

        List<Company> companyList =  companyRepository.findByParentId( returnParent.getId() );
        Assert.assertEquals( 2, companyList.size() );
    }

    @Test
    public void updateCompanyTest()
    {

        Company company = new Company( null, null, "Test Company", null, new HashSet<>(), new HashSet<>() );
        Company returnCompany = companyRepository.save( company );
        //update
        returnCompany.setName( "Test Company Updated" );

        Iterable<Company> companies = companyRepository.findAll();
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Test Company Updated" );
    }

    @Test
    public void deleteCompanyTest()
    {

        Company company = new Company( null, null, "Test Company", null, new HashSet<>(), new HashSet<>() );
        Company returnCompany = companyRepository.save( company );

        Iterable<Company> companies = companyRepository.findAll();
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Test Company" );

        companyRepository.deleteById( returnCompany.getId() );

        Iterable<Company> companiesAfterDelete = companyRepository.findAll();
        Assertions.assertThat( companiesAfterDelete ).extracting( Company::getName ).isNotIn( "Test Company" );
    }


    @Test
    public void deleteChildCompaniesWithParentTest()
    {
        int size = companyRepository.findAll().size();

        Company parentCompany = new Company( null, null, "Parent Company", null, new HashSet<>(), new HashSet<>() );
        Company returnParent = companyRepository.save( parentCompany );

        Company company1 = new Company( null, returnParent.getId(), "Child Company1", returnParent, new HashSet<>(), new HashSet<>() );
        companyRepository.save( company1 );

        Company company2 = new Company( null, returnParent.getId(), "Child Company2", returnParent, new HashSet<>(), new HashSet<>() );
        companyRepository.save( company2 );

        Iterable<Company> companies = companyRepository.findAll();
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Parent Company" );
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Child Company1" );
        Assertions.assertThat( companies ).extracting( Company::getName ).contains( "Child Company2" );

        List<Company> companyList =  companyRepository.findByParentId( returnParent.getId() );
        Assert.assertEquals( 2, companyList.size() );

        companyRepository.deleteById( returnParent.getId() ); //delete parent company
        companyRepository.deleteAll( companyList ); //delete child companies

        Assertions.assertThat( companies ).extracting( Company::getName ).isNotIn( "Parent Company", "Child Company1" , "Child Company2");
        Assert.assertEquals( size, companyRepository.findAll().size() );
    }
}
