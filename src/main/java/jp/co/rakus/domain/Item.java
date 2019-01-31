package jp.co.rakus.domain;

/**
 * 商品情報を表すドメイン.
 * 
 * @author risa.okumura
 *
 */
public class Item {
	
	/**	商品のID */
	private Integer id;
	/**	商品の名前 */
	private String name;
	/**	商品の状態ID*/
	private Integer condition;
	/**	商品のカテゴリーID*/
	private Integer category;
	/**	商品のブランド*/
	private String brand;
	/**	商品の料金*/
	private Integer price;
	/**	商品の運送状態ID*/
	private Integer shipping;
	/**	商品の説明*/
	private String description;
	/**	商品のカテゴリー情報*/
	private Category categoryName;
	
	public Category getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(Category categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "items [id=" + id + ", name=" + name + ", conditionId=" + condition + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description
				+ "]";
	}

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
