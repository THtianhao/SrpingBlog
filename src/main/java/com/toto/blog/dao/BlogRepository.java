package com.toto.blog.dao;

import com.toto.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("from Blog  where recommend = true")
    List<Blog> findTop(Pageable pageable);


    @Query("from Blog where title like ?1 or content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);
}
