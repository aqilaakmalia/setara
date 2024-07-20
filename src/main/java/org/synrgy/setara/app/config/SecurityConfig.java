package org.synrgy.setara.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.synrgy.setara.security.filter.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] SWAGGER_WHITELIST = {
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/swagger-resources/**",
    "/swagger-resources"
  };

  private static final String[] AUTH_WHITELIST = {
    "/api/v1/auth/sign-in"
  };

  private final JwtAuthenticationFilter jwtAuthFilter;

  private final AuthenticationProvider authProvider;

  private final ObjectMapper mapper;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(withDefaults())
      .authenticationProvider(authProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .formLogin(withDefaults())

      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.POST, AUTH_WHITELIST).permitAll()
        .requestMatchers(SWAGGER_WHITELIST).permitAll()
        .anyRequest().authenticated());

    return http.build();
  }

}
