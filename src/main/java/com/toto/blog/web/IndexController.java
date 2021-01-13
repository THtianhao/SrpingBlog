package com.toto.blog.web;

import com.toto.blog.entity.Blog;
import com.toto.blog.service.BlogService;
import com.toto.blog.service.TagService;
import com.toto.blog.service.TypeService;
import com.toto.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommends", blogService.listRecommendBlog(10));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model, @RequestParam String query) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "/blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlog(3));
        return "_fragments :: newblogList";
    }
}
