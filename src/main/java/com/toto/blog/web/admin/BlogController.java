package com.toto.blog.web.admin;

import com.toto.blog.entity.Blog;
import com.toto.blog.entity.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs_input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blogQuery, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs ::bloglist";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(RedirectAttributes attributes, Blog blog, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1 = blogService.saveBlog(blog);

        if (blog1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return REDIRECT_LIST;
    }

}
