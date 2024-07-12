package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.CoworkingUserDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.service.UserService;
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
@RequestMapping("/users")
@Tag(name = "User", description = "User API")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@Operation(summary = "Get all users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Users found",
					content = { @Content(mediaType = "application/json",
					array = @ArraySchema( schema = @Schema(implementation = CoworkingUserDTO.class)))}),
			@ApiResponse(responseCode = "404", description = "Users not found",
					content = @Content)
	})
	public ResponseEntity<List<CoworkingUserDTO>> getUsers() {
		List<CoworkingUserDTO> users = userService.getAllUsers();
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(users);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = CoworkingUserDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<CoworkingUserDTO> getUser(@Parameter(description = "id of user to get") @PathVariable String id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				if (!Objects.equals(coworkingUser.getId(), Long.valueOf(id))) {
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				}
			}
			CoworkingUserDTO user = userService.getUserById(Long.valueOf(id));
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(user);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}

	@PostMapping("/register")
	@Operation(summary = "Create user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = CoworkingUserDTO.class)) }),
			@ApiResponse(responseCode = "409", description = "User already exists",
					content = @Content)
	})
	public ResponseEntity<CoworkingUserDTO> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "new user") @RequestBody CoworkingUserDTO userDTO) {
		try {
			CoworkingUserDTO user = userService.createUser(userDTO);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.build();
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = CoworkingUserDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<CoworkingUserDTO> updateUser(@Parameter(description = "id of user to be updated") @PathVariable String id,@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user to update") @RequestBody CoworkingUserDTO userDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				if (!Objects.equals(coworkingUser.getId(), Long.valueOf(id))) {
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				}
			}
			CoworkingUserDTO user = userService.updateUser(Long.valueOf(id), userDTO);
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(user);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "User deleted",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden",
					content = @Content)
	})
	public ResponseEntity<Void> deleteUser(@Parameter(description = "id of user to be deleted") @PathVariable String id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
		try {
			if (coworkingUser.getRole() != Roles.ADMIN) {
				if (!Objects.equals(coworkingUser.getId(), Long.valueOf(id))) {
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				}
			}
			userService.deleteUser(Long.valueOf(id));
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
