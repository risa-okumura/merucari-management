package jp.co.rakus.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddItemForm {
	

	/** 商品のID */
	private String id;
	/** 商品の名前 */
	@NotEmpty(message="商品名を入力してください")
	private String name;
	/** 商品の状態ID */
	private String condition;
	/** 商品のカテゴリーID */
	private String category;
	/** 商品のブランド */
	private String brand;
	/** 商品の料金 */
	@NotNull(message="商品の値段を入力してください")
	private String price;
	/** 商品の説明 */
	@NotEmpty(message="商品の説明を入力してください")
	private String description;
	/** 親カテゴリーのID */
	private String pulldown1;
	/** 子カテゴリーのID */
	private String pulldown2;
	/** 孫カテゴリーのID */
	private String pulldown3;
	
	@Override
	public String toString() {
		return "AddItemForm [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", description=" + description + ", pulldown1=" + pulldown1
				+ ", pulldown2=" + pulldown2 + ", pulldown3=" + pulldown3 + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPulldown1() {
		return pulldown1;
	}
	public void setPulldown1(String pulldown1) {
		this.pulldown1 = pulldown1;
	}
	public String getPulldown2() {
		return pulldown2;
	}
	public void setPulldown2(String pulldown2) {
		this.pulldown2 = pulldown2;
	}
	public String getPulldown3() {
		return pulldown3;
	}
	public void setPulldown3(String pulldown3) {
		this.pulldown3 = pulldown3;
	}

}
