package com.springjdbc.dao;

import java.util.List;

import com.springjdbc.model.Contact;

public interface ContactDao {
	List<Contact> findAll();
	List<Contact> findAllWithDetail();
	List<Contact> findByFirstName(String firstName);
	String findLastNameById(Long id);
	String findFirstNameById(int id);
	void insert(Contact contact);
	void update(Contact contact);
	void delete(Long contactId);
}
