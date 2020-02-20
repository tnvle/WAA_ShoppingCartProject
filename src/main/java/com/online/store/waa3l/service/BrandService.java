package com.online.store.waa3l.service;

import com.online.store.waa3l.domain.Brand;

import java.util.List;

public interface BrandService {
    public List<Brand> getAllBrands();

    public Brand getBrandById(Integer id);
}
