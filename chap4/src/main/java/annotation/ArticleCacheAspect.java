package annotation;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import xml.Article;

@Component
@Aspect		//내가 바로 Aspect 클래스야
@Order(2)	//2번째로 사용 될 AOP 클래스야 (순서)
public class ArticleCacheAspect {
	private Map<Integer,Article> cache = new HashMap<Integer,Article>();
	@Around("execution(public * *..ReadArticleService.*(..))")		//시작과 끝에 쓰겠다
	public Object cache(ProceedingJoinPoint joinPoint) throws Throwable{
		Integer id = (Integer)joinPoint.getArgs()[0];	//핵심로직의 매개변수 목록들을 가져오겠다.
		System.out.println("[ACA] "+joinPoint.getSignature().getName()+"("+id+") 메서드 호출 전"); //핵심로직 메서드 이름을 가져오겠다.
		Article article = cache.get(id);		//캐시에 id 라는값이 있다? 없다? 실행을 했었냐 안했었냐
		
		if(article!= null) {		//그러한 객체는 등록된 적이 없으니 내 다음으로 가
			System.out.println("[ACA] cache 에서 Article["+id+"] 가져옴");
			return article;		//내 선에서 끝내겠다. Main 으로 넘겨줌
		}
		
		Object ret = joinPoint.proceed();
		System.out.println("[ACA] " + joinPoint.getSignature().getName()+"("+id+")");
		if(ret != null && ret instanceof Article) {	//ret가 null이 아닌데 전달받은 값이 Article 객체면  ACA에 추가한다.
			cache.put(id, (Article)ret);
			System.out.println("[ACA] cache에 Article["+id+"] 추가함");
		}
		return ret;		//다시 메인을 전달해준다.
	}
}
