package spring.project.chargingstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChargingStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargingStationApplication.class, args);
	}

}
