package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Controller
public class VacancySearcherApplication {

	@Autowired
	private DBWorker db_worker;

	public static void main(String[] args) {
		SpringApplication.run(VacancySearcherApplication.class, args);
	}

	@GetMapping("requests/user/{username}")
	public String requestsByUser(@PathVariable(name = "username") String username, Model model) {
		HashMap<String, List<String>> date_map = new HashMap<>();
		try {
			date_map = db_worker.getUserRequests(username);
			model.addAttribute("date_map", date_map);
			model.addAttribute("username", username);
		} catch (Exception ignored) {

		}
		return "user_requests";

	}

	@GetMapping("requests/user/{username}/raw")
	@ResponseBody
	public Map<String, List<String>> requestsByUserRaw(@PathVariable(name = "username") String username) {
		HashMap<String, List<String>> date_map = new HashMap<>();
		try {
			date_map = db_worker.getUserRequests(username);
		} catch (Exception ignored) {

		}
		return date_map;

	}

	@GetMapping("/submitted")
	//@ResponseBody
	public String submitToDB(@RequestParam(value = "usernameInput") String username,
								 @RequestParam(value = "regionInput") String region,
								 @RequestParam(value = "vacancyInput") String vacancy, Model model) {
		try{
			db_worker.writeToDB(username, region, vacancy, 10);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "redirect:requests/user/" + username;
	}
}
