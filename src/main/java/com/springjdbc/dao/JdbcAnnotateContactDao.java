package com.springjdbc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springjdbc.model.Contact;
import com.springjdbc.query.SelectAllContacts;
import com.springjdbc.query.SelectContactByFirstName;

@Repository("contactDaoAnnotate")
public class JdbcAnnotateContactDao implements ContactDao {

	private static final Log LOG = LogFactory.getLog(JdbcAnnotateContactDao.class);
	
	private SelectAllContacts selectAllContacts;
	private SelectContactByFirstName selectContactByFirstName;
	
	public JdbcAnnotateContactDao() {}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.selectAllContacts = new SelectAllContacts(dataSource);
		this.selectContactByFirstName = new SelectContactByFirstName(dataSource);
	}
	
	public List<Contact> findAll() {
		return selectAllContacts.execute();
	}

	public List<Contact> findAllWithDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contact> findByFirstName(String firstName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("first_name", firstName);
		return selectContactByFirstName.executeByNamedParam(paramMap);
	}

	public String findLastNameById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String findFirstNameById(int id) {
		// TODO Auto-generated method stub
		return null;
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
