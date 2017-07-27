package springframe.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tech.domain.People;
import org.tech.service.impl.PeopleService;
/**
 * 单元测试
 * 测试service 层
 * @author fangyunhe
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//配置了@ContextConfiguration注解并使用该注解的locations属性指明spring和配置文件之后，
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class PeopleServiceUnit {
	
	 @Autowired
	 private PeopleService peopleService;
	 
	 @Test
	 public void test(){
		 People people = new People();
		 people.setName("t22");
		 System.out.println(peopleService.add(people));
	 }
}
