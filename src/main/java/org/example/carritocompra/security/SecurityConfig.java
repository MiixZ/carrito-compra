package org.example.carritocompra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager
            .createUser(User.builder()
            .username("user")
            .password(passwordEncoder().encode("user"))
            .roles("USER")
            .build());
        manager
            .createUser(User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests
                .requestMatchers(
                    "/",
                    "/web/catalogo",
                    "/product/**",
                    "/cart/**",
                    "/user/**",
                    "/productApi/**",
                    "/web/productosPrecio",
                    "/web/login"
                )
                .permitAll()
                .requestMatchers(
                    "/chartApi/**",
                    "/productApi/**",
                    "/web/carrito"
                )
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(
                    "/web/productos",
                    "/adminApi/**",
                    "/h2-console/**",
                    "/web/formulario"
                )
                .hasRole("ADMIN");
        })
        .formLogin(formLogin -> formLogin
            .loginPage("/web/login").permitAll()
            .defaultSuccessUrl("/web/catalogo", false))
        .logout(logout -> logout
            .logoutUrl("/web/logout")
            .logoutSuccessUrl("/login?logout").permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                    .accessDeniedPage("/web/catalogo"))
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**", "/product/**", "/chart/**", "/web/**", "/web/login"))
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}