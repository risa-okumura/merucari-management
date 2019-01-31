package jp.co.rakus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.domain.Item;
import jp.co.rakus.repository.ItemRepository;

@Service
public class ViewItemDetailService {
	
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * IDを元に商品情報を取得する.
	 * 
	 * @param id
	 * @return 商品情報
	 */
	public Item findById(Integer id) {
		
		Item item = itemRepository.findById(id);
		
		return item;
	}

}
