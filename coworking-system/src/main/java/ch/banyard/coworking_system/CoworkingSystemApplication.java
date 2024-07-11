package ch.banyard.coworking_system;

import ch.banyard.coworking_system.model.Booking;
import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.Room;
import ch.banyard.coworking_system.model.enums.Day;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.model.enums.RoomType;
import ch.banyard.coworking_system.model.enums.Status;
import ch.banyard.coworking_system.repository.BookingRepository;
import ch.banyard.coworking_system.repository.RoomRepository;
import ch.banyard.coworking_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Date;

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
			String salt = BCrypt.gensalt();
			CoworkingUser u1 = new CoworkingUser("ael.banyard@banyard.ch", BCrypt.hashpw("123", salt), "ael", "banyard", false, Roles.ADMIN);
			CoworkingUser u2 = new CoworkingUser("en.lueber@gmail.com", BCrypt.hashpw("123", salt), "evan", "lueber", false, Roles.MEMBER);
			userRepository.save(u1);
			userRepository.save(u2);
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (CoworkingUser user : userRepository.findAll()) {
				log.info(user.toString());
			}

			Room r1 = new Room("Room 1", "First Floor Room 1", "101", 1  , RoomType.CONFERENCE_ROOM, 20);
			Room r2 = new Room("Room 2", "First Floor Room 2", "102", 1  , RoomType.WORKSPACE, 3);
			Room r3 = new Room("Room 3", "First Floor Room 3", "103", 1  , RoomType.MEETING_ROOM, 10);
			Room r4 = new Room("Room 4", "Second Floor Room 1", "201", 2  , RoomType.WORKSPACE, 3);
			roomRepository.save(r1);
			roomRepository.save(r2);
			roomRepository.save(r3);
			roomRepository.save(r4);
			log.info("Rooms found with findAll():");
			log.info("-------------------------------");
			for (Room room : roomRepository.findAll()) {
				log.info(room.toString());
			}

			Booking b1 = new Booking(Date.valueOf("2024-10-11"), Day.HALF_DAY_MORNING, Status.REQUESTED, u1, r1);
			Booking b2 = new Booking(Date.valueOf("2024-10-11"), Day.HALF_DAY_AFTERNOON, Status.REQUESTED, u2, r2);
			Booking b3 = new Booking(Date.valueOf("2024-10-11"), Day.FUll_DAY, Status.REQUESTED, u1, r3);
			Booking b4 = new Booking(Date.valueOf("2024-10-11"), Day.FUll_DAY, Status.REQUESTED, u2, r4);
			bookingRepository.save(b1);
			bookingRepository.save(b2);
			bookingRepository.save(b3);
			bookingRepository.save(b4);
			log.info("Bookings found with findAll():");
			log.info("-------------------------------");
			for (Booking booking : bookingRepository.findAll()) {
				log.info(booking.toString());
			}
		};
	}

}
