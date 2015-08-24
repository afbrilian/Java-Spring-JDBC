# Java-Spring-JDBC wiki!
This is the example of how java interact with database (I am using mysql) using spring JDBC

## Dependency
* group mysql; artifact: mysql-connector-java; version: 5.1.29
* group: springframework; artifact: spring-core; version: 4.1.1.RELEASE
* group springframework; artifact: spring-context; version: 4.0.0.RELEASE
* group springframework; artifact: spring-jdbc; version: 4.0.2.RELEASE

## How To Use
* Install maven, see: [https://maven.apache.org/users/index.html](https://maven.apache.org/users/index.html)
* Install mysql (you could use xampp though)

## You Must Understand
* maven, used for Object Mapping (Dependency)
* mySql, Relational Database
* DAO (Data Access Object) Pattern
* Spring Dependency Injection

## Database Settings

![database structure](https://cloud.githubusercontent.com/assets/11399839/9414014/98fbc292-485f-11e5-9adf-f730242a6dd2.PNG)

![contact](https://cloud.githubusercontent.com/assets/11399839/9414079/fb5a1fc4-485f-11e5-995b-47b2511c973e.PNG)
![contactteldetail](https://cloud.githubusercontent.com/assets/11399839/9414080/fba9c83a-485f-11e5-9bb4-1b27549cdfc2.PNG)

## Description
It will be easier for me to explain by using the commit history :D
* First Commit [8ecb4b2](https://github.com/afbrilian/Java-Spring-JDBC/commit/8ecb4b258d91d247d2cfcadd49cc304606901b96)

just like the commit message said, "first commit, still without Spring (pure JDBC)". Define the mysql connector in pom.xml.
Create the model class, they are Contact.java and ContactTelDetail.java. Create the interface for contactDao. aaand finally create the implementation for ContactDao, in my case its name is PlainContactDao (should be PlainContatImpl though, sorry for my bad namming, too lazy to change it LOL).
now lets continue... in this PlainContactDao class there is static that define the mysql connector driver so everytime this class is invoked by other class the static will run first and it will throws class not found exception if it cannot find the driver class.
Then we define the getConnection() and closeConnection() method, you know its for the connectivity to the database.
So as you can see in every method that need to access the database it is a pain in the ass that you have to open the connection the creating a statement, execute the query aand close it every damn time in every method.
But the good news, dont worry, there is spring framework to make our work easier by eliminating this open and close connection things *YAY

* Second Commit [41ea67](https://github.com/afbrilian/Java-Spring-JDBC/commit/41ea67dc3a6e3f879173f62c9961a7feae2fccbe)

There is the spring framework to save the day!! define the dependency for the spring DriverManagerDataSource(not the commons-dbcp, it is outdated and i have changed it in my latest commit) and spring framework in pom.xml.
create the xml file for spring dependency (from now on forward I will stick to the datasource-dbcp.xml), define the datasource bean which use the org.apache.commons.dbcp.BasicDataSource class.
the datasource bean use destroy-method close so the connection to the database will be closed everytime the process is complete, and define the other properties by loaded it from .properties file which located in :
```
  <context:property-placeholder location="classpath:META-INF/config/jdbc.properties" />
```
as you can see the jdbc.properties file contains the information that the datasource needs to connect to the database.
Then define the contactDao bean which is the implementation of the contactDao and set/referenced the property of datasource to the dataSource bean.
Now lets go to the JdbcContactDao which is one again is the implementation for the contactDao. In this file we declared the datasource and jdbctemplate properties, to make it simple the datasource is the source connection to the database and jdbctemplate will be the executor of the query. Simple isnt it.
Spring will inject the datasource as in the datasource-dbcp.xml file, in the setDataSource method the jdbcTemplate will use this source to connect to the database.
now lets see one of its method:
```
  public String findFirstNameById(int id) {
		return jdbcTemplate.queryForObject(
				"select first from employees where id = ?",
				new Object[] { id }, String.class);
  }
```
it should be pretty straight forward, this method use jdbctemplate to execute the query.
now lets go the next commit.

* third commit [92d16fe](https://github.com/afbrilian/Java-Spring-JDBC/commit/92d16fe05d0f26b68caea5deaf8b3e929374a748)

there isnt any much changes in this commit other than we replaced the jdbctemplate with the namedparameterjdbctemplate. as you can see in findFirstNameById() method the namedparameterjdbctemplate takes map collection as the parameter, for me its very nice since the code become more tidy.

* fourth commit [70d4015](https://github.com/afbrilian/Java-Spring-JDBC/commit/70d40159014baeb5d760c01506abbac3cfa9d768)

Here we manipulate the result from the database, mainly when the result is more than 1. We use the RowMapper<T> in ContactMapper inner class (you could separate this class) and the overriden method which is mapRow will act as the contact mapper and voila there is the list of contact you are searching for.

* fifth commit [871d9e3](https://github.com/afbrilian/Java-Spring-JDBC/commit/871d9e36482e4a3c3bf97a7c01dba5da4c679739)

My bad really, from the last commit I was actually using the wrong database, thats why it was feel weird lol.

* sixth commit [bc18214](https://github.com/afbrilian/Java-Spring-JDBC/commit/bc182142cd268c9d420d122106acc97fff60df45)

Now after set the right database ^^! this commit will map the one to many relationship, from the example, one contact could have more than one contactTelDetail which implemented in the Contact model using List.
Just need to add one more inner class (yes! you could separate this class!) that implements ResultSetExtractor<List<T>>, this class have one overriden method which is extractData.
this method is easy to understand right? we just need to create a Map that consist of id and contact object, if there isnt any contact object in the map, it will instatiate the contact and put it in the map with the contact id as the map key.
after that, the map will continue by adding the contactTelDetail object to the contact object (if you wondering why there isnt any loop to add the contactTelDetail to a list, just run the query so you will know the resultset that returned from the database).
after all the process done, it will return the map values by using map.values(). and voila, you get both contact and contactTelDetail object.

* seventh commit [9f96a2b](https://github.com/afbrilian/Java-Spring-JDBC/commit/9f96a2bedf6f777e2961d965c5dd0c07406fb832)

In this commit we will use the annotation, so first add the @repository("contactDaoAnnotate") annotation that will indicate the bean names. in datasource-dbcp.xml add the:
```
<context:component-scan base-package="com.springjdbc.dao" />
```
so spring could locate the bean, and use @autowired to inject the datasource. Now lets see the SelectAllContacts class which extends MappingSqlQuery<T>, pretty straight forward isnt it? the constructor will just execute the query and map the returned dataset. easy.
the implementation class (JdbcAnnotateContactDao class) will run the execute() command to run the query.

* eight commit (ignore this)

* ninth commit [50d361b](https://github.com/afbrilian/Java-Spring-JDBC/commit/50d361bf0c82be354e9352c34e079e4bf44410a2)

Now lets see if we want to find data using parameter using spring framework, we will use MappingSqlQuery<T>.
The difference with the previous commit is we need to send a named parameter using Map and execute it with executeByNamedParam().
Aand in the SelectContactByFirstName class we need to declare the parameter using super.declareParameter() method so the query will be complete and we will get the right result from the query.

And for the other commit? it is almost the same but just using the different spring class.

## Summary
Spring framework make our life easier!! and consider using querydsl as the DSL for query in many database (please wait for other repository which use querydsl ^^).

Any question or improvement for this explanation is welcome :)
>> Big thanks to Apress.Spring.4th.Edition
