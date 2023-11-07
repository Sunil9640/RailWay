package com.smart.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
	

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImp();
	}
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider getDaoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

		return daoAuthenticationProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDaoAuthProvider());
	}
	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.csrf().disable(). authorizeRequests()
	 * .antMatchers("/admin/**").hasRole("ADMIN")
	 * .antMatchers("/user/**").permitAll() .antMatchers("/**").permitAll().and()
	 * .formLogin() .loginProcessingUrl("/login") .defaultSuccessUrl("/dash")
	 * .and();
	 * 
	 * }
	 */
	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/user").hasRole("USER")
        .antMatchers("/**").permitAll()
        .anyRequest().authenticated()
        .and()

        .formLogin()
        .loginPage("/loginform")
        .permitAll().loginProcessingUrl("/dologin")
        .defaultSuccessUrl("/user/dash")
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
	
	
}
