package com.aluracursosByWolf.LiterAlura;

import com.aluracursosByWolf.LiterAlura.principal.Principal;
import com.aluracursosByWolf.LiterAlura.repository.IAutoresRepository;
import com.aluracursosByWolf.LiterAlura.repository.ILibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aluracursosByWolf.LiterAlura")
public class LiterAluraApplication implements CommandLineRunner{

	@Autowired
	private Principal principal;

	@Autowired
	private IAutoresRepository autoresRepository;
	@Autowired
	private ILibrosRepository librosRepository;

	@Override
	public void run(String... args) throws Exception {

		principal.muestraElMenu();
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}
}
