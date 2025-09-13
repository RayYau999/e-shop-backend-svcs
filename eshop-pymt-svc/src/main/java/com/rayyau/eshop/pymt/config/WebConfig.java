package com.rayyau.eshop.pymt.config;

import com.rayyau.eshop.pymt.resolver.UserIdResolver;
import com.rayyau.eshop.security.library.repository.UserRepository;
import com.rayyau.eshop.security.library.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new UserIdResolver(jwtUtil, userRepository));
        resolvers.add(new UserIdResolver(jwtUtil, userRepository));
    }
}