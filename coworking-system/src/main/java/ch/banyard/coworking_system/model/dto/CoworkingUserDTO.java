package ch.banyard.coworking_system.model.dto;

import ch.banyard.coworking_system.model.enums.Roles;
import lombok.Builder;

@Builder
public record CoworkingUserDTO(
		Long id,
		String username,
		String password,
		String firstName,
		String lastName,
		Boolean blocked,
		Roles role) {
}
