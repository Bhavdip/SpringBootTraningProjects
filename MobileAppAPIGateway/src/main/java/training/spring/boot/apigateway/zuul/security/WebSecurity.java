package training.spring.boot.apigateway.zuul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	
	private final Environment mEnvironment;
	
	@Autowired
	public WebSecurity(Environment environment) {
		this.mEnvironment = environment;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Disable cross sites
		http.csrf().disable();
		//Disable Frame options
		http.headers().frameOptions().disable();
		
		/***
		 * In API Gateway some of HTTP Request may not require authorization token
		 * to access the web service. For example Login, SignUp it should allow to 
		 * access with out token hence we need to tell spring security to allow them
		 * 
		 */
		http.authorizeRequests()
		.antMatchers(mEnvironment.getProperty("api.url.users.actuator.path")).permitAll()
		.antMatchers(mEnvironment.getProperty("api.url.actuator.path")).permitAll()
		.antMatchers(mEnvironment.getProperty("api.url.h2console.path")).permitAll()
		.antMatchers(HttpMethod.POST, mEnvironment.getProperty("api.url.login.path")).permitAll()
		.antMatchers(HttpMethod.POST, mEnvironment.getProperty("api.url.registration.path")).permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(getAutherizationFilter());
		
		/**
		 * Below statement will tell spring do not create a session for API request
		 * because it will cache the authorize token into cookies and allow API call
		 * without passing token. To prevent this we use STATELESS mode of session creation.
		 * Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
		 */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	private AutherizationFilter getAutherizationFilter() throws Exception{
		AutherizationFilter authenticationFilter = new AutherizationFilter(authenticationManager(), mEnvironment);
		return authenticationFilter;
	}
}
