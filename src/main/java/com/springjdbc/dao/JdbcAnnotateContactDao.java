package com.springjdbc.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springjdbc.model.Contact;
import com.springjdbc.query.SelectAllContacts;

@Repository("contactDaoAnnotate")
public class JdbcAnnotateContactDao implements ContactDao {
	
	private DataSource dataSource;
	private SelectAllContacts selectAllContacts;
	
	public JdbcAnnotateContactDao() {}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.selectAllContacts = new SelectAllContacts(dataSource);		
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public List<Contact> findAll() {
		return selectAllContacts.execute();
	}

	public List<Contact> findAllWithDetail() {
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
