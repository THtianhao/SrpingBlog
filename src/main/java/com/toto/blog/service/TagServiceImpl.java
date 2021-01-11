package com.toto.blog.service;

import com.toto.blog.NotFoundException;
import com.toto.blog.dao.TagRepository;
import com.toto.blog.entity.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable page) {
        return tagRepository.findAll(page);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    private List<Long> stringCoverList(String ids) {
        List<Long> list = new ArrayList<Long>();
        if (ids != null && !ids.isEmpty()) {
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(Long.valueOf(idarray[i]));
            }
            return list;
        }
        return list;
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(stringCoverList(ids));
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag t) {
        Tag one = tagRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("没有找到tag");
        }
        BeanUtils.copyProperties(t, one);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
