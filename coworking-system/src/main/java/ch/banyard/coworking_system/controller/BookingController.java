package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.BookingDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking", description = "Booking API")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping
	@Operation(summary = "Get all bookings")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Bookings found",
					content = { @Content(mediaType = "application/json",
					array = @ArraySchema( schema = @Schema(implementation = BookingDTO.class)))}),
			@ApiResponse(responseCode = "404", description = "Bookings not found",
					content = @Content)
	})
	public ResponseEntity<List<BookingDTO>> getBookings() {
		List<BookingDTO> bookings = bookingService.getAllBookings();
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(bookings);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get booking by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Booking found",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = BookingDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Booking not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<BookingDTO> getBooking(@Parameter(description = "id of booking to get")  @PathVariable String id) {
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
	@Operation(summary = "Create booking")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Booking created",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = BookingDTO.class)) }),
			@ApiResponse(responseCode = "409", description = "Booking already exists",
					content = @Content)
	})
	public ResponseEntity<BookingDTO> createBooking(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "new booking") @RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
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
	@Operation(summary = "Update booking")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Booking updated",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = BookingDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Booking not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<BookingDTO> updateBooking(@Parameter(description = "id of booking to update") @PathVariable String id,@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "booking to update") @RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				if (!Objects.equals(bookingService.getBookingById(Long.valueOf(id)).coworkingUserDTO().id(), coworkingUser.getId()))
				{
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				}
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
	@Operation(summary = "Delete booking")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Booking deleted",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Booking not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<Void> deleteBooking(@Parameter(description = "id of booking to delete") @PathVariable String id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				if (!Objects.equals(bookingService.getBookingById(Long.valueOf(id)).coworkingUserDTO().id(), coworkingUser.getId()))
				{
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				}
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
