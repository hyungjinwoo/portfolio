package admin.model.dao;


import static common.JDBCTemplate.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import admin.model.service.AdminService;
import member.model.vo.Member;
import notice.model.vo.Notice;
import place.model.vo.Place;
import quest.model.vo.Question;
import quest.model.vo.QuestionComment;
import review.model.vo.Review;

public class AdminDAO {
	
	private Properties prop= new Properties();
	
	public AdminDAO() {
		String fileName = getClass().getResource("/sql/admin/admin-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int selectMemberCnt(Connection conn, String memberName, String memberId, String phone, String gender,
			String leave) {
		int selectMemberCnt = 0;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = "select count(*) as cnt from "+leave+" where 1=1 ";
		
		if(memberName.trim().length()>=1) {
			sql += "and member_name like '%"+memberName+"%'";
		}
		
		if(memberId.trim().length()>=1) {
			sql += "and member_id like '%"+memberId+"%'";
		}
		
		if(phone.trim().length()>=1) {
			sql += "and phone like '%"+phone+"%'";
		}
		
		if(gender.trim().length()>=1) {
			sql += "and gender='"+gender+"'";
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				selectMemberCnt=rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return selectMemberCnt;
	}
	
	public List<Member> selectMemberList(Connection conn, String memberName, String memberId, String phone,
		String gender,String leave,int cPage, int numPerPage) {
		List<Member> userList = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		int startRowNum = cPage*numPerPage-(numPerPage-1); 
		int endRowNum = cPage*numPerPage;
		
		String sql = "select V.* from (select rownum as rnum,V.* from ("; 
		sql += "select * from "+leave+" where 1=1 ";
		
		if(memberName.trim().length()>=1) {
			sql += "and member_name like '%"+memberName+"%'";
		}
		
		if(memberId.trim().length()>=1) {
			sql += "and member_id like '%"+memberId+"%'";
		}
		
		if(phone.trim().length()>=1) {
			sql += "and phone like '%"+phone+"%'";
		}
		
		if(gender.trim().length()>=1) {
			sql += "and gender='"+gender+"'";
		}
		
		sql += ") V) V where rnum between "+startRowNum+" and "+endRowNum;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemberName(rset.getString("member_name"));
				m.setPassword(rset.getString("password"));
				m.setTel(rset.getString("phone"));
				m.setGender(rset.getString("gender"));
				m.setInterest(rset.getString("member_like"));
				/*m.setQuestion(rset.getString("password_check"));*/
				m.setAnswer(rset.getString("password_answer"));
				m.setOriginalImgName(rset.getString("original_imgname"));
				m.setRenamedImgName(rset.getString("renamed_imgname"));
				m.setJoinDate(rset.getDate("joindate"));
				m.setBirth(rset.getDate("birthday"));
				
				userList.add(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}

		return userList;
	}


	public Member selectOne(Connection conn, String memberId) {
		Member m = null;
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rset=pstmt.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return m;
	}




	public int memberUpdate(Connection conn, Member member) {
		int result =0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("memberUpdate");
		
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberName());
			pstmt.setDate(2, member.getBirth());
			pstmt.setString(3, member.getTel());
			pstmt.setString(4, member.getGender());
			pstmt.setString(5, member.getMemberId());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn);
		}
		
		return result;
	}

/*
	public List<Questionboard> qnaAllList(Connection conn, int cPage, int numPerPage) {
		List<Questionboard> qnaAllList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = prop.getProperty("qnaAllList");
		
		int startRowNum = (cPage*numPerPage)-(numPerPage-1);
		int endRowNum = cPage*numPerPage;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRowNum);
			pstmt.setInt(2, endRowNum);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Questionboard qBoard = new Questionboard();
				
				qBoard.setQuestion_no(rs.getInt("question_no"));
				qBoard.setMember_writer(rs.getString("question_writer"));
				qBoard.setQuestion_title(rs.getString("question_title"));
				qBoard.setQuestion_content(rs.getString("question_content"));
				qBoard.setQuestion_original_filename(rs.getString("question_original_filename"));
				qBoard.setQuestion_renamed_filename(rs.getString("question_renamed_filename"));
				qBoard.setQuestion_date(rs.getDate("question_date"));
				qBoard.setQuestion_level(rs.getInt("question_level"));
				qBoard.setAnswer_level(rs.getInt("answer_level"));
				
				qnaAllList.add(qBoard);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return qnaAllList;
	}*/

	public int qnaAllCount(Connection conn) {
		int result=0;
		ResultSet rs=null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("qnaAllCount");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int qnaAnswer(Connection conn, int qNo, String qnaAns) {
		int result = 0;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("qnaAnswer");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qnaAns);
			pstmt.setInt(2, qNo);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}




	public List<QuestionComment> qnaAnsList(Connection conn, List<Integer> ansList) {
		List<QuestionComment> qnaAnsList = new ArrayList<QuestionComment>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		QuestionComment qc = null;
		String query="";
		
		switch(ansList.size()) {
			case 5:query="qnaAnsList5";break;
			case 4:query="qnaAnsList4";break;
			case 3:query="qnaAnsList3";break;
			case 2:query="qnaAnsList2";break;
			case 1:query="qnaAnsList1";break;
		}
		
		
		String sql = prop.getProperty(query);
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			switch(ansList.size()) {
				case 5:pstmt.setInt(5, ansList.get(4));
				case 4:pstmt.setInt(4, ansList.get(3));
				case 3:pstmt.setInt(3, ansList.get(2));
				case 2:pstmt.setInt(2, ansList.get(1));
				case 1:pstmt.setInt(1, ansList.get(0));break;
		}

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				qc = new QuestionComment();
				qc.setQuestionCommentNo(rs.getInt("question_comment_no"));
				qc.setQuestionCommentContent(rs.getString("question_comment_content"));
				qc.setQuestionRef(rs.getInt("question_ref"));
				qnaAnsList.add(qc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return qnaAnsList;
	}




	public JSONArray chartlist(Connection conn) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql= prop.getProperty("chartlist");
		
		JSONArray chartlist = new JSONArray();
		JSONArray charthead = new JSONArray();
			charthead.add("국가명");
			charthead.add("리뷰수");
			chartlist.add(charthead);
		JSONArray chartdata = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				chartdata = new JSONArray();
				chartdata.add(rs.getString("eng_national_name"));
				chartdata.add(rs.getInt("cnt"));
				chartlist.add(chartdata);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return chartlist;
	}




	public int columnChart(Connection conn,int age,String like) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql =prop.getProperty("columnChart");
		int result=0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, age);
			pstmt.setString(2, "%"+like+"%");
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				result=rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	public List<Place> placeList(Connection conn) {
		List<Place> placeList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("placeList");
		Place p=null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				p = new Place();
				p.setPlaceId(rset.getString("place_id"));
				p.setCityCode(rset.getString("city_code_1"));
				p.setPlaceCode(rset.getString("place_code"));
				p.setPlaceName(rset.getString("place_name"));
				p.setPlaceX(rset.getDouble("place_x"));
				p.setPlaceY(rset.getDouble("place_y"));
				p.setPlaceRating(rset.getInt("place_rating"));
				placeList.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		
		return placeList;
	}




	public List<Review> mapSearchReview(Connection conn, String placeId) {
		List<Review> reviewList=new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql = prop.getProperty("mapSearchReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, placeId);
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				Review r = new Review();
				r.setReviewNo(rset.getInt("review_no"));
				r.setMemberId(rset.getString("member_id"));
				r.setPlaceId(rset.getString("place_id"));
				r.setReviewTitle(rset.getString("review_title"));
				r.setReviewContent(rset.getString("review_content"));
				r.setPlaceRating(rset.getInt("place_rating"));
				r.setLikeCount(rset.getInt("like_count"));
				r.setReadCount(rset.getInt("read_count"));
				r.setReviewDate(rset.getDate("review_date"));
				reviewList.add(r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return reviewList;
	}




	public int adminMapReviewDel(Connection conn, int reveiwNo) {
		int result = 0;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("adminMapReviewDel");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reveiwNo);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}




	public int chkJid(Connection conn, String jSESSIONID) {
		int result = 0;
		ResultSet rset= null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("connCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, jSESSIONID);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				result=rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return result;
	}




	public int inJid(Connection conn, String jSESSIONID) {
		int result = 0;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("inJid");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, jSESSIONID);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}




	public int chkMemberId(Connection conn, String memberId, String jSESSIONID) {
		int result = 0;
		ResultSet rset= null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("chkMemberId");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, jSESSIONID);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				result=rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return result;
	}




	public int inMemberId(Connection conn, String memberId, String jSESSIONID) {
		int result = 0;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("inMemberId");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, jSESSIONID);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}




	public Map<Integer,Integer> chartAllCnt(Connection conn) {
		Map<Integer,Integer> chartAllList = new HashMap<>();
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("chartAllCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				chartAllList.put(Integer.parseInt(rset.getString("dday")),rset.getInt("cnt"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return chartAllList;
	}




	public Map<Integer,Integer> chartMemberList(Connection conn) {
		Map<Integer,Integer> chartMemberList = new HashMap<>();
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("chartMemberCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				chartMemberList.put(Integer.parseInt(rset.getString("dday")),rset.getInt("cnt"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return chartMemberList;
	}




	public int todayQnaCnt(Connection conn) {
		int todayQnaCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("todayQnaCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				todayQnaCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return todayQnaCnt;
	}




	public int todayReviewCnt(Connection conn) {
		int todayReviewCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("todayReviewCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				todayReviewCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return todayReviewCnt;
	}




	public int totalQnaCnt(Connection conn) {
		int totalQnaCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("totalQnaCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				totalQnaCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return totalQnaCnt;
	}




	public int ingQnaCnt(Connection conn) {
		int ingQnaCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("ingQnaCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ingQnaCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return ingQnaCnt;
	}




	public int totalReviewCnt(Connection conn) {
		int totalReviewCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("totalReviewCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				totalReviewCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return totalReviewCnt;
	}




	public int totalMemberCnt(Connection conn) {
		int totalMemberCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("totalMemberCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				totalMemberCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return totalMemberCnt;
	}




	public List<Question> qnaList(Connection conn) {
		List<Question> qnaList = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("qnaList");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Question q = new Question();
				q.setQuestionNo(rset.getInt("question_no"));
				q.setQuestionWriter(rset.getString("question_writer"));
				q.setQuestionTitle(rset.getString("question_title"));
				q.setQuestionCotent(rset.getString("question_content"));
				q.setQuestionOriginalFileName(rset.getString("question_original_filename"));
				q.setQuestionRenamedFileName(rset.getString("question_renamed_filename"));
				q.setQuestionDate(rset.getDate("question_date"));
				q.setQuestionLevel(rset.getInt("question_level"));
				q.setAnswerLevel(rset.getInt("answer_level"));
				qnaList.add(q);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return qnaList;
	}




	public List<Notice> noticeList(Connection conn) {
		List<Notice> noticeList = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("noticeList");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice n = new Notice();
				n.setNoticeNo(rset.getInt("notice_no"));
				n.setNoticeTitle(rset.getString("notice_title"));
				n.setNoticeContent(rset.getString("notice_content"));
				n.setEventCheck(rset.getInt("event_check"));
				n.setNoticeDate(rset.getDate("notice_date"));
				noticeList.add(n);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return noticeList;
	}




	public int totalNoticeCnt(Connection conn) {
		int totalNoticeCnt=0;
		ResultSet rset = null;
		PreparedStatement pstmt =null;
		String sql = prop.getProperty("totalNoticeCnt");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				totalNoticeCnt=rset.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return totalNoticeCnt;
	}




	public JSONArray pielist(Connection conn) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql= prop.getProperty("pielist");
		
		JSONArray pielist = new JSONArray();
		JSONArray charthead = new JSONArray();
			charthead.add("도시명");
			charthead.add("리뷰수");
			pielist.add(charthead);
		JSONArray chartdata = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				chartdata = new JSONArray();
				chartdata.add(rs.getString("city_name"));
				chartdata.add(rs.getInt("cnt"));
				pielist.add(chartdata);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return pielist;
	}
	

	public List<Member> memberList(Connection conn, int cPage, int numPerPage) {
		List<Member> memberList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("memberList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRowNum = cPage*numPerPage-(numPerPage-1); 
			int endRowNum = cPage*numPerPage;
			
			pstmt.setInt(1, startRowNum);
			pstmt.setInt(2, endRowNum);
			
			//쿼리 실행
			rset = pstmt.executeQuery();
			
			//rset의 정보를 member 객체에 옮겨담기
			while(rset.next()) {
				Member m = new Member();
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

	public int memberAllCount(Connection conn) {
		int memberAllCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("memberAllCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			//쿼리 실행
			rset = pstmt.executeQuery();
			
			//rset의 정보를 member 객체에 옮겨담기
			if(rset.next()) {
				memberAllCount=rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return memberAllCount;
	}
}
