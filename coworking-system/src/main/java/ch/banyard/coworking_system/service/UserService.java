package ch.banyard.coworking_system.service;

import ch.banyard.coworking_system.model.dto.CoworkingUserDTO;
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

	public CoworkingUserDTO createUser(CoworkingUserDTO userDTO) {
		if (userRepository.findOneByUsername(userDTO.username()).isPresent()) {
			throw new IllegalArgumentException("User already exists");
		}
		return userMapper.mapUserToDTO(userRepository.save(userMapper.mapUserDtoToEntity(userDTO)));
	}

	public CoworkingUserDTO updateUser(Long id, CoworkingUserDTO userDTO) {
		if (userRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("User does not exist");
		}
		return userMapper.mapUserToDTO(userRepository.save(userMapper.mapUserDtoToEntity(userDTO)));
	}

	public void deleteUser(Long id) {
		if (userRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("User does not exist");
		}
		userRepository.deleteById(id);
	}

}
