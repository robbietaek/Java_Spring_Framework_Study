package chap1;

public class Greeter {
	private String format;

	public String greet(String guest) {
		return String.format(format, guest);
	}
	//format : %s를 시작합니다
	public void setFormat(String format) {
		this.format = format;
	}
}
