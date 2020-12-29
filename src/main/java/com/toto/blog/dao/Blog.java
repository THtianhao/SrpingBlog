package com.toto.blog.dao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_blog")

public class Blog {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String content;
    private String flag;
    private Integer views;
    private boolean isAppreciationEnable;
    private boolean isShareStatementEnbale;
    private boolean isCommentEnable;
    private boolean isPublish;
    private boolean isRecomment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type = new Type();

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user = new User();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciationEnable() {
        return isAppreciationEnable;
    }

    public void setAppreciationEnable(boolean appreciationEnable) {
        isAppreciationEnable = appreciationEnable;
    }

    public boolean isShareStatementEnbale() {
        return isShareStatementEnbale;
    }

    public void setShareStatementEnbale(boolean shareStatementEnbale) {
        isShareStatementEnbale = shareStatementEnbale;
    }

    public boolean isCommentEnable() {
        return isCommentEnable;
    }

    public void setCommentEnable(boolean commentEnable) {
        isCommentEnable = commentEnable;
    }

    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean publish) {
        isPublish = publish;
    }

    public boolean isRecomment() {
        return isRecomment;
    }

    public void setRecomment(boolean recomment) {
        isRecomment = recomment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}