package jp.co.rakus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.form.SearchItemForm;
import jp.co.rakus.repository.CategoryRepository;
import jp.co.rakus.repository.ItemRepository;

@Service
public class PagingItemListService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	/**
	 * 表示する商品一覧のページの指定数を計算する.
	 * @param pageNum 現在のページ番号
	 * @return　ページ指定数
	 */
	public Integer offset(String pageNumStr) {
		
		Integer pageNum = 1;
		Integer offset = 0;
		
		if(pageNumStr != null) {
			
			if(pageNumStr.equals("")) {
				return offset;
			}
			
			pageNum = Integer.parseInt(pageNumStr);
			for(int i = 0;i < pageNum ; i++) {
				offset =  offset + 30;
			}
		}
		
		return offset;
	}
	
	/**
	 * 次のページナンバーを取得する.
	 * @param pageNum　現在のページナンバー
	 * @param countPage　ページの最大数
	 * @return　次のページナンバー
	 */
	public Integer nextLink(String pageNum,Integer countPage) {
		
		Integer nextPageNum = 1;
		
		if(pageNum != null) {
			if(!pageNum.equals("")) {
				nextPageNum = Integer.parseInt(pageNum) + 1;
			}
		}
		
//		if(nextPageNum == countPage) {
//			nextPageNum = null;
//		}
		return nextPageNum;
	}
	
	/**
	 * 1つ前のページナンバーを取得する.
	 * @param pageNum 現在のページナンバー
	 * @return　ひとつ前のページナンバー
	 */
	public Integer preLink(String pageNum) {
		
		Integer prePageNum = null;
		
		if(pageNum != null) {
			if(!pageNum.equals("")) {
				prePageNum = Integer.parseInt(pageNum) - 1;
				if(prePageNum < 0) {
					prePageNum = 0;
				}
			}
		}		
		return prePageNum;
	}
	
	/**
	 * 全件検索（初期表示）時のページの総数を計算する.
	 * @return　ページ数
	 */
	public Integer countPage() {
		return itemRepository.countAllItem()/30+1;
	}
	
	
	/**
	 * 検索結果のページ数を計算する.
	 * @param searchItemForm
	 * @return
	 */
	public Integer countPage(SearchItemForm searchItemForm) {
		String name = searchItemForm.getName();
		Integer parentId = searchItemForm.getParentId();
		Integer childId = searchItemForm.getChildId();
		Integer grandChildId = searchItemForm.getGrandChildId();
		String brand = searchItemForm.getBrand();
		
		// // もし検索条件の商品名が空文字ならnullを商品名に代入.
		if (name == "") {
			name = null;
		}
		// もし検索条件のブランド名が空文字ならnullをブランド名に代入.
		if (brand == "") {
			brand = null;
		}
		
		List<Integer> searchIdList = new ArrayList<>();
		Integer maxPageNum = 0;

		// もし検索条件の孫IDがnullなら、親IDと子IDで検索した孫IDリストを検索用IDリストに代入.
		if (grandChildId == null) {
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
				maxPageNum = itemRepository.countAllItem();
			} else if (name == null) {
				maxPageNum = itemRepository.countByBrand(brand);
			} else if (brand == null) {
				maxPageNum = itemRepository.countByName(name);
			} else {
				maxPageNum = itemRepository.countByNameAndBrand(name, brand);
			}


		} else {
			
			if(name == null && brand == null) {
				maxPageNum = itemRepository.countByCategory(searchIdList);
			}else if(name == null) {
				maxPageNum = itemRepository.countByBrandAndCategory(brand, searchIdList);
			}else if( brand == null){
				maxPageNum = itemRepository.countByNameAndCategory(name, searchIdList);
			}else {
				maxPageNum = itemRepository.countByNameAndCategoryAndBrand(name, searchIdList, brand);
			}
		} 
		
		return (maxPageNum/30) + 1 ;
	}

}
