package ch.banyard.coworking_system.controller;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.CoworkingUserDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<CoworkingUserDTO>> getUsers() {
		List<CoworkingUserDTO> users = userService.getAllUsers();
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CoworkingUserDTO> getUser(@PathVariable String id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
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
	public ResponseEntity<CoworkingUserDTO> createUser(@RequestBody CoworkingUserDTO userDTO) {
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
	public ResponseEntity<CoworkingUserDTO> updateUser(@PathVariable String id, @RequestBody CoworkingUserDTO userDTO, @AuthenticationPrincipal CoworkingUser coworkingUser) {
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
	public ResponseEntity<Void> deleteUser(@PathVariable String id, @AuthenticationPrincipal CoworkingUser coworkingUser) {
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
