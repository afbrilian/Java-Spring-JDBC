package com.springjdbc.run;

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
 		
 		ctx.close();
	}
}