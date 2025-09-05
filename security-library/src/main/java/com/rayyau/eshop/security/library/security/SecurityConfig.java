package com.rayyau.eshop.security.library.security;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.List;

@AutoConfiguration
@EnableWebSecurity
@AllArgsConstructor
@ConditionalOnClass(name = "org.springframework.security.authentication.AuthenticationManager")
@EnableConfigurationProperties(SecurityConfigProperties.class)
@Slf4j
//@EnableJpaRepositories(basePackages = "com.rayyau.eshop.security.library.repository")
//@EntityScan(basePackages = "com.rayyau.eshop.security.library.dto")
//@ComponentScan(basePackages = {"com.rayyau.eshop.security.library"})
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final SecurityConfigProperties configProperties;
    private static final String PAYPAL_STATUS_PATH = "/webhook/paypal/status/**";

    @Bean
    public JwtUtil jwtUtil() {
        final SecretKey secretKey = Keys.hmacShaKeyFor(configProperties.getSecretKey().getBytes());
        return new JwtUtil(secretKey);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // CORS for only localhost:3000 and only the webhook status path
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfgRestricted = new CorsConfiguration();
        cfgRestricted.setAllowedOrigins(List.of("http://localhost:3000"));
        cfgRestricted.setAllowedMethods(List.of("GET","POST","OPTIONS"));
        cfgRestricted.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));
        cfgRestricted.setAllowCredentials(true);
        cfgRestricted.setMaxAge(3600L);

        // Fallback (deny by not registering broader pattern)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PAYPAL_STATUS_PATH, cfgRestricted);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        log.info("init filter chain");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        configurer -> configurer
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/webhook/paypal").permitAll()
                                .requestMatchers("/webhook/paypal/status/**").permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthenticationProvider authenticationProvider() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
