package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClose {
	
	public static void close(Statement stmt, Connection conn,ResultSet rs) {		
		 
		try {
		// DB에서의 쿼리문 실행 및 특정 동작 상태 정보 - > 해방
		if(stmt != null) {
			stmt.close();
		// 연결된 DB - > 해방
		}
		if(conn != null) {
			conn.close();
		}
		// 상태정보에서 입력받은 결과 데이터 - > 해방
		if(rs != null) {
			rs.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}