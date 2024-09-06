package com.example.demo.repository;

import com.example.demo.model.LikeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRecordRepository extends JpaRepository<LikeRecord, Long> {
    LikeRecord findByIpAddress(String ipAddress);
}

//JPA repository about LikeRecord entity.
