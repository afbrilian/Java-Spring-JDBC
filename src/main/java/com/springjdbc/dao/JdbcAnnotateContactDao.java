package com.springjdbc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.springjdbc.model.Contact;
import com.springjdbc.model.ContactTelDetail;
import com.springjdbc.query.InsertContact;
import com.springjdbc.query.InsertContactTelDetail;
import com.springjdbc.query.SelectAllContacts;
import com.springjdbc.query.SelectContactByFirstName;
import com.springjdbc.query.UpdateContact;

@Repository("contactDaoAnnotate")
public class JdbcAnnotateContactDao implements ContactDao {

	private static final Log LOG = LogFactory.getLog(JdbcAnnotateContactDao.class);
	
	private DataSource dataSource;
	private SelectAllContacts selectAllContacts;
	private SelectContactByFirstName selectContactByFirstName;
	private UpdateContact updateContact;
	private InsertContact insertContact;
	private InsertContactTelDetail insertContactTelDetail;
	
	public JdbcAnnotateContactDao() {}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.selectAllContacts = new SelectAllContacts(dataSource);
		this.selectContactByFirstName = new SelectContactByFirstName(dataSource);
		this.updateContact = new UpdateContact(dataSource);
		this.insertContact = new InsertContact(dataSource);
	}
	
	public DataSource getDataSource(){
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
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("first_name", contact.getFirstName());
		paramMap.put("last_name", contact.getLastName());
		paramMap.put("birth_date", contact.getBirthDate());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertContact.updateByNamedParam(paramMap, keyHolder);
		contact.setId(keyHolder.getKey().longValue());
		LOG.info("New contact inserted with id: " + contact.getId());
	}

	public void update(Contact contact) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("first_name", contact.getFirstName());
		paramMap.put("last_name", contact.getLastName());
		paramMap.put("birth_date", contact.getBirthDate());
		paramMap.put("id", contact.getId());
		updateContact.updateByNamedParam(paramMap);
		LOG.info("Existing contact updated with id: " + contact.getId());
		
	}

	public void delete(Long contactId) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertWithDetail(Contact contact){
		//batch insert is not a thread safe, only set the datasource when needed
		this.insertContactTelDetail = new InsertContactTelDetail(dataSource);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("first_name", contact.getFirstName());
		paramMap.put("last_name", contact.getLastName());
		paramMap.put("birth_date", contact.getBirthDate());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertContact.updateByNamedParam(paramMap, keyHolder);
		contact.setId(keyHolder.getKey().longValue());
		LOG.info("New contact inserted with id: " + contact.getId());
		
		List<ContactTelDetail> contactTelDetails = contact.getContactTelDetails();
		if (contactTelDetails != null) {
			for (ContactTelDetail contactTelDetail: contactTelDetails) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("contact_id", contact.getId());
				paramMap.put("tel_type", contactTelDetail.getTelType());
				paramMap.put("tel_number", contactTelDetail.getTelNumber());
				insertContactTelDetail.updateByNamedParam(paramMap);
			}
		}
		insertContactTelDetail.flush();
		
//		BatchSqlUpdate class will queue up the insert operations and submit them to the database in batch. Every time the
//		number of records equals the batch size, Spring will execute a bulk insert operation to the database for the pending records.
		
	}
	
}
