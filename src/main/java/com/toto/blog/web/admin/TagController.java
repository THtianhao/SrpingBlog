package com.toto.blog.web.admin;

import com.toto.blog.entity.Tag;
import com.toto.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Types;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 进入页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                              Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 新增
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags_input";
    }

    /**
     * 提交新增tag
     * @param tag
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            bindingResult.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if (bindingResult.hasErrors()) {
            return "admin/tags";
        }
        Tag tag2 = tagService.saveTag(tag);
        if (tag2 == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 修改tag
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags_input";
    }

    /**
     * 提交修改tag
     * @param tag
     * @param bindingResult
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String postTag(@Valid Tag tag, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            bindingResult.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if (bindingResult.hasErrors()) {
            return "admin/tags_input";
        }
        Tag tag2 = tagService.updateTag(id, tag);
        if (tag2 == null) {
            redirectAttributes.addFlashAttribute("message", "修改失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除tag
     * @param id
     * @param redirectAttributes
     * @return
     */

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
