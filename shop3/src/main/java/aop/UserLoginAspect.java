package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

@Component
@Aspect
@Order(1)
public class UserLoginAspect {

	@Around		//advice : 핵심로직 전,후에 다 처리할거야 
	("execution(* controller.User*.check*(..)) && args(..,session)")		//세션만 있을 때(조건문이라고 생각하면 됨)
	//pointcut: 핵심로직을 설정 			패키지.유저로 시작하고 아무거나 상관없이 전부 다.체크로시작하는 메서드 이름 전부 다 && 매개변수가 세션으로 끝나는 것들
	public Object userLoginCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("로그인 후 거래하세요","login.shop");
		}
		
		Object ret = joinPoint.proceed();
		return ret;
	}
	
	@Around		//advice : 핵심로직 전,후에 다 처리할거야 
	("execution(* controller.User*.check*(..)) && args(id,session)")		//아이디와 세션이 있을 떄(조건문이라고 생각하면 됨)
	public Object userIdCheck(ProceedingJoinPoint joinPoint, String id, HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("2. 로그인 후 거래하세요","login.shop");
		}
		
		if(!loginUser.getUserid().equals("admin") && !loginUser.getUserid().equals(id)) {
			throw new LoginException("본인정보만 확인가능합니다.","main.shop");
		}
		
		Object ret = joinPoint.proceed();
		return ret;
	}
}
