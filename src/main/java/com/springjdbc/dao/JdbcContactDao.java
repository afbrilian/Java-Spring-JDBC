package com.springjdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.springjdbc.model.Contact;
import com.springjdbc.model.ContactTelDetail;
import com.springjdbc.sqlhandling.MySQLErrorCodesTranslator;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
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
	
	private static final class ContactMapper implements RowMapper<Contact>{

		public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
			Contact contact = new Contact();
			contact.setId(rs.getLong("ID"));
			contact.setFirstName(rs.getString("FIRST_NAME"));
			contact.setLastName(rs.getString("LAST_NAME"));
			contact.setBirthDate(rs.getDate("BIRTH_DATE"));
			return contact;
		}

	}
	
	private static final class ContactWithDetailExtractor implements ResultSetExtractor<List<Contact>>{

		public List<Contact> extractData(ResultSet rs) throws SQLException,	DataAccessException {
			Map<Long, Contact> map = new HashMap<Long, Contact>();
			Contact contact = null;
			while(rs.next()){
				Long id = rs.getLong("id");
				contact = map.get(id);
				
				if(contact == null){
					contact = new Contact();
					contact.setId(id);
					contact.setFirstName(rs.getString("first_name"));
					contact.setLastName(rs.getString("last_name"));
					contact.setBirthDate(rs.getDate("birth_date"));
					contact.setContactTelDetails(new ArrayList<ContactTelDetail>());
					map.put(id, contact);
				}
				
				Long contactTelDetailId = rs.getLong("contact_tel_id");
				
				if (contactTelDetailId > 0) {
					ContactTelDetail contactTelDetail = new ContactTelDetail();
					contactTelDetail.setId(contactTelDetailId);
					contactTelDetail.setContactId(id);
					contactTelDetail.setTelType(rs.getString("tel_type"));
					contactTelDetail.setTelNumber(rs.getString("tel_number"));
					contact.getContactTelDetails().add(contactTelDetail);
				}
				
			}
			return new ArrayList<Contact> (map.values());
		}
	}
	
	public List<Contact> findAll() {
		String sql = "select ID, FIRST_NAME, LAST_NAME, BIRTH_DATE from contact";
		return namedParameterJdbcTemplate.query(sql, new ContactMapper());
	}

	public List<Contact> findAllWithDetail() {
		String sql = "select c.id, c.first_name, c.last_name, c.birth_date"
				+ ", t.id as contact_tel_id, t.tel_type, t.tel_number from contact c "
				+ "left join contact_tel_detail t on c.id = t.contact_id";
		return namedParameterJdbcTemplate.query(sql, new ContactWithDetailExtractor());
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
		String sql = "select FIRST_NAME from contact where ID = :ID";
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("ID", id);
		
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
		
		/*Use NON-parameter jdbc template
		return jdbcTemplate.queryForObject(
				"select FIRST_NAME from contact where id = ?",
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
