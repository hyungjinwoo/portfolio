package mainPage.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import mainPage.model.dao.MainPageDAO;
import mainPage.model.vo.City;
import mainPage.model.vo.City_Img;
import mainPage.model.vo.Nation;

public class MainPageService {

	public List<Nation> searchNation(String srchNation) {
		Connection conn = getConnection();
		List<Nation> list= new MainPageDAO().searchNation(conn, srchNation);
		close(conn);
		return list;
	}

	public List<City_Img> selectCities(String nid) {
		Connection conn = getConnection();
		List<City_Img> cityList = new MainPageDAO().selectCities(conn, nid);
		close(conn);
		return cityList;
	}

	public List<City> selectCityNames(String nid) {
		Connection conn = getConnection();
		List<City> cityNameList = new MainPageDAO().selectCityNames(conn,nid);
		close(conn);
		return cityNameList;
	}

	public List<Nation> getWea_Name(String nid) {
		Connection conn = getConnection();
		List<Nation> weaList = new MainPageDAO().getWea_Name(conn,nid);
		close(conn);
		return weaList;
		
	}

	public List<Nation> getCrc_List(String nid) {
		Connection conn = getConnection();
		List<Nation> crcList = new MainPageDAO().getCrc_List(conn,nid);
		close(conn);
		return crcList;
	}

	public List<City_Img> selectcityListMore(String nid, int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<City_Img> list = new MainPageDAO().selectcityListMore(conn, cPage, numPerPage, nid);
		close(conn);
		return list;
	}

	public int selectcityCount (String nid) {
		Connection conn = getConnection();
		int totalContent = new MainPageDAO().selectcityCount(conn, nid);
		close(conn);
		return totalContent;}
	
}
