package com.toto.blog.service;

import com.toto.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag getTag(Long id);

    Tag saveTag(Tag tag);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag t);

    void deleteTag(Long id);
}
