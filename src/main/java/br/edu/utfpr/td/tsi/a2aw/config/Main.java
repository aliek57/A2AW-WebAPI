package br.edu.utfpr.td.tsi.a2aw.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("br.edu.utfpr.td.tsi.a2aw")
@PropertySource("file:application.properties")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}