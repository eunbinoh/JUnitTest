package com.kh.sharethevision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/root-context.xml"})
public class TestJUnit {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String UID = "spring";
	private static final String PWD = "spring";
	
	private DataSource dataSource;
	private ApplicationContext ctx;
	
	
	@Test
	public void connectTest() throws Exception {
		Class.forName(DRIVER);
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(URL,UID,PWD);
			String id = "user02";
			String name = selectName(conn,id);
			String phone = selectName(conn,id);
			System.out.println("id:"+id+", nickname:"+name+", phone:"+phone);
			if(conn!=null) {
//				int num = insert(conn, "테스트1", "테스트2");
//				System.out.println(num+"개의 행 추가 완료");
				System.out.println("DB 커넥션 성공");
				
			}else {
				System.out.println("DB 연결 실패");
				System.out.println("DB 정보를 가져오지 못했습니다");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try { 
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
		@Disabled
		private int insert(Connection conn, String name, String pass) {
			final String SQL= "INSERT INTO MEMBER VALUES ('user05','pass05','테스트','닉네임','33@33','F',20,'010-123-1222','99/10/10','서울시','18/01/01','18/01/01',default)"; 
			try(Statement stmt = (Statement) conn.createStatement()) {
				return stmt.executeUpdate(SQL); 
				
			}catch(Exception e){ 
				e.printStackTrace();
				System.out.println("쿼리문 실패");
				return 0;
			}
		}
		
		private String selectName(Connection conn, String id) throws Exception {
			String result = null;
			ResultSet rs = null;
			final String query= "select * from member where id=? "; 
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, id);
				rs = pstmt.executeQuery(); 
				if(rs.next()) {
					result = rs.getString("nickname");
					result = rs.getString("phone");
				}
				
			}catch(Exception e){ 
				throw e;
			}
			return result;
		}
	
	
	@Test
	public void JUnitTest1() {
		ctx = new GenericXmlApplicationContext("root-context.xml");
		this.dataSource = (DataSource) ctx.getBean("dataSource");
		
		if (this.ctx != null) {
			System.out.println("테스트 작동 1 context not null");
		}
		if (this.dataSource != null) {
			System.out.println("테스트 작동 2 datasource not null");
		}
		
		try (Connection conn = dataSource.getConnection()) {
			System.out.println("conn eq dt True ");
		} catch (Exception e) {
			System.out.println("conn eq dt False");
		}
	}
	@Test
	public void JUnitTest2() {
		System.out.println("say Hello");
	}
}








