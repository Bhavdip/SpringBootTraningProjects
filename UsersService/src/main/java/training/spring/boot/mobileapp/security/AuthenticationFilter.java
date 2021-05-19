package training.spring.boot.mobileapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import training.spring.boot.mobileapp.api.users.service.impl.UserServiceImp;
import training.spring.boot.mobileapp.model.LoginRequestModel;
import training.spring.boot.mobileapp.model.shared.UserDto;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private UserServiceImp mUserServiceImp;
	
	private Environment mEnvironment;
	
	public AuthenticationFilter(UserServiceImp userServiceImp, Environment environment, AuthenticationManager authenticationManager) {
		this.mUserServiceImp = userServiceImp;
		this.mEnvironment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			//ObjectMapper use to map the value of user name & password into LoginRequestModel
			LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String userName = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDetails = mUserServiceImp.getUserDetailsByEmail(userName);
		
		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(mEnvironment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, mEnvironment.getProperty("token.secret")).compact();
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
		response.addHeader("expirationTime", mEnvironment.getProperty("token.expiration_time"));
	}
}
