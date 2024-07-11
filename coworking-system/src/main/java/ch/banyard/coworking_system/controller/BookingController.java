package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.BookingDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping
	public ResponseEntity<List<BookingDTO>> getBookings() {
		List<BookingDTO> bookings = bookingService.getAllBookings();
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(bookings);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingDTO> getBooking(@PathVariable String id) {
		try {
			BookingDTO booking = bookingService.getBookingById(Long.valueOf(id));
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(booking);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}

	@PostMapping
	public ResponseEntity<BookingDTO> createBooking(BookingDTO bookingDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try{
		BookingDTO booking = bookingService.createBooking(bookingDTO, coworkingUser);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(booking);
		} catch (IllegalArgumentException e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookingDTO> updateBooking(@PathVariable String id, @RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			BookingDTO booking = bookingService.updateBooking(Long.valueOf(id), bookingDTO);
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(booking);
		} catch (IllegalArgumentException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable String id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			bookingService.deleteBooking(Long.valueOf(id));
			return ResponseEntity
					.status(HttpStatus.OK)
					.build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}
}
