package org.kevi;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringTest {
	public static void main(String[] args) throws Exception {
		// 使用 classpath* 装载多份配置文件。
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");
		//ApplicationContext ctx = new FileSystemXmlApplicationContext("file:bean.xml");
		System.out.println(ctx);
		SpringTest t = (SpringTest)ctx.getBean("t");
		SpringTest t1 = ctx.getBean("t1", SpringTest.class);
		SpringTest t2 = ctx.getBean("t2", SpringTest.class);
		System.out.println(t==t1);
		System.out.println(t==t2);
		System.out.println(t1==t2);
		DispatcherServlet a;
	}
	
	public void test() {
		System.out.println("test...");
	}
}