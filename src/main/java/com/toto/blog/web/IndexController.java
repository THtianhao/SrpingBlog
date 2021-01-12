package com.toto.blog.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;

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

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @PostMapping("/search/{query}")
    public String search(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model, @RequestParam String query) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }
}
