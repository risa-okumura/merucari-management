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
	private Integer parentId;
	/**	検索する商品の子ID */
	private Integer childId;
	/**	検索する商品の孫ID */
	private Integer grandChildId;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	public Integer getGrandChildId() {
		return grandChildId;
	}
	public void setGrandChildId(Integer grandChildId) {
		this.grandChildId = grandChildId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	
	

}
