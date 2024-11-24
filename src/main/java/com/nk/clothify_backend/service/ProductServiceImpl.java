package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.CategoryEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.repository.CategoryRepository;
import com.nk.clothify_backend.repository.ProductRepository;
import com.nk.clothify_backend.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product createProduct(CreateProductRequest req) {

        CategoryEntity topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel==null){
            CategoryEntity topLevelCategory = new CategoryEntity();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        CategoryEntity secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());

        if (secondLevel==null) {
            CategoryEntity secondLevelCategory = new CategoryEntity();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategoryEntity(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        CategoryEntity thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());

        if (thirdLevel==null) {
            CategoryEntity thirdLevelCategory=new CategoryEntity();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategoryEntity(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercentage(req.getDiscountPercentage());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategoryEntity(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        ProductEntity savedProductEntity = productRepository.save(modelMapper.map(product, ProductEntity.class));

        return modelMapper.map(savedProductEntity, Product.class);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        
        productRepository.delete(productEntity);
        return "Product deleted Successfully..."; 
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {

        Product productById = findProductById(productId);
        productById.setQuantity(req.getQuantity());

        return modelMapper.map(
                productRepository.save(
                        modelMapper.map(
                                productById, ProductEntity.class)
                ), Product.class);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<ProductEntity> productById = productRepository.findById(productId);

        if (productById.isPresent()){
            return modelMapper.map(productById.get(), Product.class);
        }
        throw new ProductException("Product not found with ID: " + productId);

    }

    @Override
    public List<Product> findAllProducts() throws ProductException {
        List<ProductEntity> productEntities = productRepository.findAll();
        if (productEntities.isEmpty()) {
            throw new ProductException("No products found");
        }
        return productEntities.stream()
                .map(productEntity -> modelMapper.map(productEntity, Product.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        List<ProductEntity> productEntities = productRepository.findByCategoryName(category);
        return productEntities.stream()
                .map(productEntity -> modelMapper.map(productEntity, Product.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        List<ProductEntity> productEntityList= productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);

        if (colors != null && !colors.isEmpty()) {
           productEntityList=productEntityList.stream().filter((p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))))
                   .collect(Collectors.toList());
        }
        if (stock != null) {
            if(stock.equals("is_stock")){
                productEntityList=productEntityList.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                productEntityList=productEntityList.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), productEntityList.size());

        List<Product> products = new ArrayList<>();
        productEntityList.forEach(productEntity -> products.add(modelMapper.map(productEntity, Product.class)));

        List<Product> pageContent= products.subList(startIndex,endIndex);

        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,productEntityList.size());

        return filteredProducts;
    }
}