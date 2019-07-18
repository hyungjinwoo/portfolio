package member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import member.model.vo.Member;

public class MemberDAO {
	private Properties prop = new Properties();
	
	public MemberDAO() {
		String fileName = getClass()
						 .getResource("/sql/member/member-query.properties")
						 .getPath();
//		MemberDAO의 클래스 객체로부터 getResource 메소드 호출. getPath는 절대경로를 호출하기 위해
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public int insertMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberName());
			pstmt.setString(3, m.getPassword());
			pstmt.setDate(4, m.getBirth());
			pstmt.setString(5, m.getTel());
			pstmt.setString(6, m.getGender());
			pstmt.setString(7, m.getInterest());
			pstmt.setString(8, m.getQuestion());
			pstmt.setString(9, m.getAnswer());
			pstmt.setString(10, m.getOriginalImgName());
			pstmt.setString(11, m.getRenamedImgName());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public boolean checkIdDuplicate(Connection conn, String memberId) {
		boolean isUsable = false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkIdDuplicate");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				int cnt = rset.getInt("cnt");
				
				if(cnt == 0) {
					isUsable = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return isUsable;
	}

	public int loginCheck(Connection conn, Member m) {
		int result = -1;
		String sql = prop.getProperty("selectOne");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			rset = pstmt.executeQuery();
			
			//rset의 결과를 변수에 담기
			String memberId = "";
			String password = "";
			//리턴된 행이 있을 경우 실행됨
			if(rset.next()) {
				memberId = rset.getString("member_id"); //컬럼명은 대소문자 구분 X
				password = rset.getString("password");
			}
			
			//비교 및 결과 도출
			if(memberId.equals(m.getMemberId()) && 
					password.equals(m.getPassword())) {
				//로그인 성공
				result = 1;
			}
			else if(memberId.equals(m.getMemberId())) {
				//비밀번호 틀림
				result = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public Member selectOne(Connection conn, String memberId) {
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			//쿼리 실행
			rset = pstmt.executeQuery();
			
			//rset의 정보를 member 객체에 옮겨담기
			if(rset.next()) {
				m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemberName(rset.getString("member_name"));
				m.setPassword(rset.getString("password"));
				m.setTel(rset.getString("phone"));
				m.setGender(rset.getString("gender"));
				m.setInterest(rset.getString("member_like"));
				m.setQuestion(rset.getString("password_check"));
				m.setAnswer(rset.getString("password_answer"));
				m.setOriginalImgName(rset.getString("original_imgname"));
				m.setRenamedImgName(rset.getString("renamed_imgname"));
				m.setJoinDate(rset.getDate("joindate"));
				m.setBirth(rset.getDate("birthday"));
				
			}
			System.out.println("member@dao="+m);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}

	public List<Member> selectOneByName(Connection conn, String memberName) {
		List<Member> memberList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOneByName");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemberName(memberName);
				m.setPassword(rset.getString("password"));
				m.setTel(rset.getString("phone"));
				m.setGender(rset.getString("gender"));
				m.setInterest(rset.getString("member_like"));
				m.setQuestion(rset.getString("password_check"));
				m.setAnswer(rset.getString("password_answer"));
				m.setOriginalImgName(rset.getString("original_imgname"));
				m.setRenamedImgName(rset.getString("renamed_imgname"));
				m.setJoinDate(rset.getDate("joindate"));
				m.setBirth(rset.getDate("birthday"));
				
				memberList.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return memberList;
	}

	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMember");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getQuestion());
			pstmt.setString(2, m.getAnswer());
			pstmt.setString(3, m.getTel());
			pstmt.setString(4, m.getInterest());
			pstmt.setString(5, m.getRenamedImgName());
			pstmt.setString(6, m.getOriginalImgName());
			pstmt.setString(7, m.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int updatePassword(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updatePassword");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getMemberId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMember");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

}
