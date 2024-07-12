package ch.banyard.coworking_system.model.dto;

import ch.banyard.coworking_system.model.enums.Day;
import ch.banyard.coworking_system.model.enums.Status;
import lombok.Builder;

@Builder
public record BookingDTO(
		Long id,
		String name,
		String date,
		Day day,
		Status status,
		CoworkingUserDTO coworkingUserDTO,
		RoomDTO roomDTO
) {
}
