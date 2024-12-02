package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoReview;
import org.springframework.http.ResponseEntity;

public interface IReviewService {
    public String createReview(DtoReview dtoReview);
}
