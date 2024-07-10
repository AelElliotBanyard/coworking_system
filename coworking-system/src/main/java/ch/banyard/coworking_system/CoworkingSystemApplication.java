package ch.banyard.coworking_system;

import ch.banyard.coworking_system.repository.BookingRepository;
import ch.banyard.coworking_system.repository.RoomRepository;
import ch.banyard.coworking_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoworkingSystemApplication {

	private final Logger log = LoggerFactory.getLogger(CoworkingSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CoworkingSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
		return (args) -> {
			log.info("Coworking System Application started");
		};
	}

}
