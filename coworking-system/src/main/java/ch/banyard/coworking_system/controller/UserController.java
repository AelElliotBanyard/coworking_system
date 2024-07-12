package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.request.RequestUser;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User API")
public class UserController {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
		CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (coworkingUser.getRole() != Roles.ADMIN) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.build();
		} else {
			List<CoworkingUserDTO> users = userService.getAllUsers();
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(users);
		}
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
	public ResponseEntity<CoworkingUserDTO> getUser(@Parameter(description = "id of user to get") @PathVariable String id) {
		try {

			CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			if (coworkingUser.getRole() != Roles.ADMIN) {
				if (!Objects.equals(coworkingUser.getId(), Long.valueOf(id))) {
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				} else {
					CoworkingUserDTO user = userService.getUserById(Long.valueOf(id));
					return ResponseEntity
							.status(HttpStatus.OK)
							.contentType(MediaType.APPLICATION_JSON)
							.body(user);

				}
			} else {
				CoworkingUserDTO user = userService.getUserById(Long.valueOf(id));
				return ResponseEntity
						.status(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(user);
			}
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
	public ResponseEntity<CoworkingUserDTO> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "new user") @RequestBody RequestUser requestUser) {
		try {
			requestUser.setPassword(bCryptPasswordEncoder.encode(requestUser.getPassword()));
			CoworkingUserDTO user = userService.createUser(requestUser);
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
	public ResponseEntity<CoworkingUserDTO> updateUser(@Parameter(description = "id of user to be updated") @PathVariable String id,@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user to update") @RequestBody CoworkingUserDTO userDTO) {
		try {
			CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			if (!Objects.equals(coworkingUser.getId(), Long.valueOf(id))) {
			if (coworkingUser.getRole() != Roles.ADMIN) {
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				} else {
				CoworkingUserDTO oldUser = userService.getUserById(Long.valueOf(id));
				CoworkingUser newUser = new CoworkingUser();
				newUser.setId(Long.valueOf(id));
				if (userDTO.password() == null) {
					newUser.setPassword(oldUser.password());
				} else {
					newUser.setPassword(userDTO.password());
				}
				if (userDTO.firstName() == null) {
					newUser.setFirstName(oldUser.firstName());
				} else {
					newUser.setFirstName(userDTO.firstName());
				}
				if (userDTO.lastName() == null) {
					newUser.setLastName(oldUser.lastName());
				} else {
					newUser.setLastName(userDTO.lastName());
				}
				if (userDTO.username() == null) {
					newUser.setUsername(oldUser.username());
				} else {
					newUser.setUsername(userDTO.username());
				}
				if (userDTO.blocked() != null && coworkingUser.getRole() == Roles.ADMIN){
					newUser.setBlocked(userDTO.blocked());
				}
				else{
					newUser.setBlocked(oldUser.blocked());
			}
				if (userDTO.role() != null && coworkingUser.getRole() == Roles.ADMIN){
					newUser.setRole(userDTO.role());
				}
				else{
					newUser.setRole(oldUser.role());
				}
					CoworkingUserDTO user = userService.updateUser(Long.valueOf(id), newUser);
					return ResponseEntity
							.status(HttpStatus.OK)
							.contentType(MediaType.APPLICATION_JSON)
							.body(user);
			}
			} else {
				CoworkingUserDTO oldUser = userService.getUserById(Long.valueOf(id));
				CoworkingUser newUser = new CoworkingUser();
				newUser.setId(Long.valueOf(id));
				if (userDTO.password() == null) {
					newUser.setPassword(oldUser.password());
				} else {
					newUser.setPassword(userDTO.password());
				}
				if (userDTO.firstName() == null) {
					newUser.setFirstName(oldUser.firstName());
				} else {
					newUser.setFirstName(userDTO.firstName());
				}
				if (userDTO.lastName() == null) {
					newUser.setLastName(oldUser.lastName());
				} else {
					newUser.setLastName(userDTO.lastName());
				}
				if (userDTO.username() == null) {
					newUser.setUsername(oldUser.username());
				} else {
					newUser.setUsername(userDTO.username());
				}
				if (userDTO.blocked() != null && coworkingUser.getRole() == Roles.ADMIN){
					newUser.setBlocked(userDTO.blocked());
				}
				else{
					newUser.setBlocked(oldUser.blocked());
				}
				if (userDTO.role() != null && coworkingUser.getRole() == Roles.ADMIN){
					newUser.setRole(userDTO.role());
				}
				else{
					newUser.setRole(oldUser.role());
				}
				CoworkingUserDTO user = userService.updateUser(Long.valueOf(id), newUser);
				return ResponseEntity
						.status(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(user);
			}
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
	public ResponseEntity<Void> deleteUser(@Parameter(description = "id of user to be deleted") @PathVariable String id) {
		try {
			CoworkingUser coworkingUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			if (!Objects.equals(coworkingUser.getId(), Long.valueOf(id))) {
				if (coworkingUser.getRole() != Roles.ADMIN) {
					return ResponseEntity
							.status(HttpStatus.FORBIDDEN)
							.build();
				} else {
					userService.deleteUser(Long.valueOf(id));
					return ResponseEntity
							.status(HttpStatus.NO_CONTENT)
							.build();

				}
			} else {
				userService.deleteUser(Long.valueOf(id));
				return ResponseEntity
						.status(HttpStatus.NO_CONTENT)
						.build();
			}
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();
		}
	}



}
