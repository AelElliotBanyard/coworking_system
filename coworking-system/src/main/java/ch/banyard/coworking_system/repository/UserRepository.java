package ch.banyard.coworking_system.repository;

import ch.banyard.coworking_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
