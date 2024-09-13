package com.hoaht.blog_service.repos;

import com.hoaht.blog_service.entities.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends MongoRepository<Blog, ObjectId> {

}
