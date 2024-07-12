package ch.banyard.coworking_system.security;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoworkingUserDetailService implements UserDetailsService {
	private UserRepository userRepository;

	@Autowired
	public void UserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userRepository.findCoworkingUserByUsername(username).isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		CoworkingUser coworkingUser = userRepository.findCoworkingUserByUsername(username).get();
		if (coworkingUser == null) {
			throw new UsernameNotFoundException(username);
		} else {
			return User
					.withUsername(coworkingUser.getUsername())
					.password(coworkingUser.getPassword())
					.roles(String.valueOf(coworkingUser.getRole()))
					.build();
		}

	}

}
