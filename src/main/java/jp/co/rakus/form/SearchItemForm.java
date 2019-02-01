package jp.co.rakus.form;

/**
 * 商品情報検索用のフォーム.
 * 
 * @author risa.okumura
 *
 */
public class SearchItemForm {
	
	/**	検索する商品名*/
	private String name;
	/**	検索する商品の親ID */
	private String parentId;
	/**	検索する商品の子ID */
	private String childId;
	/**	検索する商品の孫ID */
	private String grandChildId;
	/**	検索する商品のブランド名 */
	private String brand;
	
	
	@Override
	public String toString() {
		return "SearchItemForm [name=" + name + ", parentId=" + parentId + ", childId=" + childId + ", grandChild="
				+ grandChildId + ", brand=" + brand + "]";
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getChildId() {
		return childId;
	}


	public void setChildId(String childId) {
		this.childId = childId;
	}


	public String getGrandChildId() {
		return grandChildId;
	}


	public void setGrandChildId(String grandChildId) {
		this.grandChildId = grandChildId;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}

	

}
