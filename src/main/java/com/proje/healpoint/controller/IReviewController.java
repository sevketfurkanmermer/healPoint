package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoReview;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReviewController {
    public ResponseEntity<DtoReview> createReview(DtoReview dtoReview);
    public ResponseEntity<List<DtoReview>> getDoctorReviews(String doctorTc);
    public ResponseEntity<List<DtoReview>> getPatientReviews();
}
