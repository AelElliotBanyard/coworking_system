package ch.banyard.coworking_system.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import ch.banyard.coworking_system.model.CoworkingUser;

import static ch.banyard.coworking_system.security.WebSecurityConstants.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
static Logger log = Logger.getLogger("JwtAuthenticationFilter");
	private JwtTokenUtil jwtTokenUtil;
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		log.info("authProvider loaded"+authenticationManager);
		Assert.notNull(authenticationManager, "AuthenticationManager cannot be null");
		this.authenticationManager = authenticationManager;
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		log.info("JwtAuthentication.doFilter:authorization");

		super.doFilter(request, response, chain);

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res) throws AuthenticationException {
		try {
			log.info("attemptAuthentication");
			//    AuthenticationManager authenticationManager1= SecurityContextHolder.getContext().getAuthentication();
			CoworkingUser coworkingUser = new ObjectMapper().readValue(req.getInputStream(), CoworkingUser.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							coworkingUser.getUsername(),
							coworkingUser.getPassword(),
							new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
	                                        HttpServletResponse res,
	                                        FilterChain chain,
	                                        Authentication auth) throws IOException, ServletException {
		log.info("successfulAuthentication");
		User user = ((User) auth.getPrincipal());
		log.info("user : " + user.toString());

		String token = JWT.create()
				.withSubject(((User) auth.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.sign(HMAC512(SECRET.getBytes()));
		// token in body
		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		out.print(new ObjectMapper().writeValueAsString(new TokenResponse(token)));
		// token in header
		//      res.setHeader(HEADER_STRING, TOKEN_PREFIX+token);
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
	// POJO to represent the response body
	@Data
	@AllArgsConstructor
	private class TokenResponse {
		private String token;
	}
}
