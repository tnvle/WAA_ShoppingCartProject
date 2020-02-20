package com.online.store.waa3l.controller;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.Brand;
import com.online.store.waa3l.domain.Category;
import com.online.store.waa3l.domain.Product;
import com.online.store.waa3l.dto.ProductPageDTO;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.service.BrandService;
import com.online.store.waa3l.service.CategoryService;
import com.online.store.waa3l.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
public class ShopController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @GetMapping("/shop")
    public  String defaultShop(Model model){
        loadProducts(productService.findPaginatedProducts(6, 1),6, 1, model);

        return "shop";
    }
    @GetMapping("/shop/{category_id}")
    public  String categoryShop(@PathVariable("category_id") Integer categoryId, Model model){
        loadProducts(productService.findPaginatedProductsByCategory(categoryId, 6, 1),6, 1, model);

        return "shop";
    }
    @GetMapping("/shop/{numPerPage}/{pageNo}")
    public  String shop(@PathVariable("numPerPage") Integer numPerPage, @PathVariable("pageNo") Integer pageNo, Model model){
        loadProducts(productService.findPaginatedProducts(numPerPage, pageNo),numPerPage, pageNo, model);
        return "shop";
    }
    @GetMapping("/rest/shop/{numPerPage}/{pageNo}")
    public  @ResponseBody
    Response
    shopREST(@PathVariable("numPerPage") Integer numPerPage, @PathVariable("pageNo") Integer pageNo, Model model){
        return new Response(productService.findPaginatedProducts(numPerPage, pageNo));
    }

    @GetMapping("/rest/shop/{category_id}/{numPerPage}/{pageNo}")
    public  @ResponseBody
    Response
    shopRESTByCategory(@PathVariable("category_id") Integer categoryId,@PathVariable("numPerPage") Integer numPerPage, @PathVariable("pageNo") Integer pageNo, Model model){
        return productService.findPaginatedProductsResponseByCategory(categoryId, numPerPage, pageNo);
    }
    @GetMapping("/rest/shop/brand/{brands}/{numPerPage}/{pageNo}")
    public  @ResponseBody
    Response
    shopRESTByBrands(@PathVariable("brands") Integer[] brandIds,@PathVariable("numPerPage") Integer numPerPage, @PathVariable("pageNo") Integer pageNo, Model model){
        return productService.findPaginatedProductsResponseByBrands(brandIds, numPerPage, pageNo);
    }

    private void loadProducts(ProductPageDTO allProductsPage, Integer numPerPage, Integer pageNo, Model model){
        List<Category> allCategories = categoryService.getAllCategories();
        List<Brand> allBrands = brandService.getAllBrands();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("allBrands", allBrands);
        model.addAttribute("numPerPage", numPerPage);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("startIndex", 1 + (pageNo-1)*numPerPage);
        int endIndex = allProductsPage.getTotalCount() > pageNo*numPerPage ? pageNo*numPerPage : allProductsPage.getTotalCount();
        model.addAttribute("endIndex", endIndex);
        model.addAttribute("listCount", allProductsPage.getProductList().size());
        model.addAttribute("totalCount", allProductsPage.getTotalCount());
        model.addAttribute("pageNumbers", allProductsPage.getPageNumbers());
        model.addAttribute("productList", allProductsPage.getProductList());
    }
}
