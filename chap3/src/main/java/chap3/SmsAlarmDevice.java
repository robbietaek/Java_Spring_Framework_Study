package chap3;

import org.springframework.stereotype.Component;

@Component		//애가 주입이 됨
public class SmsAlarmDevice implements AlarmDevice {
	public void alarm(String name) {
		System.out.println(name + "에서 침임이 탐지됨. 신고요망");
	}
}
