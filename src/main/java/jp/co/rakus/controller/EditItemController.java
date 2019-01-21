package jp.co.rakus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.rakus.domain.Category;
import jp.co.rakus.domain.Item;
import jp.co.rakus.form.ItemForm;
import jp.co.rakus.service.CategoryService;
import jp.co.rakus.service.ItemService;

@Controller
@Transactional
@RequestMapping("/editItem")
public class EditItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public ItemForm setUpForm() {
		return new ItemForm();
	}

	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam("id") Integer id,Model model) {
		
		Item item = itemService.findById(id);
		Category category = categoryService.findNameAllById(item.getCategory());
		
		model.addAttribute("item", item);
		model.addAttribute("category", category);
		
		// 親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);

		return "edit";
	}
	
	/**
	 * プルダウンで親カテゴリーが変更された際に、表示させる子カテゴリーのプルダウンの内容を変更する.
	 * 
	 * @param value
	 *            親カテゴリーのID
	 * @return 親カテゴリーに紐づく子カテゴリーの情報.
	 */
	@RequestMapping(value = "/pulldown/{value}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String changeChildPulldown(@PathVariable("value") String value) {

		Integer parentId = Integer.parseInt(value);
		String str = categoryService.pulldownCategory(parentId);

		return str;

	}
	
	

}
