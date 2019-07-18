package notice.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import notice.model.dao.NoticeDAO;
import notice.model.vo.Notice;

public class NoticeService {

	public List<Notice> selectAll(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Notice> list = new NoticeDAO().selectAll(conn, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int countAll() {
		Connection conn = getConnection();
		int totalContent = new NoticeDAO().countAll(conn);
		if(totalContent>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return totalContent;
	}

	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = new NoticeDAO().insertNotice(conn, n);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Notice selectNoticeOne(int noticeNo) {
		Connection conn = getConnection();
		Notice n = new NoticeDAO().selectNoticeOne(conn, noticeNo);
		close(conn);
		return n;
	}

	public int updateNotice(Notice n) {
		Connection conn = getConnection();
		int result = new NoticeDAO().updateNotice(conn, n);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteNotice(int noticeNo) {
		Connection conn = getConnection();
		int result = new NoticeDAO().deleteNotice(conn,noticeNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

}
