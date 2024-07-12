package ch.banyard.coworking_system.model.request;

import ch.banyard.coworking_system.model.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestRoom {

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private String shortName;

	private Integer floor;

	private RoomType type;

	private Integer capacity;

}
