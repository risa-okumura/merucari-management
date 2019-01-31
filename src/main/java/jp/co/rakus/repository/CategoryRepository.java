package jp.co.rakus.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Category;


/**
 * カテゴリー情報のリポジトリ.
 * 
 * @author risa.okumura
 *
 */
@Repository
public class CategoryRepository {
	
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	
	private static final RowMapper<Integer> CATEGORYID_ROW_MAPPER = (rs,i) ->{
		Integer searchId = rs.getInt("id");
		return searchId;
		
	};
	
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs,i) ->{
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		category.setNameAll(rs.getString("name_all"));
		category.setParent(rs.getInt("parent"));
		
		return category;
		
	};
	
	
	/**
	 * IDを指定してカテゴリー情報を1件検索する.
	 * 
	 * @param id
	 * @return カテゴリー
	 */
	public Category findById(Integer id){
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "SELECT id,name_all,parent,name FROM category WHERE id = :id ;";
		Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return category;
		
	}
	
	/**
	 * 親IDと子IDを元に、カテゴリーIDを検索する.
	 * @param parentId　親ID
	 * @param childId　子ID
	 * @return　カテゴリーIDの詰まったリスト
	 */
	public List<Integer> findIdByParentIdANDChildId(Integer parentId,Integer childId) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId).addValue("childId", childId);
		String sql = "SELECT id FROM category WHERE id = :parentId AND name_all IS NOT NULL OR parent = :childId AND name_all IS NOT NULL OR parent IN (SELECT id FROM category WHERE parent = :childId);";
		List<Integer> searchIdList = template.query(sql, param, CATEGORYID_ROW_MAPPER);
		
		return searchIdList;
	}
	
	/**
	 *　カテゴリーの情報を全件検索する.
	 * @return 全カテゴリー情報の詰まったリスト
	 */
	public List<Category> findAll(){
		SqlParameterSource param = new MapSqlParameterSource();
		String sql ="SELECT name_all,id,parent,name FROM category;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 親カテゴリーを検索する.
	 * @return　親カテゴリーの情報の詰まったリスト
	 */
	public List<Category> findParent(){
		SqlParameterSource param = new MapSqlParameterSource();
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NULL AND parent IS NULL ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 子IDに紐づく親カテゴリーを検索する.
	 * @return　親カテゴリーの情報の詰まったリスト
	 */
	public Category findParentBychildId(Integer childId){
		SqlParameterSource param = new MapSqlParameterSource().addValue("childId",childId);
		String sql ="SELECT id,name_all,parent,name FROM category WHERE id = (SELECT parent FROM category WHERE id = :childId);";
		Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return category;
	}
	
	/**
	 * 子カテゴリーを検索する.
	 * @return　子カテゴリーの情報の詰まったリスト
	 */
	public List<Category> findChild(){
		SqlParameterSource param = new MapSqlParameterSource();
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NULL AND parent IS　NOT NULL ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 検索用IDに紐づく子カテゴリーを検索する.
	 * 
	 * @param searchId
	 * @return　子カテゴリーの情報の詰まったリスト
	 */
	public List<Category> findChildByParentId(Integer searchId){
		SqlParameterSource param = new MapSqlParameterSource().addValue("searchId", searchId);
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NULL AND parent = :searchId ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 検索用IDに紐づく孫カテゴリーを検索する.
	 * @param searchId
	 * @return 孫カテゴリーの情報の詰まったリスト
	 */
	public List<Category> findGrandChildByParentId(Integer searchId){
		SqlParameterSource param = new MapSqlParameterSource().addValue("searchId", searchId);
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NOT NULL AND parent = :searchId ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	
	
}
