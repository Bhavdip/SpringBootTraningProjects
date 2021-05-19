package training.spring.boot.mobileapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import training.spring.boot.mobileapp.api.users.service.impl.UserServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	
	private Environment mEnvironment;
	
	private UserServiceImp mUserServiceImp;
	
	private BCryptPasswordEncoder mBCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment environment, UserServiceImp userServiceImp, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.mEnvironment = environment;
		this.mUserServiceImp = userServiceImp;
		this.mBCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//this will allow only IP address of Zuul API Gateway 
		//hasIpAddress we need set IP address of Zuul Server only
		http.authorizeRequests().antMatchers("/**").hasIpAddress(mEnvironment.getProperty("gateway.ip"))
		.and()
		.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(mUserServiceImp, mEnvironment, authenticationManager());
		authenticationFilter.setFilterProcessesUrl(mEnvironment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(mUserServiceImp).passwordEncoder(mBCryptPasswordEncoder);
	}
}
