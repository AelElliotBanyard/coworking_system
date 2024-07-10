package ch.banyard.coworking_system.repository;

import ch.banyard.coworking_system.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
