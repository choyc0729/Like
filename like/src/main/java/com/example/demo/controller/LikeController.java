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

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/")
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

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
