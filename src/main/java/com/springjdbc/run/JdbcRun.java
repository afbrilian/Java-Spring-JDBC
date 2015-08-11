package com.springjdbc.run;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.springjdbc.dao.ContactDao;

public class JdbcRun {
	public static void main(String[] args){
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:META-INF/config/datasource-dbcp.xml");
		ctx.refresh();
		ContactDao contactDao = ctx.getBean("contactDao", ContactDao.class);
 		System.out.println("First name for contact id 1 is: " +	contactDao.findFirstNameById(100));
 		ctx.close();
	}
}
