package dev.tanutapi.assignmentjavabootcamp;

import dev.tanutapi.assignmentjavabootcamp.user.User;
import dev.tanutapi.assignmentjavabootcamp.user.UserRepository;
import dev.tanutapi.assignmentjavabootcamp.user.UserService;
import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddress;
import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddressRepository;
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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserShippingAddressRepository userShippingAddressRepository;

	@PostConstruct
	void prepareData() {
		try {
			User user1 = userService.createUser("tanutapi", "qwerty", "Tanut", "Apiwong");
			User user2 = userService.createUser("hugo", "abc123", "Hugo", "Man");
			UserShippingAddress userShippingAddress1 = new UserShippingAddress(user1, "Tanut Apiwong", "123 street road", "Chatujak", "Bangkok", "10900", "0812345678");
			UserShippingAddress userShippingAddress2 = new UserShippingAddress(user2, "Hugo Man", "789 1st-street road", "Sathorn", "Bangkok", "10120", "0898765432");
			userShippingAddressRepository.save(userShippingAddress1);
			userShippingAddressRepository.save(userShippingAddress2);
			user1.setUserShippingAddress(userShippingAddress1);
			user2.setUserShippingAddress(userShippingAddress2);
			userRepository.save(user1);
			userRepository.save(user2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
