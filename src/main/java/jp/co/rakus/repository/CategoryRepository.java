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
	
	private static final RowMapper<Category> NAMEALL_ROW_MAPPER = (rs,i) ->{
		Category category = new Category();
		category.setNameAll(rs.getString("name_all"));
		return category;
	};
	
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
	 * カテゴリーIDでカテゴリー情報を1件検索する.
	 * 
	 * @param id
	 * @return カテゴリー
	 */
	public Category findNameAllById(Integer id){
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "SELECT name_all FROM category WHERE id = :id ;";
		Category category = template.queryForObject(sql, param, NAMEALL_ROW_MAPPER);
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
		String sql = "SELECT id FROM category WHERE id = :parentId AND name_all IS NOT NULL OR parent = :childId AND name_all IS NOT NULL OR parent IN (SELECT id FROM category WHERE parent = :parentId);";
		List<Integer> searchIdList = template.query(sql, param, CATEGORYID_ROW_MAPPER);
		
		return searchIdList;
	}
	
	/**
	 *　全件検索する.
	 * @return カテゴリーIDの詰まったリスト
	 */
	public List<Category> findAll(){
		SqlParameterSource param = new MapSqlParameterSource();
		String sql ="SELECT name_all,id,parent,name FROM category;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 親カテゴリーを検索する.
	 * @return
	 */
	public List<Category> findParent(){
		SqlParameterSource param = new MapSqlParameterSource();
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NULL AND parent IS NULL ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 子カテゴリーを検索する.
	 * @return
	 */
	public List<Category> findChild(){
		SqlParameterSource param = new MapSqlParameterSource();
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NULL AND parent IS　NOT NULL ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 直属の親IDに紐づく子カテゴリーを検索する.
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Category> findChildByParentId(Integer parentId){
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId);
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NULL AND parent = :parentId ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 直属の親IDに基づく孫カテゴリーを検索する.
	 * @param parentId
	 * @return
	 */
	public List<Category> findGrandChildByParentId(Integer parentId){
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId);
		String sql ="SELECT id,name_all,parent,name FROM category WHERE name_all IS NOT NULL AND parent = :parentId ORDER BY name;";
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	
	
}
