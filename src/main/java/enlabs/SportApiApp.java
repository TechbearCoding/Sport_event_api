package enlabs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "enlabs.web.catalog.model")
@EnableJpaRepositories
public class SportApiApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SportApiApp.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
