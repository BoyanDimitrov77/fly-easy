package com.fly.easy.flyeasy.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    DataSource dataSource;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            .authorizeRequests()

                .antMatchers("/register/**").anonymous()
                .antMatchers("users/resetPassword").permitAll()
                .antMatchers("users/**").hasRole("USER")
                .antMatchers("airline/createAirlineRecord").hasRole("ADMIN")
                .antMatchers("airline/uploadAirlineLogo/**").hasRole("ADMIN")
                .antMatchers("flight/create/createFlightRecord/**").hasRole("ADMIN")
                .antMatchers("flight/all").hasAnyRole("ADMIN","USER")
                .antMatchers("flight/**").hasAnyRole("USER")
                .antMatchers("booking/**").hasAnyRole("USER")
                .antMatchers("hotel/createHotel").hasRole("ADMIN")
                .antMatchers("hote/**").hasRole("USER")
    
                    .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .httpBasic()
                    .and()
                .formLogin().loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                    .and()
                .logout()
                    .logoutUrl("/my/logout")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/my/index")
                    .invalidateHttpSession(true)
                    .and()
                .csrf().disable();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(getCustomAuthProvider());
    } 
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    protected AuthenticationProvider getCustomAuthProvider() {
      return customAuthenticationProvider;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/register");
    }
}
