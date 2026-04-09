package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("app.application.adapters.persistence.entities")
public class FinancialSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialSystemApplication.class, args);
	}

}
