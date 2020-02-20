package com.online.store.waa3l.service.impl;

import com.online.store.waa3l.domain.*;
import com.online.store.waa3l.dto.ProductDTO;
import com.online.store.waa3l.dto.ProductPageDTO;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.repository.OrderRepository;
import com.online.store.waa3l.repository.ProductRepository;
import com.online.store.waa3l.repository.ReviewRepository;
import com.online.store.waa3l.repository.UserRepository;
import com.online.store.waa3l.service.EmailService;
import com.online.store.waa3l.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EmailService emailService;

    @Override
    public List<Product> findTopProduct() {
        return productRepository.findTopTenAvailableProducts();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Response review(String username, Long pid, Review review) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (!userOptional.isPresent())
            throw new RuntimeException("Username not exists");

        User user = userOptional.get();
        review.setUser(user);

        Optional<Product> product = productRepository.findById(pid);

        if (!product.isPresent())
            throw new RuntimeException("Product not exists");

        Product p = product.get();

        List<Order> orders = orderRepository.getAllOrdersByUser(user.getId());
        if (orders == null || orders.isEmpty()) {
            return new Response("400", "You have not yet had any order to make review", null);
        } else {
            boolean hasOrderThisProduct = false;
            for (Order order : orders) {
                List<OrderLine> orderLines = order.getOrderLines();
                List<Long> productIds = orderLines.stream().map(e -> e.getProduct().getId()).collect(Collectors.toList());
                if (productIds.contains(p.getId())) {
                    hasOrderThisProduct = true;
                    break;
                }
            }

            if (!hasOrderThisProduct)
                return new Response("400", "You have not yet had any order to make review", null);

        }

        List<Review> reviews = p.getReviews();
        reviews.add(review);
        p.setReviews(reviews);
        productRepository.save(p);
        return new Response(true);


    }

    @Override
    public Response findPaginatedProductsResponse(int numPerPage, int pageNo) {
        return new Response(findPaginatedProducts(numPerPage, pageNo));
    }

    @Override
    public Response findPaginatedProductsResponseByCategory(int categoryId, int numPerPage, int pageNo) {
        ProductPageDTO productPageDTO = new ProductPageDTO();
        productPageDTO.setPageNum(pageNo);
        productPageDTO.setItemsPerPage(numPerPage);
        List<Product> productList = productRepository.findAllByCategory(categoryId);
        Page<Product> productPage = new PageImpl<Product>(productList, PageRequest.of(pageNo - 1, numPerPage), productList.size());
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            productPageDTO.setPageNumbers(pageNumbers);
        }
        productPageDTO.setActivateProductList(true);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productPage.getContent().forEach(e -> {
            productDTOS.add(new ProductDTO(e));
        });
        productPageDTO.setProductList(productDTOS);
        productPageDTO.setTotalCount(productRepository.findAll().size());
        return new Response(productPageDTO);
    }

    @Override
    public Response findPaginatedProductsResponseByBrands(Integer[] brandIds, int numPerPage, int pageNo) {
        ProductPageDTO productPageDTO = new ProductPageDTO();
        productPageDTO.setPageNum(pageNo);
        productPageDTO.setItemsPerPage(numPerPage);
        List<Product> productList = productRepository.findAllByBrands(brandIds);
        Page<Product> productPage = new PageImpl<Product>(productList, PageRequest.of(pageNo - 1, numPerPage), productList.size());
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            productPageDTO.setPageNumbers(pageNumbers);
        }
        productPageDTO.setActivateProductList(true);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productPage.getContent().forEach(e -> {
            productDTOS.add(new ProductDTO(e));
        });
        productPageDTO.setProductList(productDTOS);
        productPageDTO.setTotalCount(productRepository.findAll().size());
        return new Response(productPageDTO);
    }

    @Override
    public ProductPageDTO findPaginatedProducts(int numPerPage, int pageNo) {
        ProductPageDTO productPageDTO = new ProductPageDTO();
        productPageDTO.setPageNum(pageNo);
        productPageDTO.setItemsPerPage(numPerPage);
        Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNo - 1, numPerPage));
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            productPageDTO.setPageNumbers(pageNumbers);
        }
        productPageDTO.setActivateProductList(true);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productPage.getContent().forEach(e -> {
            productDTOS.add(new ProductDTO(e));
        });
        productPageDTO.setProductList(productDTOS);
        productPageDTO.setTotalCount(productRepository.findAll().size());
        return productPageDTO;
    }

    @Override
    public ProductPageDTO findPaginatedProductsByCategory(int categoryId, int numPerPage, int pageNo) {
        ProductPageDTO productPageDTO = new ProductPageDTO();
        productPageDTO.setPageNum(pageNo);
        productPageDTO.setItemsPerPage(numPerPage);
        List<Product> productList = productRepository.findAllByCategory(categoryId);
        Page<Product> productPage = new PageImpl<Product>(productList, PageRequest.of(pageNo - 1, numPerPage), productList.size());
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            productPageDTO.setPageNumbers(pageNumbers);
        }
        productPageDTO.setActivateProductList(true);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productPage.getContent().forEach(e -> {
            productDTOS.add(new ProductDTO(e));
        });
        productPageDTO.setProductList(productDTOS);
        productPageDTO.setTotalCount(productRepository.findAll().size());
        return productPageDTO;
    }

    public List<Product> findAllActiveProducts(Long sellerId) {
        return productRepository.findAllActiveProducts(sellerId);
    }

    @Override
    public void sendEmailToFollowers(Long userId) {
        User user = userRepository.findById(userId).get();
        List<User> followers = user.getFollowers();
        for (User follower : followers) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dear ").append(follower.getFirstName()).append(" ").append(follower.getLastName()).append(",").append("<br/><br/>");
            stringBuilder.append("There is one new product on our store. We have best discounts for our first 10 buyers. <br/><br/>");
            stringBuilder.append("Please take a look at %s <br/><br/> Thank you so much.<br/><br/>Waa3L Team");

            emailService.sendEmail(stringBuilder.toString(), "New Product Added", Arrays.asList(follower.getEmail()));
        }
    }
}
