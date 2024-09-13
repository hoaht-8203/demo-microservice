package com.hoaht.blog_service.controller;

import com.hoaht.blog_service.entities.Blog;
import com.hoaht.blog_service.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping(value = "")
    public List<Blog> listBlog() {
        return blogService.listAll();
    }

    @PostMapping(value = "/create")
    public Blog addBlog(@RequestBody Blog blog) {
        return blogService.add(blog);
    }
}
