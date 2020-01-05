package dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import logic.Item;

public class ItemDao {
	private NamedParameterJdbcTemplate template;
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	public List<Item> list() {
		RowMapper<Item> mapper = new BeanPropertyRowMapper<Item>(Item.class);
		return template.query("select * from item", mapper);
	}
}
