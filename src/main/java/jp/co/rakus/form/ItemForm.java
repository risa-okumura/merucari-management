package jp.co.rakus.form;

/**
 * 
 * 商品情報を受け取るフォーム.
 * 
 * @author risa.okumura
 *
 */
public class ItemForm {

	/** 商品のID */
	private Integer id;
	/** 商品の名前 */
	private String name;
	/** 商品の状態ID */
	private Integer condition;
	/** 商品のカテゴリーID */
	private Integer category;
	/** 商品のブランド */
	private String brand;
	/** 商品の料金 */
	private Integer price;
	/** 商品の説明 */
	private String description;
	/** 親カテゴリーの名前 */
	private Integer parentId;
	/** 子カテゴリーの名前 */
	private Integer childId;
	/** 孫カテゴリーの名前 */
	private Integer grandChildId;

	@Override
	public String toString() {
		return "ItemForm [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", description=" + description + ", parentId=" + parentId
				+ ", childId=" + childId + ", grandChildId=" + grandChildId + ", shipping=" + shipping + "]";
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

	private Integer shipping;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
