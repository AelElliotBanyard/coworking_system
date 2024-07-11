package ch.banyard.coworking_system.model;

import ch.banyard.coworking_system.model.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coworking_user")
public class CoworkingUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Email
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private Boolean blocked;

	private Roles role;

	public CoworkingUser(String username, String password, String firstName, String lastName, Boolean blocked, Roles role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.blocked = blocked;
		this.role = role;
	}

}
