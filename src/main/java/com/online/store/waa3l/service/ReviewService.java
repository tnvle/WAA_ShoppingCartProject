package com.online.store.waa3l.service;

import com.online.store.waa3l.domain.Review;
import com.online.store.waa3l.dto.Response;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews();

    Response approveReview(Long reviewId);

    Review getReviewById(Long rid);
}
