package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoReview;

import java.util.List;

public interface IReviewService {
    public DtoReview createReview(DtoReview dtoReview);
    public List<DtoReview> getDoctorReviews(String doctorTc);
    public List<DtoReview> getPatientReviews();
}
