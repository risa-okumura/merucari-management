package jp.co.rakus.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.domain.Item;
import jp.co.rakus.form.ItemForm;
import jp.co.rakus.repository.ItemRepository;

@Service
public class AddItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	/**
	 * 商品情報を追加する.
	 * 
	 * @param itemForm 商品登録用フォームに入力された情報
	 */
	public void addItem(ItemForm itemForm) {
		
		Item item = new Item();
		BeanUtils.copyProperties(itemForm, item);
		
		item.setCategory(Integer.parseInt(itemForm.getGrandChildId()));
		item.setShipping(0);
		item.setPrice(Integer.parseInt(itemForm.getPrice()));
		item.setCondition(Integer.parseInt(itemForm.getCondition()));
		
		itemRepository.save(item);
		
	}
	

}
