package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoReview {
    private Long Review_id;
    private String comments;
    private int points;
}
