package com.nk.clothify_backend.controller;


import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.request.CreateProductRequest;
import com.nk.clothify_backend.response.ApiResponse;
import com.nk.clothify_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {

        productService.deleteProduct(productId);
        ApiResponse res =new ApiResponse();
        res.setMessage("product deleted successfully...");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct() throws ProductException {
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req,@PathVariable Long productId)
        throws ProductException{

        Product product = productService.updateProduct(productId, req);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }


}
