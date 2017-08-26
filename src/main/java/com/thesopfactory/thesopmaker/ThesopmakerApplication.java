package com.thesopfactory.thesopmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan( basePackages="com.thesopfactory.thesopmaker" )
public class ThesopmakerApplication {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(ThesopmakerApplication.class, args);
	}
	
}
