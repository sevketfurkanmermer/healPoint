package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoReview;
import org.springframework.http.ResponseEntity;

public interface IReviewService {
    public DtoReview createReview(DtoReview dtoReview);

}
