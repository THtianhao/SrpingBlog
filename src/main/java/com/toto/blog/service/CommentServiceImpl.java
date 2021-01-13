package com.toto.blog.service;

import com.toto.blog.dao.CommentRepository;
import com.toto.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort createTime = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> byBlogId = commentRepository.findByBlogIdAndParentCommentNull(blogId, createTime);
        eachComment(byBlogId);
        return byBlogId;
    }

    private List<Comment> eachComment(List<Comment> byBlogId) {
        for (Comment comment : byBlogId) {
            List<Comment> childComments = new ArrayList<>();
            addChild(childComments, comment);
            comment.setReplyComments(childComments);
        }
        return null;
    }

    private void addChild(List<Comment> childComments, Comment comment) {
        List<Comment> replyComments = comment.getReplyComments();
        if (replyComments.size() > 0) {
            childComments.addAll(replyComments);
            for (Comment comment1 : replyComments) {

                addChild(childComments, comment1);
            }
        }
    }


    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}

