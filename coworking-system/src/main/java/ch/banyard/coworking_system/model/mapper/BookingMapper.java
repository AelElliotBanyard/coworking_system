package ch.banyard.coworking_system.model.mapper;

import ch.banyard.coworking_system.model.Booking;
import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.Room;
import ch.banyard.coworking_system.model.dto.BookingDTO;
import ch.banyard.coworking_system.model.dto.CoworkingUserDTO;
import ch.banyard.coworking_system.model.dto.RoomDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {
	private final CoworkingUserMapper coworkingUserMapper;
	private final RoomMapper roomMapper;

	public BookingMapper(CoworkingUserMapper coworkingUserMapper, RoomMapper roomMapper) {
		this.coworkingUserMapper = coworkingUserMapper;
		this.roomMapper = roomMapper;

	}

	public Booking mapsBookingDtoToEntity(BookingDTO bookingDTO) {
		CoworkingUser coworkingUser = coworkingUserMapper.mapUserDtoToEntity(bookingDTO.coworkingUserDTO());
		Room room = roomMapper.mapRoomDtoToEntity(bookingDTO.roomDTO());
		return new Booking(Date.valueOf(bookingDTO.date()), bookingDTO.day(), bookingDTO.status(), coworkingUser, room);
	}

	public BookingDTO mapBookingToDTO(Booking booking) {
		CoworkingUserDTO userDTO = coworkingUserMapper.mapUserToDTO(booking.getCoworkingUser());
		RoomDTO roomDTO = roomMapper.mapRoomToDTO(booking.getRoom());
		return BookingDTO.builder()
				.id(booking.getId())
				.coworkingUserDTO(userDTO)
				.roomDTO(roomDTO)
				.date(booking.getDate().toString())
				.day(booking.getDay())
				.status(booking.getStatus())
				.build();
	}

	public List<BookingDTO> mapBookingsToDtoList(List<Booking> bookings) {
		return bookings.stream()
				.map(this::mapBookingToDTO)
				.collect(Collectors.toList());
	}

}
