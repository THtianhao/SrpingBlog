package com.toto.blog.service;

import com.toto.blog.NotFoundException;
import com.toto.blog.dao.BlogRepository;
import com.toto.blog.entity.Blog;
import com.toto.blog.vo.BlogQuery;
import org.hibernate.query.criteria.internal.predicate.BooleanAssertionPredicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (blog.getTitle() != null && !blog.getTitle().isEmpty())
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle()));
                if (blog.getTypeId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.<String>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("isRecommend"), blog.isRecommend()));
                }
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b);
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}