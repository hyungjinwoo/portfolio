package admin.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import admin.model.dao.AdminDAO;
import member.model.vo.Member;
import notice.model.vo.Notice;
import place.model.vo.Place;
import quest.model.vo.Question;
import quest.model.vo.QuestionComment;
import review.model.vo.Review;

public class AdminService {
	
	public List<Member> selectMemberList(String memberName, String memberId, String phone, String gender,String leave,int cPage, int numPerPage) {
		Connection conn =getConnection();
		List<Member> userList = new AdminDAO().selectMemberList(conn,memberName,memberId,phone,gender,leave,cPage,numPerPage);
		close(conn);
		return userList;
	}
	
	public int selectMemberCnt(String memberName, String memberId, String phone, String gender, String leave) {
		Connection conn =getConnection();
		int selectMemberCnt = new AdminDAO().selectMemberCnt(conn,memberName,memberId,phone,gender,leave);
		close(conn);
		return selectMemberCnt;
	}

	public Member selectOne(String memberId) {

		Connection conn = getConnection();
		Member member = new AdminDAO().selectOne(conn,memberId);
		close(conn);
		
		return member;
	}

	public int memberUpdate(Member member) {

		Connection conn = getConnection();
		int result = new AdminDAO().memberUpdate(conn,member);
		
		if(result>=1) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

/*	public List<Questionboard> qnaAllList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Questionboard> qnaAllList = new AdminDAO().qnaAllList(conn,cPage,numPerPage);
		close(conn);
		
		return qnaAllList;
	}
*/
	public int qnaAllCount() {
		Connection conn = getConnection();
		int count = new AdminDAO().qnaAllCount(conn);
		close(conn);
		
		return count;
	}

	public int qnaAnswer(int qNo, String qnaAns) {
		Connection conn = getConnection();
		int result = new AdminDAO().qnaAnswer(conn,qNo,qnaAns);
				
		if(1<=result) commit(conn);
		else rollback(conn);

		close(conn);
		
		return result;
	}

	public List<QuestionComment> qnaAnsList(List<Integer> ansList) {
		Connection conn = getConnection();
		List<QuestionComment> qnaAnsList = new AdminDAO().qnaAnsList(conn,ansList);
		close(conn);
		
		return qnaAnsList;
	}

	public JSONArray chartlist() {
		Connection conn = getConnection();
		JSONArray chartlist = new AdminDAO().chartlist(conn);
		close(conn);
		
		return chartlist; 
	}

	public int columnChart(int age,String like) {
		Connection conn = getConnection();
		int result = new AdminDAO().columnChart(conn,age,like);
		close(conn);
		
		return result;
	}

	public List<Place> placeList() {

		Connection conn = getConnection();
		List<Place> placeList = new AdminDAO().placeList(conn);
		close(conn);
		
		return placeList;
	}

	public List<Review> mapSearchReview(String placeId) {
		Connection conn = getConnection();
		List<Review> reviewList = new AdminDAO().mapSearchReview(conn,placeId);
		close(conn);
		
		return reviewList;
	}

	public int adminMapReviewDel(int reveiwNo) {
		Connection conn = getConnection();
		int result = new AdminDAO().adminMapReviewDel(conn,reveiwNo);
		
		if(1<=result) commit(conn);
		else rollback(conn);

		close(conn);
		
		return result;
	}

	public int chkJid(String jSESSIONID) {
		Connection conn = getConnection();
		int result = new AdminDAO().chkJid(conn,jSESSIONID);

		close(conn);
		
		return result;
	}

	public int inJid(String jSESSIONID) {
		Connection conn = getConnection();
		int result = new AdminDAO().inJid(conn,jSESSIONID);
		
		if(1<=result) commit(conn);
		else rollback(conn);

		close(conn);
		
		return result;
	}

	public int chkMemberId(String memberId, String jSESSIONID) {
		Connection conn = getConnection();
		int result = new AdminDAO().chkMemberId(conn,memberId,jSESSIONID);

		close(conn);
		
		return result;
	}

	public int inMemberId(String memberId, String jSESSIONID) {
		Connection conn = getConnection();
		int result = new AdminDAO().inMemberId(conn,memberId,jSESSIONID);
		
		if(1<=result) commit(conn);
		else rollback(conn);

		close(conn);
		
		return result;
	}

	public Map<Integer,Integer> chartAllCnt() {
		Connection conn = getConnection();
		Map<Integer,Integer> chartAllList = new AdminDAO().chartAllCnt(conn);
		
		close(conn);
		
		return chartAllList;
	}

	public Map<Integer,Integer> chartMemberList() {
		Connection conn = getConnection();
		Map<Integer,Integer> chartMemberList = new AdminDAO().chartMemberList(conn);

		close(conn);
		
		return chartMemberList;
	}

	public int todayQnaCnt() {
		Connection conn = getConnection();
		int todayQnaCnt = new AdminDAO().todayQnaCnt(conn);

		close(conn);
		
		return todayQnaCnt;
	}

	public int todayReviewCnt() {
		Connection conn = getConnection();
		int todayReviewCnt = new AdminDAO().todayReviewCnt(conn);

		close(conn);
		
		return todayReviewCnt;
	}

	public int totalQnaCnt() {
		Connection conn = getConnection();
		int totalQnaCnt = new AdminDAO().totalQnaCnt(conn);
		close(conn);
		return totalQnaCnt;
	}

	public int ingQnaCnt() {
		Connection conn = getConnection();
		int ingQnaCnt = new AdminDAO().ingQnaCnt(conn);
		close(conn);
		return ingQnaCnt;
	}

	public int totalReviewCnt() {
		Connection conn = getConnection();
		int totalReviewCnt = new AdminDAO().totalReviewCnt(conn);
		close(conn);
		return totalReviewCnt;
	}

	public int totalMemberCnt() {
		Connection conn = getConnection();
		int totalMemberCnt = new AdminDAO().totalMemberCnt(conn);
		close(conn);
		return totalMemberCnt;
	}

	public List<Question> qnaList() {
		Connection conn = getConnection();
		List<Question> qnaList = new AdminDAO().qnaList(conn);
		close(conn);
		return qnaList;
	}

	public List<Notice> noticeList() {
		Connection conn = getConnection();
		List<Notice> qnaList = new AdminDAO().noticeList(conn);
		close(conn);
		return qnaList;
	}

	public int totalNoticeCnt() {
		Connection conn = getConnection();
		int totalNoticeCnt = new AdminDAO().totalNoticeCnt(conn);
		close(conn);
		return totalNoticeCnt;
	}

	public JSONArray pielist() {
		Connection conn = getConnection();
		JSONArray pielist = new AdminDAO().pielist(conn);
		close(conn);
		
		return pielist; 
	}

	
	public List<Member> memberList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Member> memberList = new AdminDAO().memberList(conn,cPage,numPerPage);
		close(conn);
		
		return memberList; 
	}

	public int memberAllCount() {
		Connection conn = getConnection();
		int memberAllCount = new AdminDAO().memberAllCount(conn);
		close(conn);
		return memberAllCount; 
	}

}
