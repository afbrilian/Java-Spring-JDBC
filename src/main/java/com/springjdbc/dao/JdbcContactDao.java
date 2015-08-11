package com.springjdbc.dao;

import java.util.List;

import javax.sql.DataSource;

import com.springjdbc.model.Contact;
import com.springjdbc.sqlhandling.MySQLErrorCodesTranslator;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcContactDao implements ContactDao, InitializingBean {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void afterPropertiesSet() throws Exception {
		if (dataSource == null) {
			throw new BeanCreationException("Must set dataSource on ContactDao");
		}
		if (jdbcTemplate == null) {
			throw new BeanCreationException("Null JdbcTemplate on ContactDao");
		}

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		MySQLErrorCodesTranslator errorTranslator = new MySQLErrorCodesTranslator();
		errorTranslator.setDataSource(dataSource);
		jdbcTemplate.setExceptionTranslator(errorTranslator);
		this.jdbcTemplate = jdbcTemplate;
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
		return jdbcTemplate.queryForObject(
				"select first from employees where id = ?",
				new Object[] { id }, String.class);
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
