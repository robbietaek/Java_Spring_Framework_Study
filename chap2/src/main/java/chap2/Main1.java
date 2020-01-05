package chap2;

import java.util.Arrays;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main1 {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = //컨테이너
				new GenericXmlApplicationContext("classpath:applicationContext.xml");
		Project pro = ctx.getBean("project",Project.class);
		pro.build();
//		pro = new Project();	//그냥 객체를 만들것이기 때문에 결국
//		pro.build(); 			NullPointException 의 에러가 발생하게 된다.
		BuildRunner br = ctx.getBean("runner",BuildRunner.class);
		br.build(Arrays.asList("src/", "srcResource/"),"bin2/");
		
//		br = new BuildRunner();
//		br.build(Arrays.asList("src/", "srcResource/"),"bin2/");
		
		WriteImpl wi = ctx.getBean("write",WriteImpl.class);
		wi.write();
	}
}
