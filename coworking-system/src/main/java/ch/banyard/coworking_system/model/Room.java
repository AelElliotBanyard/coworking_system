package ch.banyard.coworking_system.model;

import ch.banyard.coworking_system.model.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private String shortName;

	private Integer floor;

	private RoomType type;

	private Integer capacity;

	public Room(String name, String description, String shortName, Integer floor, RoomType type, Integer capacity) {
		this.name = name;
		this.description = description;
		this.shortName = shortName;
		this.floor = floor;
		this.type = type;
		this.capacity = capacity;
	}
}
