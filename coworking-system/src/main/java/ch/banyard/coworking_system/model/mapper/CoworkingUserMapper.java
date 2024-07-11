package ch.banyard.coworking_system.model.mapper;

import ch.banyard.coworking_system.model.Booking;
import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.CoworkingUserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoworkingUserMapper {

	public CoworkingUser mapUserDtoToEntity(CoworkingUserDTO userDTO) {
		CoworkingUser coworkingUser = new CoworkingUser();
		coworkingUser.setId(userDTO.id());
		coworkingUser.setUsername(userDTO.username());
		coworkingUser.setPassword(userDTO.password());
		return coworkingUser;
	}
	public CoworkingUserDTO mapUserToDTO(CoworkingUser coworkingUser) {
		return new CoworkingUserDTO(coworkingUser.getId(), coworkingUser.getUsername(), coworkingUser.getPassword(),coworkingUser.getFirstName(),coworkingUser.getLastName(), coworkingUser.getBlocked(), coworkingUser.getRole());
	}

	public List<CoworkingUserDTO> mapUsersToDtoList(List<CoworkingUser> users) {
		return users.stream()
				.map(this::mapUserToDTO)
				.collect(Collectors.toList());
	}
}