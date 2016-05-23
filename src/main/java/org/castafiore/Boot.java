package org.castafiore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableAutoConfiguration
@SpringBootApplication
@EnableNeo4jRepositories
public class Boot  {

	
	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}
	
	
	

	

}
