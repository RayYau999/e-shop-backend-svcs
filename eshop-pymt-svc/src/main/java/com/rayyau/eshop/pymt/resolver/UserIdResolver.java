package com.rayyau.eshop.pymt.resolver;

import com.rayyau.eshop.pymt.annotation.UserId;
import com.rayyau.eshop.security.library.dto.UserEntity;
import com.rayyau.eshop.security.library.repository.UserRepository;
import com.rayyau.eshop.security.library.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
public class UserIdResolver implements HandlerMethodArgumentResolver {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String userName = jwtUtil.extractUsername(token);
            UserEntity userEntity = userRepository.findByUsername(userName)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return userEntity.getId(); // Should return String userId
        }
        return null;
    }
}
