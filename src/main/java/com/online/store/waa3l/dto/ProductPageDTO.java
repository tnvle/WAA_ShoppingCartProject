package com.online.store.waa3l.dto;

import com.online.store.waa3l.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageDTO {
    private List<Integer> pageNumbers = new ArrayList<>();
    private Integer pageNum;
    private Integer itemsPerPage;
    private Integer totalCount;
    private List<ProductDTO> productList = new ArrayList<>();
    private boolean activateProductList = false;
}
