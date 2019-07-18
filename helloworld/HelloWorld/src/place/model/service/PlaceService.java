package place.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;

import place.model.dao.PlaceDAO;
import place.model.vo.Place;

public class PlaceService {

	public int insertPlace(Place p) {
		Connection conn = getConnection();
		int result = new PlaceDAO().insertPlace(conn, p);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int checkPlace(String placeId) {
		Connection conn = getConnection();
		int result = new PlaceDAO().checkPlace(conn, placeId);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updatePlaceRating(String placeId, int total, String placeCode, String cityCode) {
		Connection conn = getConnection();
		int result = new PlaceDAO().updatePlaceRating(conn, placeId, total, placeCode, cityCode);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Place selectPlaceOne(String point) {
		Connection conn = getConnection();
		Place p = new PlaceDAO().selectPlaceOne(conn, point);
		close(conn);
		return p;
	}

	public int countWithPlaceCode(String cityCode, int searchType) {
		Connection conn = getConnection();
		int totalContent = new PlaceDAO().countWithPlaceCode(conn, cityCode, searchType);
		if(totalContent>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return totalContent;
	}
	
}
