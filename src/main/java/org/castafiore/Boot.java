package org.castafiore;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@EnableAutoConfiguration
@SpringBootApplication
@EnableNeo4jRepositories

public class Boot  {

	
	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}
	
	
	@Bean
	public LocaleResolver localeResolver() {
	 SessionLocaleResolver slr = new SessionLocaleResolver();
	 slr.setDefaultLocale(Locale.US); // Set default Locale as US
	 return slr;
	}
	 
	@Bean
	public ResourceBundleMessageSource messageSource() {
	 ResourceBundleMessageSource source = new ResourceBundleMessageSource();
	 source.setBasenames("i18n/messages");  // name of the resource bundle 
	 source.setUseCodeAsDefaultMessage(true);
	 return source;
	}

	

}
