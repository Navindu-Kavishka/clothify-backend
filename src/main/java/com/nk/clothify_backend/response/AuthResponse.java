package com.nk.clothify_backend.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String message;
}
