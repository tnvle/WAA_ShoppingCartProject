package com.online.store.waa3l.service.impl;

import com.online.store.waa3l.domain.Review;
import com.online.store.waa3l.domain.User;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.repository.ReviewRepository;
import com.online.store.waa3l.service.EmailService;
import com.online.store.waa3l.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EmailService emailService;

    @Override
    public List<Review> getAllReviews() {
        return (List<Review>) reviewRepository.findAll();
    }

    @Override
    public Response approveReview(Long reviewId) {

       Optional<Review> reviewOptional =  reviewRepository.findById(reviewId);
       if (!reviewOptional.isPresent())
           throw new RuntimeException("Review not exists");
       Review review = reviewOptional.get();

       review.setShow(true);
       reviewRepository.save(review);

        User user = review.getUser();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("<br/><br/>");
        stringBuilder.append("Your product review on 3L* Store has been approved by Admin.<br/><br/>");
        stringBuilder.append("It's now available on our store. Please check it out at %s. <br/><br/> Thank you so much<br/><br/>Waa3L Team.");

        emailService.sendEmail(stringBuilder.toString(), "Product Review Approved", Arrays.asList(user.getEmail()));
        return new Response(true);

    }

    @Override
    public Review getReviewById(Long rid) {
        Optional<Review> reviewOptional =  reviewRepository.findById(rid);
        if(reviewOptional.isPresent())
            return reviewOptional.get();
        return null;
    }
}
