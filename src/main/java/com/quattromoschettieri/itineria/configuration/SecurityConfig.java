package com.quattromoschettieri.itineria.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // =====================================================
                // PAGINE PUBBLICHE
                // =====================================================

                .requestMatchers(
                    "/",
                    "/contattaci.html",
                    "/faq.html",
                    "/supporto.html"
                ).permitAll()

                // Risorse statiche
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()


                // =====================================================
                // AUTENTICAZIONE
                // =====================================================

                .requestMatchers(
                    "/accedi",
                    "/registrazione",
                    "/login"
                ).permitAll()

                .requestMatchers(HttpMethod.POST, "/utenti")
                    .anonymous()


                // =====================================================
                // AREA PERSONALE
                // =====================================================

                .requestMatchers("/utente/**")
                    .hasAnyRole("USER", "MANAGER", "ADMIN")


                // =====================================================
                // DOCUMENTI PERSONALI
                // =====================================================

                .requestMatchers("/documenti/**")
                    .hasAnyRole("USER", "MANAGER", "ADMIN")


                // =====================================================
                // PREFERITI
                // =====================================================

                .requestMatchers("/api/utenti/**")
                    .hasAnyRole("USER", "MANAGER", "ADMIN")


                // =====================================================
                // RECENSIONI
                // =====================================================

                // Lettura pubblica
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/recensioni/**"
                ).permitAll()

                // Creazione
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/recensioni/**"
                ).hasAnyRole("USER", "MANAGER", "ADMIN")

                // Modifica
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/recensioni/**"
                ).hasAnyRole("USER", "MANAGER", "ADMIN")

                // Eliminazione
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/recensioni/**"
                ).hasAnyRole("USER", "MANAGER", "ADMIN")


                // =====================================================
                // LUOGHI DI INTERESSE
                // =====================================================

                // -------------------------
                // LETTURA PUBBLICA
                // -------------------------

                .requestMatchers(
                    HttpMethod.GET,
                    "/biblioteche/**",
                    "/musei/**",
                    "/ristoranti/**",
                    "/locali/**",
                    "/zoneVerdi/**"
                ).permitAll()


                // -------------------------
                // CREAZIONE
                // -------------------------

                // Il MANAGER può creare un luogo.
                // Il service assegnerà il manager autenticato
                // come proprietario del luogo.
                .requestMatchers(
                    HttpMethod.POST,
                    "/biblioteche/**",
                    "/musei/**",
                    "/ristoranti/**",
                    "/locali/**",
                    "/zoneVerdi/**"
                ).hasAnyRole("MANAGER", "ADMIN")


                // -------------------------
                // MODIFICA
                // -------------------------

                // MANAGER e ADMIN possono modificare.
                // Il service deve verificare che il MANAGER
                // sia effettivamente il manager del luogo.
                .requestMatchers(
                    HttpMethod.PUT,
                    "/biblioteche/**",
                    "/musei/**",
                    "/ristoranti/**",
                    "/locali/**",
                    "/zoneVerdi/**"
                ).hasAnyRole("MANAGER", "ADMIN")


                // -------------------------
                // ELIMINAZIONE
                // -------------------------

                .requestMatchers(
                    HttpMethod.POST,
                    "/biblioteche/*/delete",
                    "/musei/*/delete",
                    "/ristoranti/*/delete",
                    "/locali/*/delete",
                    "/zoneVerdi/*/delete"
                ).hasAnyRole("MANAGER", "ADMIN")


                // =====================================================
                // EVENTI
                // =====================================================

                // Consultazione pubblica
                .requestMatchers(
                    HttpMethod.GET,
                    "/eventi/**"
                ).permitAll()

                // Per ora SOLO ADMIN può creare eventi
                .requestMatchers(
                    HttpMethod.POST,
                    "/eventi/**"
                ).hasRole("ADMIN")

                // SOLO ADMIN può modificare eventi
                .requestMatchers(
                    HttpMethod.PUT,
                    "/eventi/**"
                ).hasRole("ADMIN")

                // SOLO ADMIN può eliminare eventi
                .requestMatchers(
                    HttpMethod.POST,
                    "/eventi/*/delete"
                ).hasRole("ADMIN")


                // =====================================================
                // CITTÀ
                // =====================================================

                // Le città possono essere consultate pubblicamente
                .requestMatchers(
                    HttpMethod.GET,
                    "/citta/**"
                ).permitAll()

                // La gestione delle città è dell'ADMIN
                .requestMatchers(
                    HttpMethod.POST,
                    "/citta/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/citta/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/citta/**"
                ).hasRole("ADMIN")


                // =====================================================
                // GESTIONE UTENTI - ADMIN
                // =====================================================

                // L'ADMIN può gestire gli utenti.
                // L'endpoint POST /utenti per la registrazione
                // è già stato gestito sopra come anonymous.
                .requestMatchers(
                    HttpMethod.GET,
                    "/utenti/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/utenti/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/utenti/**"
                ).hasRole("ADMIN")


                // =====================================================
                // DEFAULT
                // =====================================================

                .anyRequest().denyAll()
            )


            // =========================================================
            // LOGIN
            // =========================================================

            .formLogin(form -> form
                .loginPage("/accedi")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/utente", true)
                .failureUrl("/accedi?errore=true")
                .permitAll()
            )


            // =========================================================
            // LOGOUT
            // =========================================================

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}