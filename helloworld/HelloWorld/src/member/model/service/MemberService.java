package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberService {

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = new MemberDAO().insertMember(conn, member);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public boolean checkIdDuplicate(String memberId) {
		Connection conn = getConnection();
		boolean isUsable = new MemberDAO().checkIdDuplicate(conn, memberId);
		close(conn);
		return isUsable;
	}

	public int loginCheck(Member m) {
		Connection conn = getConnection();
		int result = new MemberDAO().loginCheck(conn, m);
		close(conn);
		return result;
	}

	public Member selectOne(String memberId) {
		Connection conn = getConnection();
		Member m = new MemberDAO().selectOne(conn, memberId);
		close(conn);
		return m;
	}

	public List<Member> selectOneByName(String memberName) {
		Connection conn = getConnection();
		List<Member> memberList = new MemberDAO().selectOneByName(conn, memberName);
		close(conn);
		return memberList;
	}

	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDAO().updateMember(conn, m);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updatePassword(Member m) {
        Connection conn = getConnection();
        int result = new MemberDAO().updatePassword(conn, m);
        if(result>0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
    }

	public int deleteMember(Member m) {
		Connection conn = getConnection();
        int result = new MemberDAO().deleteMember(conn, m);
        if(result>0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
	}

}
