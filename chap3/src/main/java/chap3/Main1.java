package chap3;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericXmlApplicationContext;



/*
 * 기본 어노테이션
 * 1. 객체생성 : @Component
 * xml : <contrext : component-scan base-package="chap3"/>
 * 2. 객체 주입 : 
 * @Autowired : 객체 선택의 기준이 클래스의 자료형임
 * 				(required=false) : 객체가 없는 경우를 가능하도록 하는경우 Null로 처리함
 * @Resource : 객체 중 이름이 해당 하는 객체를 주입
 * @Required : 얘를 쓸바엔 Autowired를 씀, 객체 선택의 기준이 클래스의 자료형임 . 반드시 객체가 있어야함 없으면 에러가 남
 * 
 * 3. 그 외
 * @PostConstruct : 객체 생성이 완료된 후에 호출되는 메서드의 위에 설정
 * @Qualifier : Qualifier : 객체의 이름을 별명으로 설정해줄 수 있다.
 * @Scope(..) : 생성된 객체의 지속가능한 영역을 설정 @Component와 함께 사용됨.
 * 
 */



public class Main1 {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = // 컨테이너 xml을 읽어서 주입하고 다 처리를 해준 뒤에 init 메서드를 호출 한 뒤에 WorkUnit을 호출하는 것이다.
				new GenericXmlApplicationContext("classpath:annotation.xml");
		Executor exec = ctx.getBean("executor",Executor.class);
		exec.addUnit(new WorkUnit());
		exec.addUnit(new WorkUnit());
		
		HomeController home = ctx.getBean("homeController",HomeController.class);
		home.checkSensorAndAlarm();
		System.out.println("침입없음 ===============");
		System.out.println("=============");
		
		InfraredRaySensor sensor = ctx.getBean("windowSensor",InfraredRaySensor.class);
		sensor.foundObject();
		
		InfraredRaySensor dsensor = ctx.getBean("doorSensor",InfraredRaySensor.class);
		dsensor.foundObject();
		home.checkSensorAndAlarm();
		
	}
}
