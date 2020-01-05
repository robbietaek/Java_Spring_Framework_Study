package chap3;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component // homeController 이름의 객체로 생성되고 , 컨테이너에 저장
public class HomeController {

	private AlarmDevice alarmDevice;
	private Viewer viewer;
	@Resource(name = "camera1") // 이름으로 객체를 주입
	private Camera camera1;
	@Resource(name = "camera2")
	private Camera camera2;
	@Resource(name = "camera3")
	private Camera camera3;
	@Resource(name = "camera4")
	private Camera camera4;

	private List<InfraredRaySensor> sensors;
	@Autowired(required = false) // Recorder 라는 객체가 없으면 null 로 주입, 없어도 에러는 내지 마
	private Recorder recorder;

	@Autowired // 컨테이너에서 AlarmDevice 객체와, Viewer 객체
	public void prepare(AlarmDevice alarmDevice, Viewer viewer) {
		this.alarmDevice = alarmDevice;
		this.viewer = viewer;
	}

	@PostConstruct	//객체의 생성시 모든 객체의 주입이 완료된 후. 객체 생성 이후
	public void init() {
		System.out.println("init 메서드 호출");
		viewer.add(camera1);
		viewer.add(camera2);
		viewer.add(camera3);
		viewer.add(camera4);
		viewer.draw();
	}

	@Autowired			//주입이 되는 것이니 init 보다 먼저 실행됨
	@Qualifier("instrusionDetection")		//별명설정
	public void setSensors(List<InfraredRaySensor> sensors) {
		System.out.println("Sensors 메서드 호출");
		this.sensors = sensors;
		for (InfraredRaySensor s : sensors) {
			System.out.println("센서등록" + s);
		}
	}

	public void checkSensorAndAlarm() {
		for (InfraredRaySensor s : sensors) {
			if (s.isObjectFounded()) {
				alarmDevice.alarm(s.getName());
			}
		}
	}
}
