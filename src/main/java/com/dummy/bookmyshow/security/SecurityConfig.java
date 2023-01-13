package com.dummy.bookmyshow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailService customuserDetailService;
	@Autowired
	private JwtFilter jwtFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customuserDetailService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return BCryptEncoder.getInstance();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers("/swagger-ui.html").permitAll()

			// cast controller
			.antMatchers("/v1/addCast").hasRole("ADMIN")
			.antMatchers("/v1/getCast").permitAll()

			// generic controller
			.antMatchers("/v1/bookSeats").hasRole("NORMAL")
			.antMatchers("/v1/getAvailabilityOnAScreen").permitAll()
			.antMatchers("/v1/getMoviesByCity").permitAll()
			.antMatchers("/v1/getScreensShowingMovie").permitAll()
			.antMatchers("/v1/getSupportedCities").permitAll()

			// movie controller
			.antMatchers("/v1/registerMovie").hasRole("ADMIN")

			// operation-handler
			.antMatchers("/actuator/health").hasRole("ADMIN")
			.antMatchers("/actuator/health/**").hasRole("ADMIN")
			.antMatchers("/actuator/info").hasRole("ADMIN")

			// screen controller
			.antMatchers("/v1/registerScreen").hasRole("ADMIN")

			// seat matrix controller
			.antMatchers("/v1/addCustomSeatMatrix").hasRole("ADMIN")
			.antMatchers("/v1/addDefaultSeatMatrix").hasRole("ADMIN")

			// theater controller
			.antMatchers("/v1/registerTheater").hasRole("ADMIN")

			// user controller
			.antMatchers("/v1/addUser").permitAll()
			.antMatchers("/v1/getUserDetails").hasAnyRole("ADMIN", "NORMAL")
			.antMatchers("/v1/login").permitAll()

			// concession controller
			.antMatchers("/v1/addConcession").hasRole("ADMIN")
			.antMatchers("/v1/getConcessionDetails").hasRole("ADMIN")
			.antMatchers("/v1/editConcessionDetails").hasRole("ADMIN")
			.antMatchers("/v1/orderConcession").hasAnyRole("ADMIN", "NORMAL")

			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/favicon.ico").permitAll()
				 
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
