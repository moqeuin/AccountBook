package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// DB연결과 상태저장은 패키지로 복사해서 사용가능하게 만들 수 있다.
public class DBconnection {
	
	public static void initConnection() {
		try {
			//클래스 유무, JDBC드라이버가 있는 지 확인.
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			System.out.println("Driver Loading Success");
			
		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		}  
	}
	
	
	// 데이터 베이스 연결.
	public static Connection getConnection() { //JDBC의 OBJECT를 리턴, 
		Connection conn = null;
			
		try {
			// IP주소를 가진 HR 계정의 DB에 연결한다, 연결된 DB의 테이블 정보 및 쿼리결과 등을 받을 수 있는 변수.
			conn = DriverManager.getConnection("jdbc:oracle:thin:@125.241.174.98:1521:xe",
													"hr", "hr");
				
			System.out.println("Oracle Connection Success!");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
