package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.online.store.waa3l.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p")
    List<Product> findTopProduct();

    @Query(value = "select * from Product where category_id = :category_id and status = true ", nativeQuery = true)
    List<Product> findAllByCategory(@Param("category_id") Integer categoryId);

    @Query(value = "select * from Product where brand_id in (:brands) and status = true ", nativeQuery = true)
    List<Product> findAllByBrands(@Param("brands") Integer[] brandIds);

    @Query(value="SELECT * FROM Product WHERE STATUS = 1 ORDER BY NAME DESC LIMIT 12", nativeQuery = true)
    public List<Product> findTopTenAvailableProducts();

//    public Page<Product> findAll(Pageable pageable);
    // List<Product> findToProduct();

    @Query(value ="select * from Product  where seller_id = :seller_id and status = true", nativeQuery = true)
    List<Product> findAllActiveProducts(@Param("seller_id") Long sellerId);
}
