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
			Category category = categoryRepository.findNameAllById(item.getCategory());

			String categoryName = category.getNameAll();
			String[] categoryNames = categoryName.split("/", 3);

			String parent = categoryNames[0];
			String child = categoryNames[1];
			String grandChild = categoryNames[2];

			category.setParentName(parent);
			category.setChildName(child);
			category.setGrandChildName(grandChild);

			item.setCategoryName(category);
		}
		
		return itemList;
	}
	
	/**
	 * カテゴリー検索のプルダウン表示について、上位のカテゴリーが変更されたら下位のカテゴリーのプルダウン内容を変更する.
	 * 
	 * @param parentId　上位のカテゴリーID
	 * @return　下位カテゴリーのプルダウン内容の詰まったJSON
	 */
	public String pulldownCategory(Integer parentId){
		
		StringBuilder stringBuilder = new StringBuilder();
		String str = "";
		stringBuilder.append("[");
		
		//Ajaxで送信された親カテゴリーのIDをもとに紐づく子カテゴリーの情報を検索し、データを送り返す処理
		
		List<Category> parentList = findParentCategory();
		List<Integer> parentIdList = new ArrayList<>();
		for(Category category : parentList) {
			parentIdList.add(category.getId());
		}
		
		List<Category> childCategoryList = new ArrayList<>();
		
		if(parentIdList.contains(parentId)) {
			childCategoryList = categoryRepository.findChildByParentId(parentId);
		}else {
			childCategoryList = categoryRepository.findGrandChildByParentId(parentId);
		}
		
		for(int i = 0 ; i < childCategoryList.size() ; i++) {
			Category category = childCategoryList.get(i);
			stringBuilder.append("{\"");
			stringBuilder.append("childValue");
			stringBuilder.append("\"");
		    stringBuilder.append(":");
		    stringBuilder.append("\"");
			stringBuilder.append(category.getId());
			stringBuilder.append("\"");
			stringBuilder.append(",");
			stringBuilder.append("\"");
			stringBuilder.append("childLabel");
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
	
	public Category findNameAllById(Integer id) {
		Category category = categoryRepository.findNameAllById(id);
		return category;
	}
	
	
	

}
