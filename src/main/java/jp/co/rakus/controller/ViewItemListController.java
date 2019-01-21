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
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.rakus.domain.Category;
import jp.co.rakus.domain.Item;
import jp.co.rakus.form.SearchItemForm;
import jp.co.rakus.service.CategoryService;
import jp.co.rakus.service.ItemService;

/**
 * 商品一覧画面を表示するコントローラー.
 * 
 * @author risa.okumura
 *
 */
@Controller
@Transactional
@RequestMapping("/viewItemList")
public class ViewItemListController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	@ModelAttribute
	public SearchItemForm setUpForm() {
		return new SearchItemForm();
	}
	

	/**
	 * 商品一覧表示画面を表示する.
	 * 
	 * @param model
	 * @return 商品一覧表示画面
	 */
	@RequestMapping("/list")
	public String list(Model model) {

		// 商品一覧を表示する.
		List<Item> itemList = categoryService.findCategoryList(itemService.findAll());
		model.addAttribute("itemList", itemList);

		// 検索用の親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);

		return "list";
	}

	/**
	 * 検索フォームのカテゴリー欄について、プルダウンで親カテゴリーが変更された際に、表示させる子カテゴリーのプルダウンの内容を変更する.
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
	
	/**
	 * 検索後の商品一覧画面を表示する.
	 * 
	 * @param searchItemForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/searchItem")
	public String searchItem(SearchItemForm searchItemForm,Model model) {

		List<Item> itemList = categoryService.findCategoryList(itemService.searchItem(searchItemForm));
		model.addAttribute("itemList", itemList);
		
		// 検索用の親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);

		return "list";
	}

}
