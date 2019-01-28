package jp.co.rakus.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Item;

/**
 * 商品情報のリポジトリ.
 * 
 * @author risa.okumura
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {

		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setShipping(rs.getInt("shipping"));
		item.setPrice(rs.getInt("price"));
		item.setDescription(rs.getString("description"));

		return item;
	};

	/**
	 * 指定された件数の商品情報を検索する.
	 * 
	 * @return 商品情報の詰まったリスト
	 */
	public List<Item> findAll() {
		SqlParameterSource param = new MapSqlParameterSource();
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items ORDER BY name LIMIT 30";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 指定された件数の商品情報を検索する.
	 * 
	 * @return 商品情報の詰まったリスト
	 */
	public List<Item> findAllOffset(Integer offset) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	} 


	/**
	 * idをもとに商品情報を1件検索する.
	 * 
	 * @param id
	 *            商品ID
	 * @return 商品情報
	 */
	public Item findById(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE id = :id;";
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	/**
	 * 商品名、カテゴリーID、ブランド名をもとに商品情報を検索する.
	 * 
	 * @param name
	 *            商品名
	 * @param grandChildId
	 *            孫ID
	 * @param brand
	 *            ブランド名
	 * @return 商品情報の入ったリスト
	 */
	public List<Item> findByNameAndCategoryAndBrand(String name, List<Integer> searchIdList, String brand,Integer offset) {

		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
				.addValue("searchcategorys", searchcategorys).addValue("brand", "%" + brand + "%").addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE LOWER(name) LIKE LOWER(:name) AND category IN (:searchcategorys) AND LOWER(brand) LIKE LOWER(:brand) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;

	}

	/**
	 * 商品名、ブランド名をもとに商品情報を検索する.
	 * 
	 * @param name
	 *            商品名
	 * @param brand
	 *            ブランド名
	 * @return 商品情報の入ったリスト
	 */
	public List<Item> findByNameAndBrand(String name, String brand,Integer offset) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
															  .addValue("brand","%" + brand + "%")
															  .addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE LOWER(name) LIKE LOWER(:name) AND LOWER(brand) LIKE LOWER(:brand) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品名、カテゴリー情報をもとに商品情報を検索する.
	 * 
	 * @param name 商品名
	 * @param searchIdList カテゴリーID
	 * @return 商品情報のつまったリスト
	 */
	public List<Item> findByNameAndCategory(String name, List<Integer> searchIdList,Integer offset) {

		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
				.addValue("searchcategorys", searchcategorys).addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE LOWER(name) LIKE LOWER(:name) AND category IN (:searchcategorys) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品名で商品情報を検索する.
	 * @param name 商品名
	 * @return 商品情報のつまったリスト
	 */
	public List<Item> findByName(String name,Integer offset) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE LOWER(name) LIKE LOWER(:name) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * ブランド名で商品情報を検索する.
	 * @param brand
	 * @return
	 */
	public List<Item> findByBrand(String brand,Integer offset) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", "%" + brand + "%").addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE LOWER(brand) LIKE LOWER(:brand) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * ブランド名、カテゴリー情報をもとに商品情報を検索する.
	 * 
	 * @param brand
	 * @param searchIdList
	 * @return
	 */
	public List<Item> findByBrandAndCategory(String brand, List<Integer> searchIdList,Integer offset) {

		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", "%" + brand + "%")
				.addValue("searchcategorys", searchcategorys).addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE LOWER(brand) LIKE LOWER(:brand) AND category IN (:searchcategorys) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 孫IDを元に商品情報を検索する.
	 * 
	 * @param searchIdList
	 * @return
	 */
	public List<Item> findByCategory(List<Integer> searchIdList,Integer offset) {

		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("searchcategorys", searchcategorys).addValue("offset", offset);
		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE category IN (:searchcategorys) ORDER BY name LIMIT 30 OFFSET :offset;";
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品情報を追加する.
	 * 
	 * @param item　登録する商品情報
	 */
	public void save(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "INSERT INTO items(name,condition,category,brand,price,shipping,description) VALUES (:name,:condition,:category,:brand,:price,:shipping,:description)";
		template.update(sql, param);
	}
	
	/**
	 * 商品情報を更新する.
	 * 
	 * @param item
	 */
	public void updata(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "UPDATE items SET name = :name, brand = :brand,condition = :condition, category = :category, price = :price, description = :description WHERE id = :id";
		template.update(sql, param);
	}
	
	
	/**
	 * 登録されている全ての商品数をカウントする.
	 * @return 商品数
	 */
	public Integer countAllItem() {
		SqlParameterSource param = new MapSqlParameterSource();
		String sql = "SELECT COUNT(*) FROM items;";
		Integer countItem = template.queryForObject(sql, param, Integer.class);
		return countItem;
	}
	
	/**
	 * 商品名で検索した結果の商品数をカウントする.
	 * @return 商品数
	 */
	public Integer countByName(String name) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		String sql = "SELECT COUNT(*) FROM items WHERE LOWER(name) LIKE LOWER(:name);";
		Integer countItem = template.queryForObject(sql, param, Integer.class);
		return countItem;
	}

	
	/**
	 * 商品名とカテゴリーIDで検索した結果の商品数をカウントする.
	 * @param name　商品名
	 * @param searchIdList　カテゴリーIDの詰まったリスト
	 * @return 商品数
	 */
	public Integer countByNameAndCategory(String name,List<Integer> searchIdList) {
		
		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
															  .addValue("searchcategorys", searchcategorys);
		String sql = "SELECT COUNT(*) FROM items WHERE LOWER(name) LIKE LOWER(:name) AND category IN (:searchcategorys);";
		Integer countItem = template.queryForObject(sql, param, Integer.class);
		return countItem;
	}
	
	
	/**
	 * 商品名とブランド名で検索した結果の商品数をカウントする.
	 * @param name 商品名
	 * @param brand　ブランド名
	 * @return　商品数
	 */
	public Integer countByNameAndBrand(String name, String brand) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("brand",
				"%" + brand + "%");
		String sql = "SELECT COUNT(*) FROM items WHERE LOWER(name) LIKE LOWER(:name) AND LOWER(brand) LIKE LOWER(:brand);";
		Integer countItem  = template.queryForObject(sql, param, Integer.class);
		return countItem;
	}
	
	
	/**
	 * 商品名とブランド名とカテゴリーIDで検索した結果の商品数をカウントする.
	 * @param name　商品名
	 * @param searchIdList　カテゴリーIDの詰まったリスト
	 * @param brand　ブランド名
	 * @return　商品数
	 */
	public Integer countByNameAndCategoryAndBrand(String name, List<Integer> searchIdList, String brand) {

		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
				.addValue("searchcategorys", searchcategorys).addValue("brand", "%" + brand + "%");
		String sql = "SELECT COUNT(*) FROM items WHERE LOWER(name) LIKE LOWER(:name) AND category IN (:searchcategorys) AND LOWER(brand) LIKE LOWER(:brand)";
		Integer countItem = template.queryForObject(sql, param, Integer.class);
		return countItem;

	}
	
	/**
	 * ブランド名で検索した結果の商品数をカウントする.
	 * @param brand　ブランド名
	 * @return　商品数
	 */
	public Integer countByBrand(String brand) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", "%" + brand + "%");
		String sql = "SELECT COUNT(*) FROM items WHERE LOWER(brand) LIKE LOWER(:brand)";
		Integer countItem  = template.queryForObject(sql, param,Integer.class);
		return countItem;
	}
	
	/**
	 * ブランド名とカテゴリーIDで検索した結果の商品数をカウントする.
	 * @param brand　ブランド名
	 * @param searchIdList　カテゴリーIDの詰まったリスト
	 * @return　商品数
	 */
	public Integer countByBrandAndCategory(String brand, List<Integer> searchIdList) {
		
		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", "%" + brand + "%")
				.addValue("searchcategorys", searchcategorys);
		String sql = "SELECT COUNT(*) FROM items WHERE LOWER(brand) LIKE LOWER(:brand) AND category IN (:searchcategorys);";
		Integer countItem  = template.queryForObject(sql, param,Integer.class);
		return countItem;
	}
	
	/**
	 * カテゴリーIDを元に検索した結果の商品数をカウントする.
	 * @param searchIdList カテゴリーIDの詰まったリスト
	 * @return 商品数
	 */
	public Integer countByCategory(List<Integer> searchIdList) {

		Set<Integer> searchcategorys = new HashSet<>();
		for (Integer searchId : searchIdList) {
			searchcategorys.add(searchId);
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("searchcategorys", searchcategorys);
		String sql = "SELECT COUNT(*) FROM items WHERE category IN (:searchcategorys);";
		Integer countItem  = template.queryForObject(sql, param,Integer.class);
		return countItem;
	}

	
	
}
