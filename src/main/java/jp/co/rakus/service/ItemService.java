package jp.co.rakus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.domain.Item;
import jp.co.rakus.form.ItemForm;
import jp.co.rakus.form.SearchItemForm;
import jp.co.rakus.repository.CategoryRepository;
import jp.co.rakus.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * 商品を全件検索する.
	 * 
	 * @return 商品情報の詰まったリスト
	 */
	public List<Item> findAll(Integer offset) {

		List<Item> itemList = itemRepository.findAllOffset(offset);

		return itemList;

	}

	/**
	 * 検索フォームの情報をもとに、商品を検索する.
	 * 
	 * @param searchItemForm
	 * @return 検索結果の商品情報が詰まったリスト
	 */
	public List<Item> searchItem(SearchItemForm searchItemForm,Integer offset) {
		
		String name = searchItemForm.getName();

		Integer parentId = searchItemForm.getParentId();
		Integer childId = searchItemForm.getChildId();
		Integer grandChildId = searchItemForm.getGrandChildId();
		String brand = searchItemForm.getBrand();
		
		List<Integer> searchIdList = new ArrayList<>();
		List<Item> itemList = new ArrayList<>();
		
		// もし検索条件の商品名がnullなら空文字を商品名に代入.
		if (name == "") {
			name = null;
		}
		
		// もし検索条件のブランド名がnullなら空文字をブランド名に代入.
		if (brand == "") {
			brand = null;
		}
		
		// もし検索条件の孫IDがnullなら、親IDと子IDで検索した孫IDリストを検索用IDリストに代入.
		if (grandChildId == null) {
			System.out.println("孫IDがNULL");

			searchIdList = categoryRepository.findIdByParentIdANDChildId(parentId, childId);

			// もし検索条件の子IDもnullなら、親IDで検索した孫IDリストを検索用IDリストに代入.
			if (childId == null) {

				childId = parentId;
				searchIdList = categoryRepository.findIdByParentIdANDChildId(parentId, childId);
			}

		} else {
			searchIdList.add(grandChildId);
		}


		// もし検索条件の親IDもnullなら、カテゴリーを指定せずに検索.
		if (searchIdList.size() == 0) {
			
			if(name == null && brand == null) {
				itemList = itemRepository.findAllOffset(offset);
			} else if (name == null) {
				itemList = itemRepository.findByBrand(brand,offset);
			} else if (brand == null) {
				itemList = itemRepository.findByName(name,offset);
			} else {
				itemList = itemRepository.findByNameAndBrand(name, brand,offset);
			}


		} else {
			
			if(name == null && brand == null) {
				System.out.println("カテゴリーによる検索"+searchIdList);
				itemList = itemRepository.findByCategory(searchIdList,offset);
			}else if(name == null) {
				itemList = itemRepository.findByBrandAndCategory(brand, searchIdList,offset);
			}else if( brand == null){
				itemList = itemRepository.findByNameAndCategory(name, searchIdList,offset);
			}else {
				itemList = itemRepository.findByNameAndCategoryAndBrand(name, searchIdList, brand,offset);
			}
			
		} 

		return itemList;

	}
	
	/**
	 * 商品情報を追加する.
	 * 
	 * @param itemForm 商品登録用フォームに入力された情報
	 */
	public void addItem(ItemForm itemForm) {
		
		Item item = new Item();
		BeanUtils.copyProperties(itemForm, item);
		
		item.setCategory(itemForm.getGrandChildId());
		item.setShipping(0);
		
		itemRepository.save(item);
		
	}
	
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
