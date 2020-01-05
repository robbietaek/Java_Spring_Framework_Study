package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Board;

@Repository
public class BoardDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<Board> mapper= new BeanPropertyRowMapper<Board>(Board.class);
	private Map<String, Object> param = new HashMap<>();
	private String boardcolumn = "select num, name, pass, subject, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep "
								+ "from board ";
	@Autowired
	public void setDataSource(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	public int count(String searchtype, String searchcontent) {
		param.clear();
		String sql = "select count(*) from board";
		if(searchtype!=null) {
			sql += " where " + searchtype+ " like :searchcontent";
			param.put("searchcontent", "%" + searchcontent + "%" );
		}
		return template.queryForObject(sql, param,Integer.class);
	}
	public List<Board> list(Integer pageNum, Integer limit, String searchtype, String searchcontent) {
		param.clear();
		String sql = boardcolumn;
		if(searchtype!=null) {
			sql += " where " + searchtype+ " like :searchcontent";
			param.put("searchcontent", "%" + searchcontent + "%" );
		}
		sql += " order by grp desc, grpstep limit :startrow, :limit";
		param.put("startrow", (pageNum-1) * limit);
		param.put("limit", limit);

		return template.query(sql, param,mapper);
	}
	
	public int maxnum() {
		return template.queryForObject("select ifnull(max(num),0) from board", param, Integer.class);
	}
	public void insert(Board board) {
		String sql = "insert into board (num, name, pass, subject, file1, content, regdate, readcnt, grp, grplevel, grpstep)"
				+ " values (:num, :name, :pass, :subject,:fileurl, :content, now(), 0, :grp, :grplevel, :grpstep)";
		SqlParameterSource proparam = new BeanPropertySqlParameterSource(board);
		template.update(sql, proparam);
	}
	public Board selectOne(Integer num) {
		param.clear();
		param.put("num", num);
		String sql = boardcolumn + " where num = :num";
		return template.queryForObject(sql, param,mapper);
	}
	public void readcntadd(Integer num) {
		param.clear();
		param.put("num", num);
		template.update("update board set readcnt = readcnt+1 where num = :num ", param);
	}
	public void updateGrpStep(Board board) {
		String sql = "update board set grpstep = grpstep +1 where grp = :grp and grpstep > :grpstep";
		param.clear();
		param.put("grp",board.getGrp());
		param.put("grpstep",board.getGrpstep());
		template.update(sql, param);
		
	}
	
	public void update(Board board) {
		String sql = "update board set name = :name, pass = :pass, subject = :subject, file1 = :fileurl, content = :content where num = :num"; 
		SqlParameterSource proparam = new BeanPropertySqlParameterSource(board);
		template.update(sql, proparam);
	}
	public void delete(Integer num) {
		param.clear();
		param.put("num", num);
		template.update("delete from board where num = :num", param);
	}

}
