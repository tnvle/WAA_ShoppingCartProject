package com.online.store.waa3l.service;

import com.online.store.waa3l.domain.Category;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();

    public Category getCategoryById(Integer id);
}
