package ch.banyard.coworking_system.service;

import ch.banyard.coworking_system.model.Room;
import ch.banyard.coworking_system.model.dto.RoomDTO;
import ch.banyard.coworking_system.model.mapper.RoomMapper;
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

	public RoomDTO createRoom(RoomDTO roomDTO) {
		Room room = roomMapper.mapRoomDtoToEntity(roomDTO);
		if  (roomRepository.findById(room.getId()).isPresent()) {
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
		roomRepository.save(room);
		return roomMapper.mapRoomToDTO(room);
	}

	public void deleteRoom(Long id) {
		if (roomRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Room does not exist");
		}
		roomRepository.deleteById(id);
	}
}
