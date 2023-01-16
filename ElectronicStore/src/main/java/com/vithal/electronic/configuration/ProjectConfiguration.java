package com.vithal.electronic.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfiguration {
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

}
