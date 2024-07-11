package ch.banyard.coworking_system.security;

import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CoworkingUserDetailService implements UserDetailsService {
	private UserRepository userRepository;

	@Autowired
	public void UserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final CoworkingUser coworkingUser = userRepository.findOneByUsername(username).get();
		if (coworkingUser == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails user = User
				.withUsername(coworkingUser.getUsername())
				.password(coworkingUser.getPassword())
				.roles(String.valueOf(coworkingUser.getRole()))
				.build();

		return user;
	}

}
