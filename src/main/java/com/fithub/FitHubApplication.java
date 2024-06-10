package com.fithub;

import com.fithub.model.user.Role;
import com.fithub.model.user.User;
import com.fithub.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class FitHubApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(FitHubApplication.class, args);
	}

	@Value("${admin-name}")
	private String adminName;
	@Value("${admin-email}")
	private String adminEmail;
	@Value("${admin-username}")
	private String adminUsername;
	@Value("${admin-password}")
	private String adminPassword;

	@Override
	public void run(String... args) throws Exception {
		User admin = userRepository.findUserByRole(Role.ADMIN);
		if (admin == null){
			User user = new User();

			user.setEmail(adminEmail);
			user.setUsername(adminUsername);
			user.setName(adminName);
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
			userRepository.save(user);
		}
	}
}
