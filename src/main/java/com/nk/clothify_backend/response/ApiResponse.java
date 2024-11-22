package com.nk.clothify_backend.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private boolean status;
    private String message;
}
