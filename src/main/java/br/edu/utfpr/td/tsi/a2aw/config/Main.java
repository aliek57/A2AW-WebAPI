package br.edu.utfpr.td.tsi.a2aw.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "br.edu.utfpr.td.tsi.exemplo.webapi.repository")
@ComponentScan("br.edu.utfpr.td.tsi.exemplo.webapi")
@PropertySource("file:application.properties")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}