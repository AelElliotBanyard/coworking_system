package ch.banyard.coworking_system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static ch.banyard.coworking_system.security.WebSecurityConstants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,    // @Security
		jsr250Enabled = true,     // @RolesAllowed
		prePostEnabled = true     // @PreAuthorize, @PostAuthorize
)
public class WebSecurity {
	private static final Logger log = LoggerFactory.getLogger(WebSecurity.class);

	@Autowired
	private CoworkingUserDetailService coworkingUserDetailService;
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}



	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		log.info("cors:filterChain");
		return source;
	}

	public JwtAuthenticationFilter customFilter() throws Exception {
		log.info("sec:customFilter loaded);");
		return new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
	}

	public JwtAuthorizationFilter customFilter1() throws Exception {
		log.info("sec:customFilter1 loaded);");
		return new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager());
	}


	@Bean
	public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(
						authorize ->
								authorize
										.requestMatchers(HttpMethod.POST, SIGN_UP_URL)
										.permitAll()
										.requestMatchers(HttpMethod.GET, "/swagger-ui/**")
										.permitAll()
										.requestMatchers(HttpMethod.GET, "/v3/api-docs/**")
										.permitAll()
										.requestMatchers(HttpMethod.GET, "/swagger")
										.permitAll()
										.anyRequest()
										.authenticated()
				)
				.cors(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable);

		http.addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(customFilter1(), BasicAuthenticationFilter.class);
		log.info("sec:filterChain");
		return http.build();
	}
}
