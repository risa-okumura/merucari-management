package jp.co.rakus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.rakus.domain.Category;
import jp.co.rakus.domain.Item;
import jp.co.rakus.form.SearchItemForm;
import jp.co.rakus.service.CategoryService;
import jp.co.rakus.service.ItemService;
import jp.co.rakus.service.PagingItemListService;

@Controller
@RequestMapping("/searchItem")
@SessionAttributes(names = "searchItemForm")
public class SearchItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PagingItemListService pagingItemListService;

	@ModelAttribute
	public SearchItemForm setUpForm() {
		return new SearchItemForm();
	}
	
	/**
	 * 検索結果を表示し、ページング処理を行う.
	 * 
	 * @param model
	 * @param searchItemForm
	 * @param pageNum
	 * @param itemList
	 * @return 検索結果画面.
	 */
	@RequestMapping("/toSearch")
	public String toSearch(SessionStatus sessionStatus,Model model, SearchItemForm searchItemForm,@RequestParam(value = "pageNum", required = false) String pageNum,List<Item> itemList) {

		model.addAttribute("itemList", itemList);
		
		// 検索用の親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);

		// 検索結果のページ数をリクエストスコープに格納する.
		Integer countPage = pagingItemListService.countPage(searchItemForm);
		System.out.println(countPage);
		model.addAttribute("countPage", countPage);

		// ページング用リンクをリクエストスコープに格納する.
		
		System.out.println("現在のページは" + pageNum);
		Integer nextLink = pagingItemListService.nextLink(pageNum, countPage);
		System.out.println("次のリンク" + nextLink);
		model.addAttribute("nextLink", nextLink);

		Integer preLink = pagingItemListService.preLink(pageNum);
		System.out.println("前のリンク" + preLink);
		model.addAttribute("preLink", preLink);
		
		String startPage = "searchItem/search?pageNum=";
		model.addAttribute("startPage",startPage);

		return "list";

	}

	/**
	 * 商品を検索する.
	 * 
	 * @param searchItemForm
	 * @param model
	 * @return　検索結果画面.
	 */
	@RequestMapping("/search")
	public String search(@RequestParam(value = "parentId", required = false) String parentId,
			@RequestParam(value = "childId", required = false) String childId,
			@RequestParam(value = "grandChildId", required = false) String grandChildId,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "pageNum", required = false) String pageNum, SearchItemForm searchItemForm,
			Model model,SessionStatus sessionStatus) {

		System.out.println("検索条件" + searchItemForm.toString());

		Integer offset = pagingItemListService.offset(pageNum);
		List<Item> itemList = categoryService.findCategoryList(itemService.searchItem(searchItemForm, offset));

		return toSearch(sessionStatus,model, searchItemForm, pageNum,itemList);
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

}
