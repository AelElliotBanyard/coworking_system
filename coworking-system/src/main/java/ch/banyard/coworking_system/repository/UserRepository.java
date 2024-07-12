package ch.banyard.coworking_system.repository;

import ch.banyard.coworking_system.model.CoworkingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CoworkingUser, Long> {
	Optional<CoworkingUser> findCoworkingUserByUsername(String username);
}
