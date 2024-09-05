package com.example.demo.controller;

import com.example.demo.model.Like;
import com.example.demo.model.LikeRecord;
import com.example.demo.repository.*;
import com.example.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
//사용자의 좋아요 및 싫어요 처리
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/")
    //index: get으로 현재 좋아요 상태 조회
    public String index(HttpServletRequest request, Model model) {
        Like like = likeService.getLike();
        String clientIp = getClientIp(request);
        boolean hasLiked = likeService.hasLiked(clientIp);

        System.out.println("Client IP: " + clientIp);
        System.out.println("Has liked: " + hasLiked);
        System.out.println("Like count: " + (like != null ? like.getCount() : 0));

        model.addAttribute("hasLiked", hasLiked);
        model.addAttribute("like", like);

        return "index";
    }




    @PostMapping("/like")
    //like : post로 좋아요 추가
    public String like(HttpServletRequest request, Model model) {
        String clientIp = getClientIp(request);
        try {
            Like like = likeService.addLike(clientIp);
            model.addAttribute("like", like);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/unlike")
    //unlike : delete like with post
    public String unlike(HttpServletRequest request, Model model) {
        String clientIp = getClientIp(request);
        try {
            Like like = likeService.removeLike(clientIp);
            model.addAttribute("like", like);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
    //get client`s ip address
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
