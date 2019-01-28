package jp.co.rakus.domain;

/**
 * カテゴリーを表すドメイン.
 * @author risa.okumura
 *
 */
public class Category {

	/**カテゴリーID*/
	private Integer id;
	/**直属の親カテゴリーのID*/
	private Integer parent;
	/**カテゴリーIDに対するカテゴリー名*/
	private String name;
	/**全てのカテゴリー名を合わせた名前*/
	private String nameAll;
	
	/**親カテゴリーの名前*/
	private String parentName;
	/**子カテゴリーの名前*/
	private String childName;
	/**孫カテゴリーの名前*/
	private String grandChildName;
	
	/**親カテゴリーのID*/
	private Integer parentId;	
	/**子カテゴリーのID*/
	private Integer childId;	


	@Override
	public String toString() {
		return "Category [id=" + id + ", parent=" + parent + ", name=" + name + ", nameAll=" + nameAll + ", parentName="
				+ parentName + ", childName=" + childName + ", grandChildName=" + grandChildName + ", parentId="
				+ parentId + ", childId=" + childId + "]";
	}


	public Integer getChildId() {
		return childId;
	}


	public void setChildId(Integer childId) {
		this.childId = childId;
	}



	public Integer getParentId() {
		return parentId;
	}


	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName() {
		return parentName;
	}


	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public String getChildName() {
		return childName;
	}


	public void setChildName(String childName) {
		this.childName = childName;
	}


	public String getGrandChildName() {
		return grandChildName;
	}


	public void setGrandChildName(String grandChildName) {
		this.grandChildName = grandChildName;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	
	
}
