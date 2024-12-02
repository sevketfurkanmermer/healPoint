package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IReviewController;
import com.proje.healpoint.dto.DtoReview;
import com.proje.healpoint.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/review")
public class ReviewControllerImpl implements IReviewController {
    @Autowired
    private IReviewService reviewService;
    @Override
    @PostMapping(path="/create")
    public ResponseEntity<String> createReview(@RequestBody DtoReview dtoReview) {
        String response = reviewService.createReview(dtoReview);
        return ResponseEntity.ok(response);
    }
}
