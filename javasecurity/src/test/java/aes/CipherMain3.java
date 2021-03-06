package aes;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * useraccount 테이블의 email 값을 읽어서
 * userbackup 테이블의 email을 암호화하기
 * key는 id의 해쉬값 문자열 앞 16자리로 정한다.
 */
public class CipherMain3 {
	public static void main(String[] args) throws Exception {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/classdb", "scott", "1234");
		PreparedStatement pstmt = conn.prepareStatement("select userid, email from useraccount");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			String id = rs.getString(1);
			String email = rs.getString(2);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String key = CipherUtil.makehash(id).substring(0,16);
			String cipher1 = CipherUtil.encrypt(email, key);
			pstmt.close();
			pstmt = conn.prepareStatement("update userbackup set email =? where userid = ?");
			pstmt.setString(1, cipher1);
			pstmt.setString(2, id);
			pstmt.execute();
		}
	}
}
