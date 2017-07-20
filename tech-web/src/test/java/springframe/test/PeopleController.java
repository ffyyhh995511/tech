package springframe.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tech.domain.People;
import org.tech.service.impl.PeopleService;

@RunWith(SpringJUnit4ClassRunner.class)
//配置了@ContextConfiguration注解并使用该注解的locations属性指明spring和配置文件之后，
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class PeopleController {
	
	 @Autowired
	 private PeopleService peopleService;
	 
	 @Test
	 public void test(){
		 People people = new People();
		 people.setName("t");
		 System.out.println(peopleService.add(people));
	 }
}
