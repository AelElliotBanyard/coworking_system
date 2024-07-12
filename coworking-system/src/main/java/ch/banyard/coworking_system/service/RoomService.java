package ch.banyard.coworking_system.service;

import ch.banyard.coworking_system.model.Room;
import ch.banyard.coworking_system.model.dto.RoomDTO;
import ch.banyard.coworking_system.model.mapper.RoomMapper;
import ch.banyard.coworking_system.model.request.RequestRoom;
import ch.banyard.coworking_system.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

	private final RoomRepository roomRepository;
	private final RoomMapper roomMapper;

	public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
		this.roomRepository = roomRepository;
		this.roomMapper = roomMapper;
	}

	public List<RoomDTO> getAllRooms() {
		List<Room> rooms = roomRepository.findAll();
		return roomMapper.mapRoomsToDtoList(rooms);
	}

	public RoomDTO getRoomById(Long id) {
		Room room = roomRepository.findById(id).orElseThrow();
		return roomMapper.mapRoomToDTO(room);
	}

	public RoomDTO createRoom(RequestRoom requestRoom) {
		RoomDTO roomDTO = RoomDTO.builder()
				.name(requestRoom.getName())
				.capacity(requestRoom.getCapacity())
				.description(requestRoom.getDescription())
				.floor(requestRoom.getFloor())
				.shortName(requestRoom.getShortName())
				.type(requestRoom.getType())
				.build();
		Room room = roomMapper.mapRoomDtoToEntity(roomDTO);
		if  (roomRepository.findByName(room.getName()).isPresent()) {
			throw new IllegalArgumentException("Room already exists");
		}
		roomRepository.save(room);
		return roomMapper.mapRoomToDTO(room);
	}

	public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
		Room room = roomMapper.mapRoomDtoToEntity(roomDTO);
		if  (roomRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Room does not exist");
		}
		Room existingRoom = roomRepository.findById(id).orElseThrow();
		existingRoom.setName(room.getName());
		existingRoom.setCapacity(room.getCapacity());
		existingRoom.setDescription(room.getDescription());
		existingRoom.setFloor(room.getFloor());
		existingRoom.setShortName(room.getShortName());
		existingRoom.setType(room.getType());
		roomRepository.save(existingRoom);
		return roomMapper.mapRoomToDTO(existingRoom);
	}

	public void deleteRoom(Long id) {
		if (roomRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Room does not exist");
		}
		roomRepository.deleteById(id);
	}
}
