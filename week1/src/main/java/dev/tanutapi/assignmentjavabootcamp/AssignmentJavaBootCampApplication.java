package dev.tanutapi.assignmentjavabootcamp;

import dev.tanutapi.assignmentjavabootcamp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AssignmentJavaBootCampApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentJavaBootCampApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@PostConstruct
	void prepareData() {
		try {
			userService.createUser("tanutapi", "qwerty", "Tanut", "Apiwong");
			userService.createUser("hugo", "abc123", "Hugo", "Man");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
