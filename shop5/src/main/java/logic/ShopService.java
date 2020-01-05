package logic;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.metadata.aggregated.rule.ParallelMethodsMustNotDefineParameterConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardDao;
import dao.ItemDao;
import dao.SaleDao;
import dao.SaleItemDao;
import dao.UserDao;

@Service	//@Component + service 기능
public class ShopService {
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SaleDao saleDao;
	@Autowired
	private SaleItemDao saleItemDao;
	@Autowired
	private BoardDao boardDao;
	
	public List<Item> getItemList(){
		return itemDao.list();
	}
	
	public Item getItem(String id){
		return itemDao.selectOne(id);
	}
	
	public void itemDelete(String id){
		itemDao.delete(id);
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		if(item.getPicture()!=null&&!item.getPicture().isEmpty()) {	//업로드된 이미지파일이 존재하면
			//파일을 업로드 : 업로드된 파일의 내용을 파일에 저장
			uploadFileCreate(item.getPicture(),request,"item/img/");
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.insert(item);
	}
	
	public void itemUpdate(Item item, HttpServletRequest request) {
		if(item.getPicture()!=null&&!item.getPicture().isEmpty()) {	//업로드된 이미지파일이 존재하면
			//파일을 업로드 : 업로드된 파일의 내용을 파일에 저장
			uploadFileCreate(item.getPicture(),request,"item/img/");
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.update(item);
	}

	private void uploadFileCreate(MultipartFile picture, HttpServletRequest request, String path) {
		//picture : 업로드된 파일의 내용
		String orgFile = picture.getOriginalFilename();		//파일의 이름
		String uploadPath= request.getServletContext().getRealPath("/")+path;
		File fpath = new File(uploadPath);
		if(!fpath.exists()) {	//해당 Path가 없으면 만들고
			fpath.mkdirs();		//여러개의 디렉토리를 만드려면 mkdirs 를 써주고 하나만 만들때 mkdir을 쓴다.
		}
		try{
			//파일로 생성
			picture.transferTo(new File(uploadPath+orgFile));	//해당Path에 파일이름으로 업로드해라
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void insert(User user) {
		userDao.insert(user);
	}

	public User getUser(String userid) {
		return userDao.selectOne(userid);
	}

	public Sale checkend(User loginUser, Cart cart) {
		Sale sale = new Sale();
		sale.setSaleid(saleDao.getMaxSaleId());
		sale.setUser(loginUser);
		sale.setUserid(loginUser.getUserid());
		sale.setUpdatetime(new Date());
		saleDao.insert(sale);
		//주문상품정보를 Cart에서 조회를 해서 ItemList 로 가져온다.
		List<ItemSet> itemList = cart.getItemSetList();
		int i = 0;
		for(ItemSet is : itemList) {
			int saleItemId = ++i;
			SaleItem saleItem = new SaleItem(sale.getSaleid(),saleItemId,is);
			sale.getItemList().add(saleItem);
			saleItemDao.insert(saleItem);
		}
		return sale;
	}

	public List<Sale> salelist(String id) {
		return saleDao.list(id);		//사용자 ID
	}
	

	public List<SaleItem> saleItemList(int saleid) {
		return saleItemDao.list(saleid);		//saleid 주문번호
	}

	public void userUpdate(User user) {
		userDao.update(user);
		
	}

	public void userDelete(String userid) {
		userDao.delete(userid);
	}

	public int boardcount(String searchtype, String searchcontent) {
		return boardDao.count(searchtype, searchcontent);
	}

	public List<Board> boardlist(Integer pageNum, int limit, String searchtype, String searchcontent) {
		return boardDao.list(pageNum, limit, searchtype, searchcontent);
	}

	public void boardWrite(Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {		//첨부파일이 있는 경우
			uploadFileCreate(board.getFile1(),request,"board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		//현재 등록된 게시물 번호
		int max = boardDao.maxnum();
		board.setNum(++max);
		board.setGrp(max);
		boardDao.insert(board);
	}

	public Board getBoard(Integer num, HttpServletRequest request) {
		if(request.getRequestURI().contains("detail.shop")) {
			boardDao.readcntadd(num);
		}
		return boardDao.selectOne(num);
	}
	
	public Board getBoard(Integer num) {
		return boardDao.selectOne(num);
	}

	public void boardReply(Board board) {
		//grpstep +1 증가
		boardDao.updateGrpStep(board);
		//답변글 등록
 		int max = boardDao.maxnum();
		board.setNum(++max);
		board.setGrplevel(board.getGrplevel()+1);
		board.setGrpstep(board.getGrpstep()+1);
		boardDao.insert(board);
	}

	public void boardUpdate(Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(),request,"board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.update(board);
	}

	public void boardDelete(Integer num) {
		boardDao.delete(num);
	}

	public List<User> userlist() {
		return userDao.list();
	}

	public List<User> userList(String[] idchks) {
		return userDao.list(idchks);
	}

	public void userPasswordUpdate(String userid, String chgpass) {
		userDao.updatepassword(userid, chgpass);
	}

}
