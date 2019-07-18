package place.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import place.model.vo.Place;

public class PlaceDAO {
	
	private Properties prop = new Properties();
	
	public PlaceDAO() {
		String fileName = getClass().getResource("/sql/place/place-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertPlace(Connection conn, Place p) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertPlace");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getPlaceId());
			pstmt.setString(2, p.getCityCode());
			pstmt.setString(3, p.getPlaceCode());
			pstmt.setString(4, p.getPlaceName());
			pstmt.setDouble(5, p.getPlaceX());
			pstmt.setDouble(6, p.getPlaceY());
			pstmt.setInt(7, p.getPlaceRating());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int checkPlace(Connection conn, String placeId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkPlace");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, placeId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("cnt");
			}
			
			System.out.println("해당아이디존재여부확인@DAO:"+result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int updatePlaceRating(Connection conn, String placeId, int total, String placeCode, String cityCode) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updatePlaceRating");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, total);
			pstmt.setString(2, placeCode);
			pstmt.setString(3, cityCode);
			pstmt.setString(4, placeId);

			result = pstmt.executeUpdate();
			
			System.out.println("장소Rating업데이트실행결과@DAO:"+result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public Place selectPlaceOne(Connection conn, String point) {
		Place p = new Place();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectPlaceOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, point);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				p.setPlaceId(rset.getString("place_id"));
				p.setCityCode(rset.getString("city_code_1"));
				p.setPlaceCode(rset.getString("place_code"));
				p.setPlaceName(rset.getString("place_name"));
				p.setPlaceX(rset.getDouble("place_x"));
				p.setPlaceY(rset.getDouble("place_y"));
				p.setPlaceRating(rset.getInt("place_rating"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return p;
	}

	public int countWithPlaceCode(Connection conn, String cityCode, int searchType) {
		int totalContent = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countWithPlaceCode");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, searchType);
			pstmt.setString(2, cityCode);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				totalContent = rset.getInt("cnt");
			}
			System.out.println("장소분류에 따른 해당분류장소 개수@DAO:"+totalContent);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContent;
	}

}
