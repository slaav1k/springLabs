package labs.electicstoreadmin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class ManageCategoriesController {
  
  private final CategoryService categoryService;

  @GetMapping
  public String categoriesAdmin(Model model) {
    model.addAttribute("categories", categoryService.findAll());
    return "categoriesAdmin";
  }
  
  @PostMapping
  public String addCategory(Category category) {
    categoryService.addCategory(category);
    return "redirect:/admin/categories";
  }
  
}
