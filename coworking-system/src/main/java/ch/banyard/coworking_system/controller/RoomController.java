package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.RoomDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	private final RoomService roomService;

	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	@GetMapping
	public ResponseEntity<List<RoomDTO>> getRooms() {
		List<RoomDTO> rooms = roomService.getAllRooms();
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(rooms);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
		try {
			RoomDTO room = roomService.getRoomById(id);
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(room);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}


	@PostMapping
	public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			RoomDTO room = roomService.createRoom(roomDTO);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(room);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			RoomDTO room = roomService.updateRoom(id, roomDTO);
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(room);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRoom(@PathVariable Long id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			roomService.deleteRoom(id);
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.build();
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}
}
