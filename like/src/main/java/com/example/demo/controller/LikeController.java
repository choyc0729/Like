package com.example.demo.controller;

import com.example.demo.model.Like;
import com.example.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        Like like = likeService.getLikeByIpAddress(clientIp);
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
        likeService.removeLike(clientIp);
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
