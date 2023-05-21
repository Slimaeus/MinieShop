package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Comment;
import com.master.minieshop.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseEntityService<Comment, String, CommentRepository> {
    public CommentService(CommentRepository repository) {
        super(repository);
    }
}
