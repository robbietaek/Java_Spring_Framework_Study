package chap2;

public class WriteImpl {
	private ArticleDao dao;
	
	public WriteImpl(ArticleDao dao) {
		this.dao = dao;
	}
	public void write() {
		System.out.println("WriteImpl.write 메서드 호출");
		dao.insert();
		System.out.println("WriteImpl.write 실행 종료");
	}
}
