package com.nk.clothify_backend.request;

import com.nk.clothify_backend.model.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    private String title;
    private String description;
    private Double price;
    private Double discountedPrice;
    private int discountPercentage;
    private int quantity;
    private String brand;
    private String color;
    private Set<Size> size=new HashSet<>();
    private String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;


}
