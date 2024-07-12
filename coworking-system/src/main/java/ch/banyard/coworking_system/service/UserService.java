package ch.banyard.coworking_system.service;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.request.RequestUser;
import ch.banyard.coworking_system.model.dto.CoworkingUserDTO;
import ch.banyard.coworking_system.model.enums.Roles;
import ch.banyard.coworking_system.model.mapper.CoworkingUserMapper;
import ch.banyard.coworking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final CoworkingUserMapper userMapper;

	public UserService(UserRepository userRepository, CoworkingUserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public List<CoworkingUserDTO> getAllUsers() {
		return userMapper.mapUsersToDtoList(userRepository.findAll());
	}

	public CoworkingUserDTO getUserById(Long id) {
		return userMapper.mapUserToDTO(userRepository.findById(id).orElseThrow());
	}

	public CoworkingUserDTO createUser(RequestUser requestUser) {
		if (userRepository.findCoworkingUserByUsername(requestUser.getUsername()).isPresent()) {
			throw new IllegalArgumentException("User already exists");
		}
		CoworkingUserDTO userDTO = CoworkingUserDTO.builder()
				.username(requestUser.getUsername())
				.password(requestUser.getPassword())
				.firstName(requestUser.getFirstName())
				.lastName(requestUser.getLastName())
				.blocked(false)
				.role(Roles.MEMBER)
				.build();
		return userMapper.mapUserToDTO(userRepository.save(userMapper.mapUserDtoToEntity(userDTO)));
	}

	public CoworkingUserDTO updateUser(Long id, CoworkingUser user) {
		if (userRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("User does not exist");
		}
		CoworkingUser existingUser = userRepository.findById(id).orElseThrow();
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setBlocked(user.getBlocked());
		existingUser.setRole(user.getRole());
		existingUser.setSalt(user.getSalt());
		existingUser.setPassword(user.getPassword());
		existingUser.setUsername(user.getUsername());
		return userMapper.mapUserToDTO(userRepository.save(existingUser));
	}

	public void deleteUser(Long id) {
		if (userRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("User does not exist");
		}
		userRepository.deleteById(id);
	}

	public CoworkingUser getUserByUsername(String username) {
		return userRepository.findCoworkingUserByUsername(username).orElseThrow();
	}

}
