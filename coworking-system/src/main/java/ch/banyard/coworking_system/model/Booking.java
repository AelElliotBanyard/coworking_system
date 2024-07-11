package ch.banyard.coworking_system.model;

import ch.banyard.coworking_system.model.enums.Day;
import ch.banyard.coworking_system.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date date;

	private Day day;

	private Status status;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	private CoworkingUser coworkingUser;

	@JoinColumn(name = "room_id", referencedColumnName = "id")
	@ManyToOne
	private Room room;

	public Booking(Date date, Day day, Status status, CoworkingUser coworkingUser, Room room) {
		this.date = date;
		this.day = day;
		this.status = status;
		this.coworkingUser = coworkingUser;
		this.room = room;
	}

}
