package main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration		//내 클래스가 바로 컨테이너를 설정하는 설정을 위한 자바소스다. 이 안에서 객체를 만들겠다.xml 설정 대체하는 소스
@ComponentScan(basePackages = {"annotation","main"})	//annotation 패키지 위에 Component 를 발견하면 내꺼야!
@EnableAspectJAutoProxy		//AOP 설정

public class AppCtx {
//	@Bean		//<bean id = 'memberService2' class = "MemberService"/>
//	public MemberService memberService2() {
//		return new MemberService();
//	}
	
}
