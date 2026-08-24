package com.quattromoschettieri.itineria.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth

                // Pagine pubbliche
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/contattaci.html",
                    "/faq.html",
                    "/supporto.html"
                ).permitAll()

                // Login e registrazione: solo utenti non autenticati
                .requestMatchers(
                    "/accedi.html",
                    "/registrazione.html"
                ).anonymous()

                // Risorse statiche
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // Registrazione: solo utenti non autenticati
                .requestMatchers(HttpMethod.POST, "/utenti")
                    .anonymous()

                // Login
                .requestMatchers("/login")
                    .anonymous()

                // Area utente
                .requestMatchers("/utente/**")
                    .hasAnyRole("USER", "MANAGER", "ADMIN")

                // Gestione luoghi da parte di manager e admin
                .requestMatchers("/manager/**")
                    .hasAnyRole("MANAGER", "ADMIN")

                // Area amministratore
                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                // Documenti: solo utenti autenticati
                .requestMatchers("/documenti/**")
                    .hasAnyRole("USER", "MANAGER", "ADMIN")

                // Per ora lasciamo pubblici gli endpoint non ancora definiti
                .anyRequest().permitAll()
            )

            .formLogin(form -> form
                .loginPage("/accedi.html")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/area-personale.html", true)
                .failureUrl("/accedi.html?errore=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html")
                .permitAll()
            );

        return http.build();
    }

    //TODO: sistemare endpoint una volta che si definiscono le pagine per manager e admin
}