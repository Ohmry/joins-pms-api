package joins.pms;

import joins.pms.api.v1.test.DummyCreator;
import joins.pms.api.v1.test.DummyType;
import joins.pms.core.context.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JoinsPmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(JoinsPmsApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner () {
		return args -> {
			Environment environment = SpringContext.getBean(Environment.class);
			boolean isLocal = false;

			for (String profile : environment.getActiveProfiles()) {
				if (profile.equals("local")) {
					isLocal = true;
					break;
				}
			}

			if (isLocal) {
				DummyCreator dummyCreator = new DummyCreator();
				dummyCreator.create(DummyType.Schedule, 50);
			}
		};
	}
}
