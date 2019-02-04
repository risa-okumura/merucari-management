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
	 * 現在のページ番号を数値に変換する.
	 * 
	 * @param pageNumStr
	 *            リクエストパラメータで送られてきたページ番号
	 * @return 数値に変換した現在のページ番号
	 */
	public Integer nowPage(String pageNumStr) {

		Integer pageNum = 1;

		if (pageNumStr != null) {

			if (pageNumStr.equals("")) {
				return pageNum;
			} else {
				return pageNum = Integer.parseInt(pageNumStr);
			}
		} else {
			return pageNum = 1;
		}

	}

	/**
	 * 表示する商品一覧のページの指定数を計算する.
	 * 
	 * @param nowPage
	 *            現在のページ番号
	 * @return ページ指定数
	 */
	public Integer offset(Integer nowPage) {

		Integer offset = 0;

		if (nowPage == 1) {
			return offset;
		}

		for (int i = 1; i < nowPage; i++) {
			offset = offset + 30;
		}

		return offset;
	}

	/**
	 * 次のページナンバーを取得する.
	 * 
	 * @param pageNum
	 *            現在のページナンバー
	 * @param countPage
	 *            ページの最大数
	 * @return 次のページナンバー
	 */
	public Integer nextLink(Integer nowPage, Integer countPage) {

		Integer nextPage = nowPage + 1;

		return nextPage;
	}

	/**
	 * 1つ前のページナンバーを取得する.
	 * 
	 * @param pageNum
	 *            現在のページナンバー
	 * @return ひとつ前のページナンバー
	 */
	public Integer preLink(Integer nowPage) {

		Integer prePageNum = nowPage - 1;

		if (prePageNum < 0) {
			prePageNum = 0;
		}
		return prePageNum;
	}

	/**
	 * 全件検索（初期表示）時のページの総数を計算する.
	 * 
	 * @return ページ数
	 */
	public Integer countPage() {
		Integer countPage = itemRepository.countAllItem() / 30;
		if (countPage == 0) {
			countPage = 1;
		}
		return countPage;

	}

	/**
	 * 検索結果のページ数を計算する.
	 * 
	 * @param searchItemForm
	 * @return 検索結果を元に計算したページ数
	 */
	public Integer countPage(SearchItemForm searchItemForm) {
		String name = searchItemForm.getName();
		String parentId = searchItemForm.getParentId();
		String childId = searchItemForm.getChildId();
		String grandChildId = searchItemForm.getGrandChildId();
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
		
		
		if(parentId == null) {
			parentId = "";
		}
		

		if (!parentId.equals("")) {

			// もし検索条件の孫IDがnullなら、親IDと子IDで検索した孫IDリストを検索用IDリストに代入.
			if (grandChildId.equals("")) {

				// もし検索条件の子IDもnullなら、親IDで検索した孫IDリストを検索用IDリストに代入.
				if (childId.equals("")) {

					childId = parentId;
					searchIdList = categoryRepository.findIdByParentIdANDChildId(Integer.parseInt(parentId),
							Integer.parseInt(childId));

				} else {
					searchIdList = categoryRepository.findIdByParentIdANDChildId(Integer.parseInt(parentId),
							Integer.parseInt(childId));
				}

			} else {
				// 孫IDを検索用IDリストに詰める.
				searchIdList.add(Integer.parseInt(grandChildId));
			}

		}

		// もし検索条件のカテゴリーIDがない場合は、カテゴリーを指定せずに検索する.
		// カテゴリーIDが存在する場合はカテゴリー情報を含めて検索する.
		if (searchIdList.size() == 0) {

			if (name == null && brand == null) {
				// 名前およびブランド名もない場合は全件検索.
				maxPageNum = itemRepository.countAllItem();
			} else if (name == null) {
				// 名前がない場合はブランド名で検索.
				maxPageNum = itemRepository.countByBrand(brand);
			} else if (brand == null) {
				// ブランド名がない場合は商品名で検索.
				maxPageNum = itemRepository.countByName(name);
			} else {
				// 商品名とブランド名で検索.
				maxPageNum = itemRepository.countByNameAndBrand(name, brand);
			}

		} else {

			if (name == null && brand == null) {
				// 名前およびブランド名がない場合はカテゴリー情報のみで検索.
				maxPageNum = itemRepository.countByCategory(searchIdList);
			} else if (name == null) {
				// 名前がない場合は、ブランド名とカテゴリー情報で検索.
				maxPageNum = itemRepository.countByBrandAndCategory(brand, searchIdList);
			} else if (brand == null) {
				// ブランド名がない場合は、商品名とカテゴリー情報で検索.
				maxPageNum = itemRepository.countByNameAndCategory(name, searchIdList);
			} else {
				// ブランド名と商品名とカテゴリー情報で検索.
				maxPageNum = itemRepository.countByNameAndCategoryAndBrand(name, searchIdList, brand);
			}
		}

		maxPageNum = maxPageNum / 30;

		if (maxPageNum == 0) {
			maxPageNum = 1;
		}

		return maxPageNum;
	}

}
