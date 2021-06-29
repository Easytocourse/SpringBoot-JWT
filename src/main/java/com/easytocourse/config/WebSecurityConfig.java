package com.easytocourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.easytocourse.UserUtility.JwtUtil;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserDetailsService userDetailsService;

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	  @Autowired
	    private JwtFilter jwtFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * // csrf().disable() is need to disble when we are not using csrf token in
		 * requests
		 * http.csrf().disable().authorizeRequests().antMatchers("/users").authenticated
		 * ().anyRequest().permitAll().and().formLogin()
		 * .usernameParameter("email").defaultSuccessUrl("/users").permitAll().and().
		 * logout().logoutSuccessUrl("/") .permitAll();
		 */

		
		// this is type for rest service with basic authetincation with out form type authentication
//		http.csrf().disable().authorizeRequests().antMatchers("/Users").authenticated()
//				.and().httpBasic();
		
		
		http.csrf().disable().authorizeRequests()
      //  .antMatchers("/").hasAnyAuthority("Batsmen", "Bowler", "Allrounder", "Wicketkeeper", "Umpire")
        .antMatchers("/ground").permitAll()
        .antMatchers("/batting").hasAnyAuthority("Batsmen", "Allrounder", "Wicketkeeper")
		.antMatchers("/keeping").hasAuthority("Wicketkeeper")
		.antMatchers("/bowling").hasAnyAuthority("Bowler", "Allrounder")
		.antMatchers("/umpiring").hasAuthority("Umpire")
		.anyRequest().authenticated().and().httpBasic().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
}
