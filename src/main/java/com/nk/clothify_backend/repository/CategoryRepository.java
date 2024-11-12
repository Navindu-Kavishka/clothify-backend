package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    public CategoryEntity findByName(String name);

    @Query("SELECT c FROM CategoryEntity c WHERE c.name=:name AND c.parentCategoryEntity.name=:parentCategoryName")
    public CategoryEntity findByNameAndParent(@Param("name") String name,
                                              @Param("parentCategoryName") String parentCategoryName);


}
