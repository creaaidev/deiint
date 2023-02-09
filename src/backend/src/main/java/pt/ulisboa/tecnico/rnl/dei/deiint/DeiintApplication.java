package pt.ulisboa.tecnico.rnl.dei.deiint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class DeiintApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeiintApplication.class, args);
	}

}