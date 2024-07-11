package ch.banyard.coworking_system.model.dto;

import lombok.Builder;
import ch.banyard.coworking_system.model.enums.RoomType;

@Builder
public record RoomDTO(
		Long id,
		String name,
		String description,
		Integer capacity,
		Integer floor,
		String shortName,
		RoomType type
) {
}
