package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class VacancySearcherApplication {

	@Autowired
	private SkillsWriter skills_writer;

	public static void main(String[] args) {
		SpringApplication.run(VacancySearcherApplication.class, args);
	}

	@GetMapping("/written")
	public String enterVacancies(@RequestParam(value = "usernameInput") String username,
								 @RequestParam(value = "regionInput") String region,
								 @RequestParam(value = "vacancyInput") String vacancy) {
		try{
			skills_writer.writeSkills(username, region, vacancy);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "Skills written.";
	}
}
