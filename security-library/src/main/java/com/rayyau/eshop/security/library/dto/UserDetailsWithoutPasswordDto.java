package com.rayyau.eshop.security.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserDetailsWithoutPasswordDto implements Serializable {

    private static final long serialVersionUID = 5L;

    private String username;

    private Long id;

    private String role;

    private String fullName;
}
