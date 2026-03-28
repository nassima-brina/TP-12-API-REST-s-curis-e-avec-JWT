package ma.ens.security;

import ma.ens.security.entities.Role;
import ma.ens.security.entities.User;
import ma.ens.security.repositories.RoleRepository;
import ma.ens.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringJwtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApiApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UserRepository userRepository,
							RoleRepository roleRepository,
							PasswordEncoder passwordEncoder) {
		return args -> {
			// 1. Création des rôles
			Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));
			Role userRole = roleRepository.save(new Role(null, "ROLE_USER"));

			User admin = new User(
					null,
					"admin",
					passwordEncoder.encode("1234"),
					true,
					new ArrayList<>(List.of(adminRole, userRole))
			);

			userRepository.save(admin);

			User simpleUser = new User(
					null,
					"user",
					passwordEncoder.encode("1234"),
					true,
					new ArrayList<>(List.of(userRole))
			);

			userRepository.save(simpleUser);

			System.out.println("----------------------------------------------");
			System.out.println("Initialisation terminée !");
			System.out.println("Utilisateur 1: admin / 1234 (Rôles: ADMIN, USER)");
			System.out.println("Utilisateur 2: user / 1234 (Rôle: USER)");
			System.out.println("----------------------------------------------");
		};
	}
}

