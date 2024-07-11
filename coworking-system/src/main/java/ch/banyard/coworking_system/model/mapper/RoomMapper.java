package ch.banyard.coworking_system.model.mapper;

import ch.banyard.coworking_system.model.Room;
import ch.banyard.coworking_system.model.dto.RoomDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

	public Room mapRoomDtoToEntity(RoomDTO roomDTO) {
		Room room = new Room();
		room.setId(roomDTO.id());
		room.setCapacity(roomDTO.capacity());
		room.setName(roomDTO.name());
		room.setFloor(roomDTO.floor());
		room.setShortName(roomDTO.shortName());
		room.setType(roomDTO.type());
		room.setDescription(roomDTO.description());
		return room;
	}
	public RoomDTO mapRoomToDTO(Room room) {
		return new RoomDTO(room.getId(), room.getName(), room.getDescription(), room.getCapacity(), room.getFloor(), room.getShortName(), room.getType());
	}

	public List<RoomDTO> mapRoomsToDtoList(List<Room> rooms) {
		return rooms.stream()
				.map(this::mapRoomToDTO)
				.collect(Collectors.toList());
	}
}
