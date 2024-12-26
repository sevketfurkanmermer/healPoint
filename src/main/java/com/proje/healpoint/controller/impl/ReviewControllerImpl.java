package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IReviewController;
import com.proje.healpoint.dto.DtoReview;
import com.proje.healpoint.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/review")
public class ReviewControllerImpl implements IReviewController {
    @Autowired
    private IReviewService reviewService;
    @Override
    @PostMapping(path="/create")
    public ResponseEntity<DtoReview> createReview(@RequestBody DtoReview dtoReview) {
        DtoReview response = reviewService.createReview(dtoReview);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-doctor/{doctorTc}")
    @Override
    public ResponseEntity<List<DtoReview>> getDoctorReviews(@PathVariable(name = "doctorTc") String doctorTc) {
        List<DtoReview> reviews = reviewService.getDoctorReviews(doctorTc);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/list-patient")
    @Override
    public ResponseEntity<List<DtoReview>> getPatientReviews() {
        List<DtoReview> myReviews = reviewService.getPatientReviews();
        return ResponseEntity.ok(myReviews);
    }
}
