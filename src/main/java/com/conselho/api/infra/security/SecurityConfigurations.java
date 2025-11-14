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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    public static final String[] ENDPOINTS_PEDAGOGICO_ADMIN = {
            //Entidades
            "api/usuario/**",
            "/api/aluno/**",
            "/api/professor/**",
            "/api/pedagogico/**",
            "/api/supervisor/**",
            "/api/weg/**",

            //Pre-Conselho
            "/api/preConselho/**",
            "/api/preConselhoAmbienteEnsino/**",
            "/api/preConselhoPedagogico/**",
            "/api/preConselhoSupervisao/**",
            "/api/preConselhoProfessor/**",

            //Feedbacks
            "/api/conselhoAlunosFeedbacks",
            "/api/conselhoTurmasFeedbacks",

            "/api/turmas/**",
            "/api/conselho/**",
            "/api/aluno-turma/**",
            "/api/unidadeCurricular",
            "/api/ucProfessor"
    };


    public static final String[] ENDPOINTS_ALUNO = {
            "/api/pre_conselho/**",
            "/api/preConselhoAmbienteEnsino/**",
            "/api/preConselhoPedagogico/**",
            "/api/preConselhoSupervisao/**",
            "/api/preConselhoProfessor/**"
    };

    public static final String[] ENDPOINTS_WEG = {
            "/api/feedbackAluno/**"
    };

    public static final String[] ENDPOINTS_SWAGGER = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ENDPOINTS_SWAGGER).permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(ENDPOINTS_PEDAGOGICO_ADMIN).hasAnyRole("PEDAGOGICO", "ADMIN")
                        .requestMatchers(ENDPOINTS_ALUNO).hasRole("ALUNO")
                        .requestMatchers(ENDPOINTS_WEG).hasRole("WEG")

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("POST","GET", "PUT", "PATCH", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
