package com.master.minieshop.rest;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.entity.Comment;
import com.master.minieshop.entity.Product;
import com.master.minieshop.service.CommentService;
import com.master.minieshop.service.ProductService;
import com.master.minieshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/comments")
public class ApiCommentsController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductService productService;
    @GetMapping("{productId}/{content}/{rate}")
    public void post(
                     @PathVariable("productId") Integer productId,
                     @PathVariable("content") String content,
                     @PathVariable("rate") Integer rate,
                     Principal principle) {
        AppUser user = userService.findByUsername(principle.getName());
        Product product = productService.getById(productId).orElseThrow();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setRate(rate);
        comment.setUser(user);
        comment.setProduct(product);
        commentService.save(comment);
    }

}
