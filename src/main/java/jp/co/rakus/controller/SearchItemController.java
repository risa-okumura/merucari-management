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
import jp.co.rakus.service.PagingItemListService;
import jp.co.rakus.service.SearchItemService;

@Controller
@RequestMapping("/searchItem")
@SessionAttributes(names = "searchItemForm")
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;

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
		
		Integer nowPage = pagingItemListService.nowPage(pageNum);
		// 検索用の親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);

		// 検索結果のページ数をリクエストスコープに格納する.
		Integer countPage = pagingItemListService.countPage(searchItemForm);
		System.out.println(countPage);
		model.addAttribute("countPage", countPage);
		
		//現在のページ番号をリクエストスコープに格納する.
		model.addAttribute("nowPage",nowPage);

		// 次のページ番号をリクエストスコープに格納する.
		Integer nextPage = pagingItemListService.nextLink(nowPage, countPage);
		model.addAttribute("nextPage", nextPage);

		// 1つ前のページ番号をリクエストスコープに格納する.
		Integer prePage = pagingItemListService.preLink(nowPage);
		model.addAttribute("prePage", prePage);
		
		// ページング処理を行うのに、リクエストパラメータを送るためのパス（ここでは検索ページ）を指定.
		String startPage = "searchItem/search?pageNum=";
		model.addAttribute("startPage",startPage);

		return "list";

	}

	/**
	 * フォームで受け取った検索条件を元に、商品を検索する.
	 * 
	 * @param searchItemForm　
	 * @param model
	 * @param parentId
	 * @param childId
	 * @param grandChildId
	 * @param brand
	 * @param pageNum
	 * @param searchItemForm
	 * @param model
	 * @param sessionStatus　フォームの情報をセッションに保存するのに使用する.
	 * @return　商品検索の結果画面を表示するコントローラ.
	 */
	@RequestMapping("/search")
	public String search(@RequestParam(value = "parentId", required = false) String parentId,
			@RequestParam(value = "childId", required = false) String childId,
			@RequestParam(value = "grandChildId", required = false) String grandChildId,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "pageNum", required = false) String pageNum, SearchItemForm searchItemForm,
			Model model,SessionStatus sessionStatus) {
		
		//現在のぺージ番号を元に、商品検索結果を取得するための、件数指定用の数値（OFFSET）を取得する.
		Integer nowPage = pagingItemListService.nowPage(pageNum);
		Integer offset = pagingItemListService.offset(nowPage);
		
		//検索条件と件数指定用の数値を元に商品を検索する.
		List<Item> itemList = categoryService.findCategoryList(searchItemService.searchItem(searchItemForm, offset));

		return toSearch(sessionStatus,model, searchItemForm, pageNum,itemList);
	}

	/**
	 * 検索フォームのカテゴリー欄について、プルダウンで直属の親カテゴリーが変更された際に、直属の子カテゴリーのプルダウンの内容を変更する.
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
