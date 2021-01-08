package com.toto.blog.dao;

import com.toto.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

}
