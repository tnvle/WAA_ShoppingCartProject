package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {
    @Query(value = "SELECT b FROM Brand b")
    public List<Brand> getAllBrands();
}
