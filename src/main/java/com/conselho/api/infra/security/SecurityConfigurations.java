package com.conselho.api.infra.security;

import com.conselho.api.model.usuario.UsuarioRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/auth/login/**",
            "/api/auth/cadastrar/**"
    };
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/api/conselhos",
            "/api/pre_conselhos",
    };

    public static final String[] ENDPOINTS_PEDAGOGICO = {
            "/api/alunos/**",
            "/api/professores/**",
            "/api/turmas/**",
            "/api/conselho/**"
    };

    public static final String[] ENDPOINTS_ALUNO = {
            "/api/pre_conselho/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(authorize -> authorize
                        // Swagger e OpenAPI liberados
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()

                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()

                        // Permitir o GET de conselhos e pré-conselhos para todos
                        .requestMatchers(HttpMethod.GET, "/api/conselhos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/pre_conselhos").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/conselhos").hasRole("PEDAGOGICO")
                        .requestMatchers(ENDPOINTS_PEDAGOGICO).hasRole(String.valueOf(UsuarioRole.PEDAGOGICO))
                        .requestMatchers(ENDPOINTS_ALUNO).hasRole("ALUNO")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
