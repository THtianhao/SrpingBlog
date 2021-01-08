package com.toto.blog.web.admin;

import com.toto.blog.entity.Type;
import com.toto.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 进入页面，展示分页
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 新增数据
     * @param model
     * @return
     */

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types_input";
    }

    /**
     * 新增数据提交
     * @param type
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            bindingResult.rejectValue("name", "nameError", "不能重复添加分类");
        }
        if (bindingResult.hasErrors()) {
            return "admin/types_input";
        }
        Type type2 = typeService.saveType(type);
        if (type2 == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types_input";
    }


    /**
     * 修改数据提交
     * @param type
     * @param bindingResult
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            bindingResult.rejectValue("name", "nameError", "不能重复添加分类");
        }
        if (bindingResult.hasErrors()) {
            return "admin/types_input";
        }
        Type type2 = typeService.updateType(id,type);
        if (type2 == null) {
            redirectAttributes.addFlashAttribute("message", "更新失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除数据
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
