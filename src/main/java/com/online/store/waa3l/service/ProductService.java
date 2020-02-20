package com.online.store.waa3l.service;

import com.online.store.waa3l.domain.*;
import com.online.store.waa3l.dto.ProductPageDTO;
import com.online.store.waa3l.dto.Response;

import java.util.List;


public interface ProductService {

    public List<Product> findTopProduct();

    public Product save(Product product);

    public Product getProductById(Long id);

    Response review(String username, Long pid, Review review);

    public Response findPaginatedProductsResponse(int numPerPage, int pageNo);

    public Response findPaginatedProductsResponseByCategory(int categoryId, int numPerPage, int pageNo);

    public Response findPaginatedProductsResponseByBrands(Integer[] brandIds,int numPerPage, int pageNo);

    public ProductPageDTO findPaginatedProducts(int numPerPage, int pageNo);

    public ProductPageDTO findPaginatedProductsByCategory(int categoryId, int numPerPage, int pageNo);

    public List<Product> findAllActiveProducts(Long sellerId);

    public void sendEmailToFollowers(Long userId);
}
