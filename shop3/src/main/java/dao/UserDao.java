package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
	private Map<String, Object> param = new HashMap<>();

	@Autowired
	public void setDataSource(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public void insert(User user) {
		String sql = "insert into useraccount (userid, password, username, phoneno, postcode, address, email, birthday) values "
				+ "(:userid, :password, :username, :phoneno, :postcode, :address, :email, :birthday)";
		SqlParameterSource proparam = new BeanPropertySqlParameterSource(user);
		template.update(sql, proparam); // db에 등록
	}

	public User selectOne(String userid) {
		String sql = "select * from useraccount where userid = :userid";
		param.clear();
		param.put("userid", userid);
		return template.queryForObject(sql, param, mapper);

	}

	public void update(User user) {
		String sql = "update useraccount set username = :username, phoneno = :phoneno, postcode = :postcode, address = :address, "
				+ "email = :email, birthday = :birthday where userid = :userid";
		SqlParameterSource proparam = new BeanPropertySqlParameterSource(user);
		template.update(sql, proparam);
	}

	public void delete(String userid) {
		param.clear();
		param.put("userid", userid);
		String sql = "delete from useraccount where userid = :userid";
		template.update(sql, param);
	}

	public List<User> list() {
		return template.query("select * from useraccount", mapper);
	}

	public List<User> list(String[] idchks) {

		String ids = "";
		
		for(int i = 0 ;i <idchks.length;i++) {
			ids += "'" + idchks[i] + ((i==idchks.length-1)?"'":"',");
		}
		String sql = "select * from useraccount where userid in (" + ids +")";
		return template.query(sql, mapper);
	}

}
