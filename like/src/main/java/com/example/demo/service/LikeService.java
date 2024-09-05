package com.example.demo.service;

import com.example.demo.model.Like;
import com.example.demo.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public Like getLikeByIpAddress(String ipAddress) {
        return likeRepository.findByIpAddress(ipAddress);
    }

    public Like addLike(String ipAddress) {
        Like like = likeRepository.findByIpAddress(ipAddress);
        if (like == null) {
            like = new Like();
            like.setIpAddress(ipAddress);
            like.setCount(1); // 처음 좋아요를 누를 때 count를 1로 설정.
            likeRepository.save(like);
        } else {
            throw new IllegalStateException("You have already liked this item.");
        }
        return like;
    }

    public Like removeLike(String ipAddress) {
        Like like = likeRepository.findByIpAddress(ipAddress);
        if (like != null) {
            likeRepository.delete(like);
        }
        return like;
    }
}
