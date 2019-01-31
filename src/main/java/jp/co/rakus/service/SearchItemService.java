package jp.co.rakus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.domain.Item;
import jp.co.rakus.form.SearchItemForm;
import jp.co.rakus.repository.CategoryRepository;
import jp.co.rakus.repository.ItemRepository;

@Service
public class SearchItemService {
	
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
			//孫IDを検索用IDリストに詰める.
			searchIdList.add(grandChildId);
		}


		// もし検索条件のカテゴリーIDがない場合は、カテゴリーを指定せずに検索する.
		//　カテゴリーIDが存在する場合はカテゴリー情報を含めて検索する.
		if (searchIdList.size() == 0) {
			if(name == null && brand == null) {
				//名前およびブランド名もない場合は全件検索.
				itemList = itemRepository.findAllOffset(offset);
			} else if (name == null) {
				//名前がない場合はブランド名で検索.
				itemList = itemRepository.findByBrand(brand,offset);
			} else if (brand == null) {
				//ブランド名がない場合は商品名で検索.
				itemList = itemRepository.findByName(name,offset);
			} else {
				//商品名とブランド名で検索.
				itemList = itemRepository.findByNameAndBrand(name, brand,offset);
			}
		} else {
			if(name == null && brand == null) {
				//名前およびブランド名がない場合はカテゴリー情報のみで検索.
				itemList = itemRepository.findByCategory(searchIdList,offset);
			}else if(name == null) {
				//名前がない場合は、ブランド名とカテゴリー情報で検索.
				itemList = itemRepository.findByBrandAndCategory(brand, searchIdList,offset);
			}else if( brand == null){
				//ブランド名がない場合は、商品名とカテゴリー情報で検索.
				itemList = itemRepository.findByNameAndCategory(name, searchIdList,offset);
			}else {
				//ブランド名と商品名とカテゴリー情報で検索.
				itemList = itemRepository.findByNameAndCategoryAndBrand(name, searchIdList, brand,offset);
			}
			
		} 

		return itemList;

	}

}
