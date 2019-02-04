package jp.co.rakus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.domain.Category;
import jp.co.rakus.domain.Item;
import jp.co.rakus.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * 全親カテゴリーの情報を取得する.
	 * @return 親カテゴリーの情報の詰まったリスト
	 */
	public List<Category> findParentCategory(){
		return categoryRepository.findParent();
		
	}
	
	/**
	 * 商品情報の中にあるカテゴリーの名前をを親、子、孫の3つに分割し、それぞれ商品情報にセットする.
	 * 
	 * @param itemList　商品情報の詰まったリスト
	 * @return　カテゴリーをセットし直した商品情報の詰まったリスト
	 */
	public List<Item> findCategoryList(List<Item> itemList){
		
		for (Item item : itemList) {
			
			Integer id = item.getCategory();
			
			if(id != 0) {
				
				Category category = categoryRepository.findById(id);
				
				String categoryName = category.getNameAll();
				if(categoryName==null) {
					return itemList;
					
				}
				String[] categoryNames = categoryName.split("/", 3);
				
				String parent = categoryNames[0];
				String child = categoryNames[1];
				String grandChild = categoryNames[2];
				
				category.setParentName(parent);
				category.setChildName(child);
				category.setGrandChildName(grandChild);
				
				Category parentCategory = categoryRepository.findParentBychildId(category.getParent());
				category.setParentId(parentCategory.getId());
				category.setChildId(category.getParent());
				
				item.setCategoryName(category);
			}
		}
		return itemList;
	}
	
	
	/**
	 * カテゴリー検索のプルダウン表示について、上位のカテゴリーが変更されたら下位のカテゴリーのプルダウン内容を変更する.
	 * 
	 * @param parentId　上位のカテゴリーID
	 * @return　下位カテゴリーのプルダウン内容の詰まった文字列
	 */
	public String pulldownCategory(Integer searchId){
		StringBuilder stringBuilder = new StringBuilder();
		String str = "";
		stringBuilder.append("[");
		
		//Ajaxで送信された検索用カテゴリーIDに紐づく子カテゴリーの情報を検索し、データを送り返す処理
		List<Category> parentList = findParentCategory();
		List<Integer> parentIdList = new ArrayList<>();
		
		//親カテゴリー情報の詰まったリストからIDだけを抽出し、親IDリストに詰める.
		for(Category category : parentList) {
			parentIdList.add(category.getId());
		}
		
		List<Category> childCategoryList = new ArrayList<>();
		
		//もし検索用IDが、親IDリストに含まれる場合は、子カテゴリーを検索する.
		//含まない場合は、孫カテゴリーを検索する.
		if(parentIdList.contains(searchId)) {
			childCategoryList = categoryRepository.findChildByParentId(searchId);
		}else {
			childCategoryList = categoryRepository.findGrandChildByParentId(searchId);
		}
		
		//検索結果のカテゴリー情報の詰まったリストから、IDと名前を抽出し、JSON形式で保存する.
		for(int i = 0 ; i < childCategoryList.size() ; i++) {
			Category category = childCategoryList.get(i);
			stringBuilder.append("{\"");
			stringBuilder.append("pulldownValue");
			stringBuilder.append("\"");
		    stringBuilder.append(":");
		    stringBuilder.append("\"");
			stringBuilder.append(category.getId());
			stringBuilder.append("\"");
			stringBuilder.append(",");
			stringBuilder.append("\"");
			stringBuilder.append("pulldownLabel");
			stringBuilder.append("\"");
		    stringBuilder.append(":");
		    stringBuilder.append("\"");
			stringBuilder.append(category.getName());
			stringBuilder.append("\"}");
	        stringBuilder.append(",");
		}
		
		stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
		stringBuilder.append("]");
		str = stringBuilder.toString();
		
		return str;
		
	}
	
	/**
	 * カテゴリーIDをもとに、名前が入ったカテゴリー情報を検索する.
	 * 
	 * @param id
	 * @return
	 */
	public Category findNameAllById(Integer id) {
		
		Category category = new Category();
		
		if(id != 0) {
			category = categoryRepository.findById(id);
		}
		return category;
	}
	
	
	

}
