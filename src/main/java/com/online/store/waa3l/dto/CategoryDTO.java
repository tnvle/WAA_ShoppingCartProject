package com.online.store.waa3l.dto;

import com.online.store.waa3l.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Integer id;

    private String name;

    private String description;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}
