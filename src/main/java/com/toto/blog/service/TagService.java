package com.toto.blog.service;

import com.toto.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Tag getTag(Long id);

    Tag saveTag(Tag tag);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag t);

    void deleteTag(Long id);
}
