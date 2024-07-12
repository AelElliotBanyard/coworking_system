package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.RoomDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.model.request.RequestRoom;
import ch.banyard.coworking_system.service.RoomService;
import ch.banyard.coworking_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@Tag(name = "Room", description = "Room API")
public class RoomController {

	private final RoomService roomService;
	private final UserService userService;

	public RoomController(RoomService roomService, UserService userService) {
		this.roomService = roomService;
		this.userService = userService;
	}

	@GetMapping
	@Operation(summary = "Get all rooms")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rooms found",
					content = { @Content(mediaType = "application/json",
					array = @ArraySchema( schema = @Schema(implementation = RoomDTO.class)))}),
			@ApiResponse(responseCode = "404", description = "Rooms not found",
					content = @Content)
	})
	public ResponseEntity<List<RoomDTO>> getRooms() {
		List<RoomDTO> rooms = roomService.getAllRooms();
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(rooms);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get room by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Room found",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = RoomDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Room not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<RoomDTO> getRoom(@Parameter(description = "id of Room to get") @PathVariable String id) {
		try {
			RoomDTO room = roomService.getRoomById(Long.valueOf(id));
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
	@Operation(summary = "Create room")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Room created",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = RoomDTO.class)) }),
			@ApiResponse(responseCode = "409", description = "Room already exists",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<RoomDTO> createRoom(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "new room") @RequestBody @Valid RequestRoom requestRoom) {
		try {
			CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			RoomDTO room = roomService.createRoom(requestRoom);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(room);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.build();
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update room")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Room updated",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = RoomDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Room not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<RoomDTO> updateRoom( @Parameter(description = "id of room to update") @PathVariable String id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "room to update") @RequestBody @Valid RequestRoom roomDTO) {
		try {
			CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			RoomDTO newRoom = RoomDTO.builder().id(Long.valueOf(id)).name(roomDTO.getName()).capacity(roomDTO.getCapacity()).description(roomDTO.getDescription()).floor(roomDTO.getFloor()).shortName(roomDTO.getShortName()).type(roomDTO.getType()).build();
			RoomDTO room = roomService.updateRoom(Long.valueOf(id), newRoom);
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
	@Operation(summary = "Delete room")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Room deleted",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Room not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<Void> deleteRoom(@Parameter(description = "id of room to delete") @PathVariable String id) {
		try {
			CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			if (coworkingUser.getRole() != Roles.ADMIN) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
			roomService.deleteRoom(Long.valueOf(id));
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
