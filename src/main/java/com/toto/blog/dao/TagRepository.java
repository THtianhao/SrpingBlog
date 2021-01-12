package com.toto.blog.dao;

import com.toto.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query("from Tag")
    List<Tag> findTop(Pageable pageable);
}
