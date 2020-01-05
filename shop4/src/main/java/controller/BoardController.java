package controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.BoardException;
import logic.Board;
import logic.ShopService;

@Controller
@RequestMapping("board")
public class BoardController {

	@Autowired
	private ShopService service;

	// view : /WEB-INF/view/board/list.jsp
	@RequestMapping("list")
	public ModelAndView list(Integer pageNum, String searchtype, String searchcontent) {
		ModelAndView mav = new ModelAndView();

		if (pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		if (searchtype != null && searchtype.trim().equals("")) { // 입력내용 띄어쓰기?
			searchtype = null;
		}
		if (searchcontent != null && searchcontent.trim().equals("")) {
			searchcontent = null;
		}
		if (searchtype == null || searchcontent == null) { // 두개다 있어야지 검색되게
			searchtype = null;
			searchcontent = null;
		}

		int limit = 10; // 페이지당 보여지는 게시물 건수
		int listcount = service.boardcount(searchtype, searchcontent); // 전체 등록된 게시물 건수
		List<Board> boardlist = service.boardlist(pageNum, limit, searchtype, searchcontent); // 건수만큼 보드 객체를 가져와
		int maxpage = (int) ((double) listcount / limit + 0.95); // 마지막페이지, 최대페이지
		int startpage = (int) ((pageNum / 10.0 + 0.9) - 1) * 10 + 1; // 보여지는 첫번째 페이지
		int endpage = startpage + 9; // 보여지는 마지막 페이지
		if (endpage > maxpage) { // end 페이지가 max 페이지를 넘지 못하도록
			endpage = maxpage;
		}
		int boardno = listcount - (pageNum - 1) * limit; // 화면에 출력되는 게시물 번호 (가짜번호)
		mav.addObject("pageNum", pageNum);
		mav.addObject("maxpage", maxpage);
		mav.addObject("startpage", startpage);
		mav.addObject("endpage", endpage);
		mav.addObject("listcount", listcount);
		mav.addObject("boardlist", boardlist);
		mav.addObject("boardno", boardno);
		return mav;
	}

	@PostMapping("write")
	public ModelAndView setBoard(@Valid Board board, BindingResult bresult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		try {
			service.boardWrite(board, request);
			mav.setViewName("redirect:list.shop");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("게시물 등록에 실패했습니다", "write.shop");
		}
		return mav;
	}

	@GetMapping("*")
	public ModelAndView getBoard(Integer num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Board board = null;
		if (num == null) {
			board = new Board();
		} else {
			board = service.getBoard(num, request);
		}
		mav.addObject("board", board);
		return mav;
	}

	@RequestMapping("imgupload")
	public String imgupload(MultipartFile upload, String CKEditorFuncNum, HttpServletRequest request, Model model) {
		String path = request.getServletContext().getRealPath("/") + "board/imgfile/";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		// upload : 업로드된 이미지 정보 저장, 이미지 파일
		if (!upload.isEmpty()) {
			File file = new File(path, upload.getOriginalFilename()); // 업로드될 파일을 저장할 File 객체
			try {
				upload.transferTo(file); // 업로드 파일 생성
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fileName = "/shop4/board/imgfile/" + upload.getOriginalFilename(); // FullPath로 지정
		model.addAttribute("fileName", fileName);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		return "ckedit";
	}

	@PostMapping("reply")
	public ModelAndView reply(@Valid Board board, BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		if (bresult.hasErrors()) {
			Board dbBoard = service.getBoard(board.getNum());
			Map<String, Object> map = bresult.getModel();
			Board b = (Board) map.get("board");
			b.setSubject(dbBoard.getSubject());
			return mav;
		}
		try {
			service.boardReply(board);
			mav.setViewName("redirect:list.shop");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("답글등록에 실패했습니다.", "reply.shop");
		}
		return mav;
	}

	@PostMapping("update")
	public ModelAndView update(@Valid Board board, BindingResult bresult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		if (bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}

		Board dbBoard = service.getBoard(board.getNum());

		if (!dbBoard.getPass().equals(board.getPass())) {
			throw new BoardException("비밀번호가 틀립니다.", "update.shop?num=" + board.getNum());
		}

		try {
			service.boardUpdate(board, request);
			mav.setViewName("redirect:list.shop");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("에러발생, 전산부연락바람", "update.shop?num=" + board.getNum());
		}
		return mav;
	}

	@PostMapping("delete")
	public ModelAndView delete(Board board, BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		Board dbBoard = service.getBoard(board.getNum());

		if (!dbBoard.getPass().equals(board.getPass())) {
			bresult.reject("error.login.password");
			return mav;
		}

		try {
			service.boardDelete(board.getNum());
			mav.setViewName("redirect:list.shop");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("삭제실패", "delete.shop?num=" + board.getNum());
		}
		return mav;
	}

}
