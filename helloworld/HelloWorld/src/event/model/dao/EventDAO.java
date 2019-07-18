package event.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import event.model.vo.Event;
import event.model.vo.EventPhoto;

public class EventDAO {
	
	private Properties prop = new Properties();
	
	public EventDAO() {
		String fileName = getClass().getResource("/sql/event/event-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Event> selectEventAll(Connection conn, int cPage, int numPerPage) {
		List<Event> list = new ArrayList<Event>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectEventAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startrownum = (cPage-1)*numPerPage+1;
			int endrownum = cPage * numPerPage;
			
			pstmt.setInt(1, startrownum);
			pstmt.setInt(2, endrownum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Event e = new Event();
				e.setEventNo(rset.getInt("event_no"));
				e.setEventTitle(rset.getString("event_title"));
				e.setEventContent(rset.getString("event_content"));
				list.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int countEventAll(Connection conn) {
		int totalContent = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countEventAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				totalContent = rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContent;
	}

	public Event selectEventOne(Connection conn, int eventNo) {
		Event e = new Event();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectEventOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, eventNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				e.setEventNo(rset.getInt("event_no"));
				e.setEventTitle(rset.getString("event_title"));
				e.setEventContent(rset.getString("event_content"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return e;
	}

	public int insertEvent(Connection conn, Event e) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertEvent");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, e.getEventTitle());
			pstmt.setString(2, e.getEventContent());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertEventPhoto(Connection conn, EventPhoto ep) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertEventPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, ep.getEventNo());
			pstmt.setString(2, ep.getOriginalFileName());
			pstmt.setString(3, ep.getRenamedFileName());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int selectLastSeq(Connection conn) {
		int eventNo = 0;
		Statement stmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectLastSeq");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				eventNo = rset.getInt("eventNo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return eventNo;
	}

	public EventPhoto selectEventPhotoOne(Connection conn, int eventNo) {
		EventPhoto ep = new EventPhoto();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectEventPhotoOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, eventNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				ep.setEventNo(rset.getInt("event_no"));
				ep.setOriginalFileName(rset.getString("original_filename"));
				ep.setRenamedFileName(rset.getString("renamed_filename"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return ep;
	}

	public int updateEvent(Connection conn, Event e) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateEvent");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, e.getEventTitle());
			pstmt.setString(2, e.getEventContent());
			pstmt.setInt(3, e.getEventNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateEventPhoto(Connection conn, EventPhoto ep) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateEventPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ep.getOriginalFileName());
			pstmt.setString(2, ep.getRenamedFileName());
			pstmt.setInt(3, ep.getEventNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteEvent(Connection conn, int eventNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteEvent");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, eventNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
	
		return result;
	}

	public int deleteEventPhoto(Connection conn, int eventNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteEventPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, eventNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
}
