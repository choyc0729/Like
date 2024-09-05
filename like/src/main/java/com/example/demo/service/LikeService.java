package com.example.demo.service;

import com.example.demo.model.Like;
import com.example.demo.model.LikeRecord;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.LikeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeRecordRepository likeRecordRepository;

    public Like getLike() {
        return likeRepository.findById(1L).orElseGet(() -> {
            Like like = new Like();
            like.setId(1L);
            return like;
        });
    }

    public Like addLike(String ipAddress) {
        LikeRecord existingRecord = likeRecordRepository.findByIpAddress(ipAddress);
        if (existingRecord != null) {
            throw new IllegalStateException("You have already liked this item.");
        }

        Like like = getLike();
        like.setCount(like.getCount() + 1);
        likeRepository.save(like);

        LikeRecord newRecord = new LikeRecord();
        newRecord.setIpAddress(ipAddress);
        likeRecordRepository.save(newRecord);

        return like;
    }


    public Like removeLike(String ipAddress) {
        LikeRecord existingRecord = likeRecordRepository.findByIpAddress(ipAddress);
        if (existingRecord == null) {
            throw new IllegalStateException("You have not liked this item.");
        }

        Like like = getLike();
        like.setCount(like.getCount() - 1);
        likeRepository.save(like);

        likeRecordRepository.delete(existingRecord);

        return like;
    }

    public boolean hasLiked(String ipAddress) {
        return likeRecordRepository.findByIpAddress(ipAddress) != null;
    }

}
