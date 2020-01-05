package dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dao.mapper.UserMapper;
import exception.LoginException;
import logic.User;

@Repository
public class UserDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	private Map<String, Object> param = new HashMap<String, Object>();

	public void insert(User user) {
		sqlSession.getMapper(UserMapper.class).insert(user);
	}

	public void update(User user) {
		sqlSession.getMapper(UserMapper.class).update(user);
	}

	public void delete(String userid) {
		sqlSession.getMapper(UserMapper.class).delete(userid);
	}
	
	public List<User> list() {
		return sqlSession.getMapper(UserMapper.class).select(null);
	}
	
	public User selectOne(String userid) {
		param.clear();
		param.put("userid", userid);
		List<User> list = sqlSession.getMapper(UserMapper.class).select(param);
		if(list==null || list.isEmpty()) {
			throw new LoginException("해당 아이디 없음","");
		}else {
			return list.get(0);
		}
	}
	
	public List<User> list(String[] idchks) {
/*		
		String ids = "";
		for(int i = 0 ;i <idchks.length;i++) {
			ids += "'" + idchks[i] + ((i==idchks.length-1)?"'":"',");
		}
		param.clear();
		param.put("ids", ids);
		//스트링에 이메일 한번에 보내는 방식
*/
		List<String> ids = Arrays.asList(idchks);		//배열로 넣어도, 리스트로 넣어도 상관없다.
		param.clear();
		param.put("userids", ids);
		return sqlSession.getMapper(UserMapper.class).select(param);
	}

	public void updatepassword(String userid, String chgpass) {
		param.clear();
		param.put("userid", userid);
		param.put("password", chgpass);
		sqlSession.getMapper(UserMapper.class).updatepass(param);
		
	}

}
