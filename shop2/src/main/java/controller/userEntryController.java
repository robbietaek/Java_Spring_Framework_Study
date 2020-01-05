package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;
import util.UserValidator;

public class userEntryController {
	private ShopService shopService;
	private UserValidator userValidator;
	//itemDao 객체를 저장하고 있는 ShopService 객체 주입
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	public void setUserValidator(UserValidator userValidator) {
		this.userValidator = userValidator;
	}
	@RequestMapping(method=RequestMethod.GET)
	public String userEntryForm() {
		return "userEntry"; //view만 설정
	}
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView userEntry(User user, BindingResult bindResult) {
		//user : 파라미터값을 저장하고 있는 객체
		ModelAndView mav = new ModelAndView();
		userValidator.validate(user, bindResult);
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		try {
			shopService.insertUser(user);
			mav.addObject("user",user);
	//DataIntegrityViolationException : spring jdbc에서 발생되는 예외
	// 키 중복 오류
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindResult.reject("error.duplicate.user");
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		mav.setViewName("userEntrySuccess");
		return mav;
	}
	@ModelAttribute
	public User getUser() {
		return new User();
	}
	@InitBinder //파라미터값 형변환
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		/*
		 * Date.class : 형변환 대상이 되는 자료형
		 * format : 형식지정
		 * 	true/false : 비입력허용/비입력불허
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,false));
	}
}
