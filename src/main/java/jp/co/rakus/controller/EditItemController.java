package jp.co.rakus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.rakus.domain.Category;
import jp.co.rakus.domain.Item;
import jp.co.rakus.domain.LoginUser;
import jp.co.rakus.form.ItemForm;
import jp.co.rakus.service.CategoryService;
import jp.co.rakus.service.EditItemService;
import jp.co.rakus.service.ViewItemDetailService;

@Controller
@Transactional
@RequestMapping("/editItem")
public class EditItemController {

	@Autowired
	private EditItemService editItemService;
	
	@Autowired
	private ViewItemDetailService viewItemDetailservice;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ViewItemDetailController viewItemDetailController;
	
	@ModelAttribute
	public ItemForm setUpForm() {
		return new ItemForm();
	}

	/**
	 *　商品編集ページを表示する.
	 * 
	 * @param id 商品のID
	 * @param model
	 * @param loginUser
	 * @return　商品編集画面.
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam("id") Integer id,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		
		Item item = viewItemDetailservice.findById(id);
		Category category = categoryService.findNameAllById(item.getCategory());
		
		model.addAttribute("item", item);
		model.addAttribute("category", category);
		
		// 親カテゴリーの情報を取得し、リクエストスコープに格納する（初期）.
		List<Category> parentList = categoryService.findParentCategory();
		model.addAttribute("parentList", parentList);

		return "edit";
	}
	
	/**
	 * 商品編集フォームに入力された情報を元に、商品情報を更新する.
	 * 
	 * @param itemForm
	 * @param result
	 * @param id
	 * @param model
	 * @param loginUser
	 * @return 情報更新後の商品詳細画面.
	 */
	@RequestMapping("/edit")
	public String edit(@Validated ItemForm itemForm,BindingResult result,String id,Model model, @AuthenticationPrincipal LoginUser loginUser) {
		
		if(itemForm.getPrice().equals("")) {
			result.rejectValue("price", "","金額を入力してください");
		}
		
		if(itemForm.getGrandChildId().equals("")) {
			result.rejectValue("grandChildId", "","カテゴリーを全て指定してください");
		}
		
		if(itemForm.getCondition()==null) {
			result.rejectValue("condition", "","商品の状態を選択してください");
		}
		
//		if(itemForm.getCondition().equals("")) {
//			result.rejectValue("condition", "","商品の状態を選択してください");
//		}
//		
		//エラーチェック用.
		if(result.hasErrors()) {
			return toEdit(Integer.parseInt(id),model,loginUser);
		}
		
		itemForm.setId(id);
		editItemService.update(itemForm);
		
		return viewItemDetailController.detail(Integer.parseInt(id), model,loginUser);
	}
	
	/**
	 * プルダウンで親カテゴリーが変更された際に、表示させる子カテゴリーのプルダウンの内容を変更する.
	 * 
	 * @param value 親カテゴリーのID
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
