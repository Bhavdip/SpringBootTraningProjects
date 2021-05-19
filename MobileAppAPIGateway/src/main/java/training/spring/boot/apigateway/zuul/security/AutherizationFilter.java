package training.spring.boot.apigateway.zuul.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

/*
 * This is response for validate the authorization token request
 * Any request coming to API Gateway needs to pass the authorization
 * token in order to access the resource.
 * 
 */
public class AutherizationFilter extends  BasicAuthenticationFilter {

	private Environment mEnvironment;
	
	public AutherizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.mEnvironment = environment;
	}
	
	@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
				throws IOException, ServletException {
		
		String authorizationHeader = request.getHeader(mEnvironment.getProperty("authorization.token.header.name"));
		/**
		 * For example authorizationHeader =>
		 * Bearer eyJhbGciOiJIUzUxMiJ9.tL0FmkA
		 */
		if(authorizationHeader == null || !authorizationHeader.startsWith(mEnvironment.getProperty("authorization.token.header.prefix"))) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken passwordAuthenticationToken = getAuthentication(request);
		
		SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(mEnvironment.getProperty("authorization.token.header.name"));
		if(authorizationHeader == null) {
			return null;
		}
		/**
		 * Remove Bearer and extract token from the authorizationHeader
		 */
		String requestToken = authorizationHeader.replace(mEnvironment.getProperty("authorization.token.header.prefix"), "");
		
		/**
		 * Use JWT Parser to find userId from token and secret key if found means valid token if not found then not valid token / token expired
		 */
		String userId = Jwts.parser()
				.setSigningKey(mEnvironment.getProperty("token.secret"))
				.parseClaimsJws(requestToken)
				.getBody()
				.getSubject();
		
		if(userId == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
		
	}
}
