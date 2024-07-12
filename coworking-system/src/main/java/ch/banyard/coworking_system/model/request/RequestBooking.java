package ch.banyard.coworking_system.model.request;

import ch.banyard.coworking_system.model.Room;
import ch.banyard.coworking_system.model.enums.Day;
import ch.banyard.coworking_system.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBooking {
	private String name;

	private Date date;

	private Day day;

	private Long room;
	private Status status;
}
