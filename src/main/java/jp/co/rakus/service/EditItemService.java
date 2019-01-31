package jp.co.rakus.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.domain.Item;
import jp.co.rakus.form.ItemForm;
import jp.co.rakus.repository.ItemRepository;

@Service
public class EditItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品情報を更新する.
	 * 
	 * @param itemForm
	 */
	public void update(ItemForm itemForm) {
		
		Item item = new Item();
		BeanUtils.copyProperties(itemForm, item);
		
		item.setCategory(itemForm.getGrandChildId());
		
		itemRepository.updata(item);
		
	}
	
	

}
