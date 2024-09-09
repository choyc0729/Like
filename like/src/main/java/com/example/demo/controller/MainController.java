package com.example.demo.controller;

import com.example.demo.model.Like;
import com.example.demo.service.LikeService;
import com.example.demo.board.Board;
import com.example.demo.board.BoardForm;
import com.example.demo.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private BoardService boardService;

    // Like methods
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Like like = likeService.getLike();
        String clientIp = getClientIp(request);
        boolean hasLiked = likeService.hasLiked(clientIp);
        System.out.println("Ip address : "+clientIp);
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("hasLiked", hasLiked);
        model.addAttribute("like", like);
        model.addAttribute("boardList", boardList);

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

    // Board methods
    @GetMapping("/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

    @GetMapping("/board/create")
    public String boardCreate(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        return "board_form";
    }

    @PostMapping("/board/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }
        try {
            this.boardService.create(boardForm.getSubject(), boardForm.getContent());
        } catch (Exception e) {
            bindingResult.reject("createFailed", "게시글 생성에 실패했습니다.");
            return "board_form";
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
