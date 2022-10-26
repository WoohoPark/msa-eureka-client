package com.example.demo.global.config;

import com.example.demo.global.filter.HeaderAuthorizationFilter;
import com.example.demo.global.filter.LoginAuthenticationFilter;
import com.example.demo.global.utils.CookieProvider;
import com.example.demo.global.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder bCryptPasswordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
//	private final RefreshTokenServiceImpl refreshTokenServiceImpl;
	private final CookieProvider cookieProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Custom Login Authentication 필터를 사용함
		LoginAuthenticationFilter loginAuthenticationFilter =
				new LoginAuthenticationFilter(authenticationManagerBean(), jwtTokenProvider,cookieProvider);
		loginAuthenticationFilter.setFilterProcessesUrl("/login");

		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().anyRequest().permitAll();

		http.addFilter(loginAuthenticationFilter);
		http.addFilterBefore(new HeaderAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}