package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xml.Article;
import xml.Member;
import xml.MemberService;
import xml.ReadArticleService;
import xml.UpdateInfo;

public class Main3 {
	public static void main(String[] args) {
		String[] config = {"annotation.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(config);		//xml  형태
		ReadArticleService service = ctx.getBean("readArticleService",ReadArticleService.class);
		try {
			Article a1 = service.getArticleAndReadCnt(1);
			Article a2 = service.getArticleAndReadCnt(1);			//cache 에 저장된 값을 줬으므로
			System.out.println("[main] a1 == a2 : " + (a1 == a2));		//값이 같을 수 밖에 없다.
			service.getArticleAndReadCnt(0);	// ACA는 호출하지만 예외가 발생했기 때문에 예외종료로 바로 빠져버린다. proceed 가 이상하기 때문에
												// 실행하지 않고 예외 객체를 보내준다.
		}catch(Exception e) {
			System.out.println("[main]" + e.getMessage());
		}
		
		System.out.println("\n UpdateMemberInfoTrace Aspect 연습");
		annotation.MemberService msvc = ctx.getBean("memberService",annotation.MemberService.class);
		msvc.regist(new Member());
		msvc.update("hong", new UpdateInfo());
		msvc.delete("hong2", "test");
		
		System.out.println();
		main.MemberService msvc2 = ctx.getBean("memberService2",main.MemberService.class);
		msvc2.regist(new Member());
		msvc2.update("hong", new UpdateInfo());
		msvc2.delete("hong2", "test");
		
		
		
		
		
	}
}
