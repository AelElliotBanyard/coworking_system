package ch.banyard.coworking_system.repository;

import ch.banyard.coworking_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}