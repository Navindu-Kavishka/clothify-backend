package com.nk.clothify_backend.request;


import lombok.Data;

@Data
public class ReviewRequest {

    private Long productId;
    private String review;
}
