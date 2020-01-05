package annotation;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import xml.Member;
import xml.UpdateInfo;

@Component("memberService")

public class MemberService {
	public void regist(Member member) {
		System.out.println("MemberService.regist() 메서드 실행");
	}
	public boolean update(String memberId, UpdateInfo info) {
		System.out.println("MemberService.update() 메서드 실행");
		return true;
	}
	public boolean delete(String id, String str) {
		System.out.println("MemberService.delete() 메서드 실행");
		return false;
	}
}
