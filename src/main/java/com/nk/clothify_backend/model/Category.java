package com.nk.clothify_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;


    @Size(max = 50)
    private String name;

    private Category parentCategoryEntity;

    private int level;



}
