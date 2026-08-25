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

            .authorizeHttpRequests(auth -> auth


                // =====================================================
                // RISORSE STATICHE
                // =====================================================

                .requestMatchers(
                    "/",
                    "/index.html",
                    "/supporto",
                    "/faq",
                    "/contattaci"
                ).permitAll()

                // Ricerca globale (luoghi ed eventi)
                .requestMatchers(
                    HttpMethod.GET,
                    "/ricerca"
                ).permitAll()

                // Risorse statiche
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()



                // =====================================================
                // LOGIN / REGISTRAZIONE
                // =====================================================

                .requestMatchers(
                    "/accedi",
                    "/registrazione",
                    "/login"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/utenti"
                ).permitAll()


                // =====================================================
                // AREA PERSONALE
                // =====================================================

                .requestMatchers("/utente/**")
                    .hasAnyRole(
                        "USER",
                        "MANAGER",
                        "ADMIN"
                    )



                // =====================================================
                // DOCUMENTI PERSONALI
                // =====================================================

                .requestMatchers("/documenti/**")
                    .hasAnyRole(
                        "USER",
                        "MANAGER",
                        "ADMIN"
                    )



                // =====================================================
                // PREFERITI
                // =====================================================

                .requestMatchers("/api/utenti/**")
                    .hasAnyRole(
                        "USER",
                        "MANAGER",
                        "ADMIN"
                    )



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
                ).hasAnyRole(
                    "USER",
                    "MANAGER",
                    "ADMIN"
                )


                // Modifica
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/recensioni/**"
                ).hasAnyRole(
                    "USER",
                    "MANAGER",
                    "ADMIN"
                )


                // Eliminazione
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/recensioni/**"
                ).hasAnyRole(
                    "USER",
                    "MANAGER",
                    "ADMIN"
                )



                // =====================================================
                // GESTIONE UTENTI - ADMIN
                // =====================================================

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
                // CREAZIONE / MODIFICA LUOGHI
                // =====================================================

                .requestMatchers(
                    HttpMethod.POST,

                    "/biblioteche/**",
                    "/musei/**",
                    "/ristoranti/**",
                    "/locali/**",
                    "/zoneVerdi/**",
                    "/parchi/**"
                ).hasAnyRole(
                    "MANAGER",
                    "ADMIN"
                )


                .requestMatchers(
                    HttpMethod.PUT,

                    "/biblioteche/**",
                    "/musei/**",
                    "/ristoranti/**",
                    "/locali/**",
                    "/zoneVerdi/**",
                    "/parchi/**"
                ).hasAnyRole(
                    "MANAGER",
                    "ADMIN"
                )


                .requestMatchers(
                    HttpMethod.DELETE,

                    "/biblioteche/**",
                    "/musei/**",
                    "/ristoranti/**",
                    "/locali/**",
                    "/zoneVerdi/**",
                    "/parchi/**"
                ).hasAnyRole(
                    "MANAGER",
                    "ADMIN"
                )



                // =====================================================
                // EVENTI - MODIFICA SOLO ADMIN
                // =====================================================

                .requestMatchers(
                    HttpMethod.POST,
                    "/eventi/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    HttpMethod.PUT,
                    "/eventi/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    HttpMethod.DELETE,
                    "/eventi/**"
                ).hasRole("ADMIN")



                // =====================================================
                // CITTÀ - MODIFICA SOLO ADMIN
                // =====================================================

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
                // TUTTE LE PAGINE VISUALIZZABILI SONO PUBBLICHE
                // =====================================================

                /*
                 * Questo comprende:
                 *
                 * /
                 * /eventi
                 * /contatti
                 * /biblioteche
                 * /musei
                 * /ristoranti
                 * /locali
                 * /parchi
                 * /zoneVerdi
                 * /luoghi/...
                 * /faq
                 * ecc.
                 *
                 * Quindi navigare nel sito NON richiede login.
                 */

                .requestMatchers(
                    HttpMethod.GET,
                    "/**"
                ).permitAll()



                // =====================================================
                // DEFAULT
                // =====================================================

                /*
                 * Tutte le altre richieste non previste
                 * vengono bloccate.
                 */

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

                .defaultSuccessUrl(
                    "/utente",
                    true
                )

                .failureUrl(
                    "/accedi?errore=true"
                )

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