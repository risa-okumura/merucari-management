package jp.co.rakus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.rakus.domain.Category;
import jp.co.rakus.domain.Item;
import jp.co.rakus.domain.LoginUser;
import jp.co.rakus.service.CategoryService;
import jp.co.rakus.service.ItemService;


/**
 * 商品詳細を表示するコントローラー.
 * 
 * @author risa.okumura
 *
 */
@Controller
@Transactional
@RequestMapping("/viewItemDetail")
public class ViewItemDetailController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 送られてきたIDを元に商品情報の詳細を表示する.
	 * @param id
	 * @param model
	 * @return　商品詳細表示画面
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam("id") Integer id,Model model, @AuthenticationPrincipal LoginUser loginUser) {
		
		Item item = itemService.findById(id);
		Category category = categoryService.findNameAllById(item.getCategory());
		
		model.addAttribute("item", item);
		model.addAttribute("category", category);
		
		return "detail";
	}

}
