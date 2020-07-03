package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBconnection;
import dto.DataDto;

public class MemberDao {
	
	private static MemberDao dao = null;
	private String id;
	
	private MemberDao() {
		
	}
	// 싱글턴
	public static MemberDao getInstance() {
		if(dao == null){
				dao = new MemberDao();			
		}
		return dao;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	// ID 중복확인 메소드
	public boolean getId(String id) {
		// ID를 입력해서 DB에서 검색 후 출력.
		String sql = " SELECT ID"
				+	 " FROM MEMBER "
				+	 " WHERE ID = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		// ID를 찾으면 true
		boolean findId = false;
			
		try {
			// db와 연결해서 db의 정보를 받아온다.
			conn = DBconnection.getConnection();
			// 쿼리문을 실행하기 전 상태를 저장.
			psmt = conn.prepareStatement(sql);
			// 쿼리문에 입력값을 대입
			psmt.setString(1, id);
			// 쿼리문을 실행한 후 출력한 데이터를 rs로 받아온다.
			rs = psmt.executeQuery();
			// 받아온 데이터가 있는지 rs가 검색
			if(rs.next()) {
				// id가 있다면 true
				findId = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 모든 실행이 끝난 후 db를 닫아준다.
			DBClose.close(psmt, conn, rs);
		}
		// 찾은 id를 리턴
		return findId;
	}
	// 회원의 정보를 DB에 입력하는 메소드
	public boolean addMember(String id, String pwd, String name, String email) {
		// 외부에서 받아온 데이터로 테이블에 입력할 쿼리문
		String sql = " INSERT INTO MEMBER(ID, PWD, NAME, EMAIL) "
				+	 " VALUES(?, ?, ?, ?) ";
						
		Connection conn =null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			// 쿼리문에서 지정한 곳에 데이터를 입력한다.
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			psmt.setString(3, name);
			psmt.setString(4, email);
					
			// 쿼리문을 적용한 row데이터의 수를 반환.
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, null);
		}
		// id가 있으면 true, 없으면 false
		return count > 0 ? true : false;
	}
	
	// 로그인할 때 ID와 패스워드 확인하는 메소드
	public int memberCheck(String id, String pwd) {
		// 입력한 id의 패스워드를 찾는 쿼리문
		String sql = " SELECT PWD " 
				+	 " FROM MEMBER "
				+	 " WHERE ID = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int check = 0;
		
		try {
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			String db_pwd = "";
			
			while(rs.next()) {	
				db_pwd = rs.getString("PWD");
			}
			// 만약에 id가 db에 없다면 2
			if(db_pwd.equals("")) {
				check = 2;
			}
			// id가 db에는 있지만 pwd가 틀렸다면 1
			else if(!db_pwd.equals(pwd)){
				check = 1;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}
		// id,pwd에 문제가 없으면 0을 반환.
		return check;
	}
	// 자신의 수입/지출 내역을 추가하는 메소드
	public boolean input_data(String id, String io_kind, int amount, String content) {
		// 외부에서 받아온 데이터를 ACT_BOOK테이블에 입력
		String sql = " INSERT INTO ACT_BOOK( SEQ, ID, IO_KIND, AMOUNT, CONTENT, WDATE )"
				+	 " VALUES ( SEQ.nextval, ?, ?, ?, ?, SYSDATE)" ;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, id);
			psmt.setString(2, io_kind);
			psmt.setInt(3, amount);
			psmt.setString(4, content);
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, null);
		}
		// 테이블에 입력이 성공했으면 true, 실패했으면 false
		return count > 0 ? true : false;
	}
	// 회원이 소비/지출한 내역을 기간을 지정해서 검색하는 메소드
	public List<DataDto> date_result(String id, String date1,String date2 ) {
		
		String sql = " SELECT m.NAME, b.WDATE, b.IO_KIND, b.AMOUNT, b.CONTENT "
				+	 " FROM member m, ACT_BOOK b "
				+	 " WHERE m.id = b.id AND m.id = ? AND "
				+	 " b.wdate BETWEEN TO_DATE(?, 'YYYYMMDD') "
				+ 	 " AND TO_DATE(?, 'YYYYMMDD HH24:MI:SS') ";
		
		
		List<DataDto> list = new ArrayList<DataDto>();
		
		Connection conn = null;
		PreparedStatement psmt =null;
		ResultSet rs = null;
		
		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
		
			psmt.setString(1, id);
			psmt.setString(2, date1);
			// 시분초는 지정을 안하면 00:00:00지정이 되기 때문에 텍스트로 붙여준다
			psmt.setString(3, date2+"23:59:59");
				
			rs = psmt.executeQuery();
			
			String name = "";
			String wdate = "";
			String iokind = "";
			int amount = 0;
			String content = "";
		
			while(rs.next()) {
				
				name = rs.getString("NAME");
				wdate = rs.getDate("WDATE")+"";
				iokind = rs.getString("IO_KIND");
				amount = rs.getInt("AMOUNT");
				content = rs.getString("CONTENT");
				// 입력데이터를 dto로 생성 -> row데이터
				DataDto dd = new DataDto(name, wdate, iokind, amount, content);
				// row데이터를 list에 추가
				list.add(dd);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}	
		// 리스트 반환
		return list;
	}
	
	// 키워드를 입력하면 그 키워드가 포함된 ROW데이터를 출력하는 메소드.
	public List<DataDto> txt_search(String id, String search) {
		
		String sql = " SELECT m.NAME, b.WDATE, b.IO_KIND, b.AMOUNT, b.CONTENT "
				+	 " FROM MEMBER m, ACT_BOOK b "
				+	 " WHERE m.id = b.id AND m.id = ? "
				+	 " AND INSTR(content, ? , 1, 1 ) != 0";
		
		List<DataDto> list = new ArrayList<DataDto>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);	
			// 찾는 유저의 id를 조건을 쿼리문을 실행
			psmt.setString(1, id);
			// 찾을 키워드
			psmt.setString(2, search);
					
			rs = psmt.executeQuery();
			
			String name = "";
			String wdate = "";
			String iokind = "";
			int amount = 0;
			String content = "";
					
			while(rs.next()) {
				
				name = rs.getString("NAME");
				wdate = rs.getDate("WDATE")+"";
				iokind = rs.getString("IO_KIND");
				amount = rs.getInt("AMOUNT");
				content = rs.getString("CONTENT");
				// 못 찾으면 null 값
				DataDto dd = new DataDto(name, wdate, iokind, amount, content);
				
				list.add(dd);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}
		// 리스트 반환
		return list;
	}	
}