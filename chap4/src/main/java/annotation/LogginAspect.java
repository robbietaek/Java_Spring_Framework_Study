package annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect		//내가 바로 Aspect 클래스야
@Order(3)	//3번째로 사용 될 AOP 클래스야 (순서)
public class LogginAspect {
	final String publicMethod="execution(public * annotation..*(..))";
	
	@Before(publicMethod)
	public void before() {
		System.out.println("[LA] 메서드 실행 전 실행");
	}
	@AfterReturning(pointcut = publicMethod, returning = "ret")
	public void afterReturning(Object ret) {
		System.out.println("[LA] 메서드 정상 종료 후 실행. 리턴값 ="+ret);
	}
	@AfterThrowing(pointcut=publicMethod,throwing="ex")
	public void afterThrowing(Throwable ex) {
		System.out.println("[LA] 메서드 예외 종료 후 실행. 예외메세지 = "+ex.getMessage());
	}
	
	@After(publicMethod)
	public void afterfinally() {
		System.out.println("[LA] 메서드 종료 후 실행");
	}
	
}
