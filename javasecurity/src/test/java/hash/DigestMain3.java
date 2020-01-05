package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
 * 화면에서 아이디와 비밀번호를 입력받아서
 * 해당 아이디가 userbackup 테이블에 없으면 "아이디확인"출력
 * 해당 아이디의 비밀번호를 비교해서 맞으면 "반갑습니다. 아이디님" 출력
 * 해당 아이디의 비밀번호를 비교해서 틀리면 "비밀번호 확인" 출력
 * 
 */
public class DigestMain3 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {

		Scanner scan = new Scanner(System.in);
		System.out.println("아이디를 입력하세요.");
		String pid = scan.nextLine();

		String did = "";
		String dpass = "";

		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/classdb", "scott", "1234");
		PreparedStatement pstmt = conn.prepareStatement("select userid, password from userbackup where userid =?");
		pstmt.setString(1, pid);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			
			did = rs.getString(1);
			dpass = rs.getString(2);

			System.out.println("비밀번호를 입력하세요.");
			String ppass = scan.nextLine();

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] pbyte = ppass.getBytes();
			byte[] phash = md.digest(pbyte);
			String inputpass = "";
			for (byte b : phash) {
				inputpass += String.format("%02X", b);
			}

			if (inputpass.equals(dpass)) {
				System.out.println("반갑습니다. " + did + "님");
			} else {
				System.out.println("비밀번호가 다릅니다.");
			}
		}else {
			System.out.println("해당 아이디가 없습니다.");
		}

	}
}
