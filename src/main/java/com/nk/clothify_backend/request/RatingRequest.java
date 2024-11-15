package com.nk.clothify_backend.request;

import lombok.Data;

@Data
public class RatingRequest {

    private Long productId;
    private Double rating;
}
