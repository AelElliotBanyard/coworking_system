package ch.banyard.coworking_system.model;

import ch.banyard.coworking_system.model.enums.Day;
import ch.banyard.coworking_system.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDateTime date;

	private Day day;

	private Status status;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	private User user;

	@JoinColumn(name = "room_id", referencedColumnName = "id")
	@ManyToOne
	private Room room;

}
