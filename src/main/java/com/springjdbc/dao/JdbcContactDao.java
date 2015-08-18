package com.springjdbc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.springjdbc.model.Contact;
import com.springjdbc.sqlhandling.MySQLErrorCodesTranslator;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcContactDao implements ContactDao, InitializingBean {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public void afterPropertiesSet() throws Exception {
		if (dataSource == null) {
			throw new BeanCreationException("Must set dataSource on ContactDao");
		}
		/*use parameter jdbc template*/
		if (namedParameterJdbcTemplate == null) {
			throw new BeanCreationException("Null NamedParameterJdbcTemplate on ContactDao");
		}
		/*Use NON-parameter jdbc template
		if (jdbcTemplate == null) {
			throw new BeanCreationException("Null JdbcTemplate on ContactDao");
		}
		*/
	}

	public void setDataSource(DataSource dataSource) {
		/*use parameter jdbc template*/
		this.dataSource = dataSource;
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		
		/*Use NON-parameter jdbc template
		this.dataSource = dataSource;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		
		MySQLErrorCodesTranslator errorTranslator = new MySQLErrorCodesTranslator();
		
		errorTranslator.setDataSource(dataSource);
		
		jdbcTemplate.setExceptionTranslator(errorTranslator);
		
		this.jdbcTemplate = jdbcTemplate;
		*/
	}

	public List<Contact> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contact> findByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String findLastNameById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String findFirstNameById(int id) {
		/*use parameter jdbc template*/
		String sql = "select first from employees where id = :id";
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("id", id);
		
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
		
		/*Use NON-parameter jdbc template
		return jdbcTemplate.queryForObject(
				"select first from employees where id = ?",
				new Object[] { id }, String.class);
		*/
	}

	public void insert(Contact contact) {
		// TODO Auto-generated method stub

	}

	public void update(Contact contact) {
		// TODO Auto-generated method stub

	}

	public void delete(Long contactId) {
		// TODO Auto-generated method stub

	}

}
