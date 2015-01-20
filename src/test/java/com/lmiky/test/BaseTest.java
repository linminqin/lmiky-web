package com.lmiky.test;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.lmiky.jdp.database.dao.BaseDAO;

/**
 * 测试积累
 * @author lmiky
 * @date 2013-4-15
 */
@ContextConfiguration(locations={
		"classpath:config/applicationContext*.xml"})
public class BaseTest extends AbstractJUnit4SpringContextTests {
	public BaseTest() {
		PropertyConfigurator.configure(BaseTest.class.getClassLoader().getResource("config/log4j.properties")); 
	}
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		PropertyConfigurator.configure(BaseTest.class.getClassLoader().getResource("config/log4j.properties")); 
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/applicationContext*.xml");  
		BaseDAO baseDAO = (BaseDAO) applicationContext.getBean("baseDAO");
		System.out.println(baseDAO);
	}
}
