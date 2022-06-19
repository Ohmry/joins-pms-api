package joins.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JoinsPmsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(JoinsPmsApiApplication.class, args);
	}
}
