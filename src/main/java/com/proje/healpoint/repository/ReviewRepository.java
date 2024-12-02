package com.proje.healpoint.repository;

import com.proje.healpoint.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews,Long> {

}
