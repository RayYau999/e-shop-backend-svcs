package com.rayyau.eshop.login.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.security")
public class SecurityConfigProperties {
    private String secretKey;
    private Long jwtTtl;
}
