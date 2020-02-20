package com.online.store.waa3l.service.impl;

import com.online.store.waa3l.domain.Brand;
import com.online.store.waa3l.repository.BrandRepository;
import com.online.store.waa3l.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.getAllBrands();
    }

    @Override
    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }
}
