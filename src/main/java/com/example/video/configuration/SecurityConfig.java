package com.example.video.configuration;


import com.example.video.security.CustomUserDetailsService;
import com.example.video.security.JwtAuthenticationEntryPoint;
import com.example.video.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
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
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors() /* */
                .and()/* */
                .csrf()/* */
                .disable()/* */
                .exceptionHandling()/* */
                .authenticationEntryPoint(unauthorizedHandler)/* */
                .and()/* */
                .sessionManagement()/* */
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)/* */
                .and()/* */
                .authorizeRequests()/* */
                .antMatchers("/", /* */
                        "/favicon.ico", /* */
                        "/**/*.png", /* */
                        "/**/*.jpeg", /* */
                        "/**/*.gif", /* */
                        "/**/*.ico", /* */
                        "/**/*.svg", /* */
                        "/**/*.jpg", /* */
                        "/**/*.mov", /* */
                        "/**/*.mp4", /* */
                        "/**/*.m4a", /* */
                        "/**/*.3gp", /* */
                        "/**/*.html", /* */
                        "/**/*.css", /* */
                        "/**/*.mp3", /* */
                        "/**/*.js")/* */
                .permitAll()/* */
                .antMatchers("/api/v*/file/**")/* */
                .permitAll()/* */
                .antMatchers("/api/v*/auth/**")/* */
                .permitAll()/* */
                .anyRequest()/* */
                .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
