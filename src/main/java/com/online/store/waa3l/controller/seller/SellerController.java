package com.online.store.waa3l.controller.seller;

import com.online.store.waa3l.domain.*;
import com.online.store.waa3l.dto.ProductOrder;
import com.online.store.waa3l.dto.ProductReview;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.service.*;
import com.online.store.waa3l.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    ServletContext servletContext;

    @GetMapping("")
    public String home() {
        return "seller/index";
    }

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    OrderService orderService;

    @Autowired
    ReviewService reviewService;

    @ModelAttribute("brands")
    public List<Brand> getBrands() {
        return brandService.getAllBrands();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/products")
    public String getAllProductsBySeller(Model model) {
        User user = userService.getCurrentUser();
        List<Product> products = productService.findAllActiveProducts(user.getId());
        model.addAttribute("products", products);
        return "seller/products";
    }


    @GetMapping("/products/addProduct")
    public String getProductForm(@ModelAttribute("product") Product product) {
        return "seller/productAddForm";
    }


    @PostMapping("/products/save")
    public String addProduct(@ModelAttribute Product product, BindingResult bindingResult, @RequestParam(value = "fileImages", required = false) List<MultipartFile> fileImages) {
        User user = userService.getCurrentUser();
        if (bindingResult.hasErrors()) {
            return "seller/products/addProduct";
        }
        List<Image> images = new ArrayList<>();
        images = saveFiles(fileImages);
        product.setUser(user);
        product.setImages(images);

        productService.save(product);

        productService.sendEmailToFollowers(user.getId());
        return "redirect:/seller/products";
    }

    @GetMapping("/products/edit/product-details/{id}")
    public String getEditProductForm(@PathVariable("id") Long productId, Model model) {
        Product oldProduct = productService.getProductById(productId);
        if (oldProduct != null) {
            model.addAttribute("oldProduct", oldProduct);
            return "seller/edit-product";
        }
        return "seller/products";
    }

    @PostMapping(value = "/products/edit/product-details")
    public String updateProduct(@Valid @ModelAttribute("oldProduct") Product product,
                                BindingResult bindingResult, Model model, @RequestParam(value = "fileImages", required = false) List<MultipartFile> fileImages) {
        User user = userService.getCurrentUser();

        if (bindingResult.hasErrors()) {
            return "seller/edit-product";
        }

        List<Image> images = new ArrayList<>();
        images = saveFiles(fileImages);
        product.setImages(images);
        product.setUser(user);
        productService.save(product);
        return "redirect:/seller/products";
    }


    @PostMapping("/products/delete/product-details/{id}")
    @ResponseBody
    public Response deleteProduct(@PathVariable("id") Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            product.setStatus(false);
        }
        productService.save(product);
        return new Response(true);
    }

    public List<Image> saveFiles(List<MultipartFile> fileImages) {

        List<MultipartFile> files = fileImages;
        List<String> fileNames = new ArrayList<>();
        List<Image> images = new ArrayList<>();

        Resource resource = resourceLoader.getResource("classpath:static/img/product-img/");
        String path = null;
        try {
            path = resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = Util.randomString() + multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                images.add(new Image(fileName));
                File imageFile = new File(path, fileName);
                try {
                    multipartFile.transferTo(imageFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return images;
    }


}
