package com.rayyau.eshop.security.library.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "common.security")
public class SecurityConfigProperties {
    private String secretKey;
    private Long jwtTtl;
}
