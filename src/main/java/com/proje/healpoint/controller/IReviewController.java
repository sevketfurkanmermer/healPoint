package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoReview;
import org.springframework.http.ResponseEntity;

public interface IReviewController {
    public ResponseEntity<String> createReview(DtoReview dtoReview);
}
