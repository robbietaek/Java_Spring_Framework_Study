package xml;

public class LoggingAdvice {
	public void before() {
		System.out.println("[LA]메서드 실행 전 전처리 수행 기능");
	}
	//핵심로직 정상 종료 후 리턴값 : ret
	public void afterReturning(Object ret) {			//핵심 로직에서 전달한 것이 ret다.
		System.out.println("[LA]메서드 정상 처리 후 수행함. ret = "+ret);
	}
	//핵심로직 예외 실행 후 리턴 값  : ex
	public void afterThrowing(Throwable ex) {
		System.out.println("[LA]메서드 예외 발생 후 수행함. 발생 예외 : "+ ex.getMessage());
	}
	//결과적으로 수행됨
	public void afterFinally() {
		System.out.println("[LA]메서드 실행 후 수행함");
	}
}
