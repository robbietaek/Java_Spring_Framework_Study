package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import exception.CartEmptyException;
import logic.Cart;
import logic.Item;
import logic.ItemSet;
import logic.Sale;
import logic.SaleItem;
import logic.ShopService;
import logic.User;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private ShopService service;

	@RequestMapping("cartAdd")
	public ModelAndView add(String id, Integer quantity, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Item item = service.getItem(id);
		Cart cart = (Cart) session.getAttribute("CART");
		if (cart == null) { // 장바구니가 없다
			cart = new Cart(); // session에 등록된 cart
			session.setAttribute("CART", cart);
		}

		cart.push(new ItemSet(item, quantity));
		mav.addObject("message", item.getName() + ":" + quantity + "개 장바구니 추가");
		mav.addObject("cart", cart);
		return mav;
	}

	@RequestMapping("cartDelete")
	public ModelAndView delete(int index, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart) session.getAttribute("CART");
		ItemSet itemset = null;
		try {
			itemset = cart.getItemSetList().remove(index);
			mav.addObject("message", itemset.getItem().getName() + "가 제거되었습니다.");
		} catch (Exception e) {
			mav.addObject("message", "상품이 삭제되지 않았습니다.");
		}
		mav.addObject("cart", cart);
		return mav;

	}

	@RequestMapping("cartView")
	public ModelAndView view(HttpSession session) throws CartEmptyException {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart) session.getAttribute("Cart");
		if (cart == null || cart.getItemSetList().size() == 0) {
			throw new CartEmptyException("장바구니에 상품이 없습니다.", "../item/list.shop");
		}
		mav.addObject("cart", cart);
		return mav;
	}
	
	//주문 전 확인
	//반드시 로그인이 필요
	//장바구니에 상품이 존재해야함
	@RequestMapping("checkout")
	public String checkout(HttpSession session) {
		return "cart/checkout";
	}
	
	@RequestMapping("end")
	public ModelAndView checkend(HttpSession session) {		//aop의 대상이 되게 하려고 check 로 만든 것이다.
		ModelAndView mav = new ModelAndView();
		Cart cart = (Cart)session.getAttribute("CART");
		User loginUser = (User)session.getAttribute("loginUser");
		Sale sale = service.checkend(loginUser,cart);
		long total = cart.getTotal();
		session.removeAttribute("CART");
		mav.addObject("sale",sale);
		mav.addObject("total",total);
		return mav;
	}
}
