package com.feedup.feedup.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feedup.feedup.dto.GlobalCommentResponse;
import com.feedup.feedup.service.CommentService;

@RestController
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/all")
    public List<GlobalCommentResponse> getAllComments() {
        return commentService.getAllComments();
    }
}
