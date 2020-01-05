package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

@Controller		//@Component+Controller 기능
@RequestMapping("item")		//item/xxx.shop
public class ItemController {
	@Autowired
	private ShopService service;
	
	@RequestMapping("list")		//item/list.shop 요청
	public ModelAndView list() {
		//itemList : item테이블의 모든 레코드와 모든 컬럼을 정보를 저장
		List<Item> itemList = service.getItemList();	//내 모든 아이템 정보를 리스트 형 객체로 화면에 뿌려준다.
		ModelAndView mav = new ModelAndView();		//뷰 : item/list
		mav.addObject("itemList",itemList);
		return mav;			//WEB-INF/view/item/list.jsp
	}
	
	@RequestMapping("create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("item/add");
		mav.addObject(new Item());
		return mav;
	}
	
	@RequestMapping("register")
	public ModelAndView register(@Valid Item item, BindingResult bresult,HttpServletRequest request) {		//valid를 빼면 공백을해도 됨
		ModelAndView mav = new ModelAndView("item/add");
		if(bresult.hasErrors()) {		//에러가 있을 시 
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		service.itemCreate(item,request);		//아이템에 있는걸 모두 DB에 넣는다.
		mav.setViewName("redirect:/item/list.shop");
		return mav;
	}
	
	@RequestMapping("update")
	public ModelAndView update(@Valid Item item, BindingResult bresult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/edit");
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		service.itemUpdate(item,request);
		mav.setViewName("redirect:/item/list.shop");
		return mav;
	}

	
	//@RequestMapping("detail")
	//@RequestMapping("*") //그 외의 모든 요청정보를 해줄 때 사용. 이럴 때는 URL에 아무것도 사용하면 안됨. 클릭한 곳 그대로 설정해줌 GetMapping 과 동일함
	@GetMapping("*")
	public ModelAndView itemSelect(String id) {
		Item item = service.getItem(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("item",item);
		return mav;	
	}
	
	@RequestMapping("delete")
	public ModelAndView delete(String id) {
		ModelAndView mav = new ModelAndView();
		service.itemDelete(id);
		mav.setViewName("redirect:/item/list.shop");
		return mav;
	}
		
}
