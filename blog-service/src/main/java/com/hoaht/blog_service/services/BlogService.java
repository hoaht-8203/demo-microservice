package com.hoaht.blog_service.services;

import com.hoaht.blog_service.entities.Blog;
import com.hoaht.blog_service.repos.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepo blogRepo;

    public BlogService(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    public List<Blog> listAll() {
        return blogRepo.findAll();
    }

    @Transactional
    public Blog add(Blog blog) {
        return blogRepo.save(blog);
    }
}
