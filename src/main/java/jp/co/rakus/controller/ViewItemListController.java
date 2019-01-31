package jp.co.rakus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import jp.co.rakus.domain.LoginUser;
import jp.co.rakus.form.SearchItemForm;
import jp.co.rakus.service.CategoryService;
import jp.co.rakus.service.PagingItemListService;
import jp.co.rakus.service.SearchItemService;

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
	 * 商品一覧表示画面を表示する.
	 * 
	 * @param model
	 * @return 商品一覧表示画面
	 */
	@RequestMapping("/list")
	public String list(Model model, @AuthenticationPrincipal LoginUser loginUser,@RequestParam(value="pageNum",required = false) String pageNum) {
		
		// 商品一覧を表示する.
		Integer nowPage = pagingItemListService.nowPage(pageNum);
		Integer offset = pagingItemListService.offset(nowPage);
		List<Item> itemList = categoryService.findCategoryList(searchItemService.findAll(offset));
		model.addAttribute("itemList", itemList);

		// 検索用の親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);
		
		//総ページ数をリクエストスコープに格納する.
		Integer countPage = pagingItemListService.countPage();
		model.addAttribute("countPage", countPage);
		
		//現在のページ番号をリクエストスコープに格納する.
		model.addAttribute("nowPage",nowPage);
		
		// 次のページ番号をリクエストスコープに格納する.
		Integer nextPage = pagingItemListService.nextLink(nowPage,countPage);
		model.addAttribute("nextPage",nextPage);
		
		// 1つ前のページ番号をリクエストスコープに格納する.
		Integer prePage = pagingItemListService.preLink(nowPage);
		model.addAttribute("prePage",prePage);
		
		// ページング処理を行うのに、リクエストパラメータを送るためのパスを指定.
		String startPage = "viewItemList/list?pageNum=";
		model.addAttribute("startPage",startPage);
		
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
		
		if(value.equals("undefined")) {
			return null;
		}

		Integer parentId = Integer.parseInt(value);
		String str = categoryService.pulldownCategory(parentId);

		return str;

	}

}
