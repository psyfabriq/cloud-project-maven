package ru.podstavkov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ru.podstavkov.service.impl.CustomAuthFailureHandler;
import ru.podstavkov.service.impl.CustomUserDetailsService;
import ru.podstavkov.service.impl.JwtAuthenticationEntryPoint;
import ru.podstavkov.service.impl.JwtAuthenticationFilter;
import ru.podstavkov.utils.annotation.LogBefore;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	 @Autowired
	 private CustomAuthFailureHandler customAuthenticationFailureHandler;

	 @Autowired
	 private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(); 
	}
	

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	 
	    http
	      .authorizeRequests()
	        .antMatchers("/css/**", "/js/**", "/img/**", "/webjars/**", "/401", "/403", "/404", "/500").permitAll()
	        .antMatchers("/**").permitAll().antMatchers("/api/auth/**").permitAll()
	        .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
	        .antMatchers(HttpMethod.GET, "/api/users/**").authenticated()
	      .and()
	      .formLogin().loginPage("/login")
	    //  .failureHandler(customAuthenticationFailureHandler)
	      .and()
	      .logout()
	      .logoutUrl("/logout")
	      .deleteCookies("remember-me")
	      .logoutSuccessUrl("/login").permitAll(false).and().rememberMe();
	      //.and()
	     // .exceptionHandling()
	      //.authenticationEntryPoint(unauthorizedHandler)
	      //.and()
	     // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	    http.httpBasic();
		http.csrf().disable();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@LogBefore
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

	}
	
	


}