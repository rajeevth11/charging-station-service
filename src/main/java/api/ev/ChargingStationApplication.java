package api.ev;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = { "api.ev"})
//@EnableJpaRepositories(basePackages = {"api.ev.repository"})
//@EntityScan( basePackages = {"api.ev.jpa"})
public class ChargingStationApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run( ChargingStationApplication.class, args );
    }
}
