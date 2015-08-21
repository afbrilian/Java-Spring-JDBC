package com.springjdbc.run;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.springjdbc.dao.ContactDao;
import com.springjdbc.model.Contact;
import com.springjdbc.model.ContactTelDetail;

public class JdbcRun {
	public static void main(String[] args){
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:META-INF/config/datasource-dbcp.xml");
		ctx.refresh();
		ContactDao contactDao = ctx.getBean("contactDao", ContactDao.class);
 		
		/*
		System.out.println("First name for contact id 1 is: " +	contactDao.findFirstNameById(1));
 		
 		List<Contact> contacts = contactDao.findAll();
 		for(Contact contact : contacts){
 			System.out.println(contact);
 			System.out.println();
 		}
 		
 		List<Contact> contactsWithDetail = contactDao.findAllWithDetail();
		for (Contact contact : contactsWithDetail) {
			System.out.println(contact);
			if (contact.getContactTelDetails() != null) {
				for (ContactTelDetail contactTelDetail : contact
						.getContactTelDetails()) {
					System.out.println("---" + contactTelDetail);
				}
			}
			System.out.println();
		}
		*/
 		
		//Use Spring annotation
		ContactDao contactDaoAnnotate = ctx.getBean("contactDaoAnnotate", ContactDao.class);		
		List<Contact> contactsAll = contactDaoAnnotate.findAll();
		listContacts(contactsAll);
		
		System.out.println("====================");
		List<Contact> contactsByFirstName = contactDaoAnnotate.findByFirstName("chris");
		listContacts(contactsByFirstName);
		
		System.out.println("====================");
		Contact ctc = new Contact();
		ctc.setId(1l);
		ctc.setFirstName("Chris");
		ctc.setLastName("John");
		ctc.setBirthDate(new Date((new GregorianCalendar(1977, 10, 1)).getTime().getTime()));
		contactDaoAnnotate.update(ctc);
		System.out.println("Check DB for id 1");
		
		System.out.println("====================");
		
 		ctx.close();
	}

	private static void listContacts(List<Contact> contacts) {
		for (Contact contact : contacts) {
			System.out.println(contact);
			if (contact.getContactTelDetails() != null) {
				for (ContactTelDetail contactTelDetail : contact
						.getContactTelDetails()) {
					System.out.println("---" + contactTelDetail);
				}
			}
			System.out.println();
		}
	}

}
//292