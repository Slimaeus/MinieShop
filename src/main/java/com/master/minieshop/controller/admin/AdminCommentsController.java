package com.master.minieshop.controller.admin;

import com.master.minieshop.entity.Comment;
import com.master.minieshop.entity.Product;
import com.master.minieshop.entity.Promotion;
import com.master.minieshop.service.CommentService;
import com.master.minieshop.service.ProductService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/comments")
public class AdminCommentsController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductService productService;

    @GetMapping({ "index", "" })
    public String showAllCurrentComment(Model model) {
        var currentComments = commentService.getAll();
        model.addAttribute("comments", currentComments);
        return "admin/comments/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
        model.addAttribute("comment", comment);
        return "admin/comments/details";
    }

    @GetMapping("/create")
    public String createComment(Model model) {
        model.addAttribute("comment", new Comment());
        return "admin/comments/create";
    }

    @PostMapping("/create")
    public String createComment(@ModelAttribute("comment") Comment comment) {
        commentService.save(comment);
        return "redirect:/admin/comments";
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
        model.addAttribute("comment", comment);
        return "admin/comments/edit";
    }

    @PostMapping("/edit/{id}")
    public String editComment(@PathVariable("id") String id, @ModelAttribute("comment") Comment comment) {
        commentService.save(comment);
        return "redirect:/admin/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
        model.addAttribute("comment", comment);
        return "admin/comments/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") String id) {
        commentService.deleteById(id);
        return "redirect:/admin/comments";
    }

    @GetMapping("/details/{id}/products")
    public String getItemInComment(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
        var item = productService.getAll().stream().filter(m -> m.getComments().equals(comment));
        model.addAttribute("products", item);
        return "admin/comments/comment-products";
    }

}
