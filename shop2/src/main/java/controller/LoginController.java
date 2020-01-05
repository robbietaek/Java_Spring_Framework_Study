package controller;

import javax.servlet.http.HttpSession;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;

public class LoginController {
	private ShopService shopService;
	private Validator validator;
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	@GetMapping					//Get 방식으로 들어왔을 때 이렇게 호출됨 4.0이후 버전부터 가능 @RequestMapping(method=RequestMethod.GET) 이거였음
	public String loginForm(Model model) {
		model.addAttribute(new User());
		return "login";
	}
	
	@PostMapping
	public ModelAndView login(User user, BindingResult bresult, HttpSession session) {		//session에 전달해준다.
		ModelAndView mav = new ModelAndView();
		validator.validate(user, bresult);
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		try {
			User dbuser = shopService.getUser(user.getUserid());		//에러날 시 catch로 이동함
			if(user.getPassword().equals(dbuser.getPassword())) {	//해당 아이디의 비밀번호와 실제 DB 에 있는 비밀번호가 같을 때 
				session.setAttribute("loginUser", dbuser);		//loginUser 라는 이름으로 세션에 등록을 해준다.
			}else {		//비밀번호가 틀린경우다.
				bresult.reject("error.login.password");		//비밀번호를 확인하세요 라는 메세지를 등록해준다.
				mav.getModel().putAll(bresult.getModel()); 	//mav 에 모든 bresult값을 전달해준다.
				return mav;
			}
		}catch(EmptyResultDataAccessException e) {		//DB에 조회된 레코드가 없는 경우다.
			bresult.reject("error.login.id");
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		mav.setViewName("loginSuccess");		//모든 게 성공하면 loginSuccess 페이지를 전달해준다.
		return mav;	//리턴
	}
}
