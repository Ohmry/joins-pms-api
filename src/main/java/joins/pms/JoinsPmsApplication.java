package joins.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JoinsPmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(JoinsPmsApplication.class, args);
	}
}
