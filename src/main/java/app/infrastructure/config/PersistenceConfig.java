package app.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "app.application.adapters.persistence.sql.repositories")
@EnableMongoRepositories(basePackages = "app.application.adapters.persistence.mongodb.repositories")
public class PersistenceConfig {
}
