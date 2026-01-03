package com.ainesh.TeamTaskTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ainesh.TeamTaskTracker.dao")
@EnableMongoRepositories(basePackages = "com.ainesh.TeamTaskTracker.dao.mongoDao")
@EnableTransactionManagement
public class TeamTaskTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamTaskTrackerApplication.class, args);
	}

}
