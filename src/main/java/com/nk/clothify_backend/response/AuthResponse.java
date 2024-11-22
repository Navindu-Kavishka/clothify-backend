package com.nk.clothify_backend.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String jwt;
    private String message;
}
