package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.POST,"/donate/**").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.PUT,"/users").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/request/{\\d+}").permitAll()
                .antMatchers(HttpMethod.GET, "/request/findDonateByIdRequest/").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/request").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/request/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/request/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
