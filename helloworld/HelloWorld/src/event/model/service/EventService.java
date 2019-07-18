package event.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import event.model.dao.EventDAO;
import event.model.vo.Event;
import event.model.vo.EventPhoto;

public class EventService {

	public List<Event> selectEventAll(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Event> list = new EventDAO().selectEventAll(conn, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int countAll() {
		Connection conn = getConnection();
		int totalContent = new EventDAO().countEventAll(conn);
		if(totalContent>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return totalContent;
	}

	public Event selectEventOne(int eventNo) {
		Connection conn = getConnection();
		Event e = new EventDAO().selectEventOne(conn, eventNo);
		close(conn);
		return e;
	}

	public int insertEvent(Event e) {
		Connection conn = getConnection();
		int result = new EventDAO().insertEvent(conn, e);
		if(result>0) {
			commit(conn);
			result = new EventDAO().selectLastSeq(conn);
			System.out.println("서비스단"+result);
		}
		else{
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int insertEventPhoto(EventPhoto ep) {
		Connection conn = getConnection();
		int result = new EventDAO().insertEventPhoto(conn, ep);
		if(result>0) {
			commit(conn);
		}
		else{
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public EventPhoto selectEventPhotoOne(int eventNo) {
		Connection conn = getConnection();
		EventPhoto ep = new EventDAO().selectEventPhotoOne(conn, eventNo);
		close(conn);
		return ep;
	}

	public int updateEvent(Event e) {
		Connection conn = getConnection();
		int result = new EventDAO().updateEvent(conn, e);
		if(result>0) {
			commit(conn);
		}
		else{
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updateEventPhoto(EventPhoto ep) {
		Connection conn = getConnection();
		int result = new EventDAO().updateEventPhoto(conn, ep);
		if(result>0) {
			commit(conn);
		}
		else{
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteEvent(int eventNo) {
		Connection conn = getConnection();
		int result = new EventDAO().deleteEvent(conn, eventNo);
		if(result>0) {
			commit(conn);
		}
		else{
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteEventPhoto(int eventNo) {
		Connection conn = getConnection();
		int result = new EventDAO().deleteEventPhoto(conn, eventNo);
		if(result>0) {
			commit(conn);
		}
		else{
			rollback(conn);
		}
		close(conn);
		return result;
	}

}
