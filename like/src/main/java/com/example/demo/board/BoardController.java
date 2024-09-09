package com.example.demo.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

/*import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("boardList", boardList);
        return "index";
    }

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
}
*/