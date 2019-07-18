package mainPage.model.dao;

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

import mainPage.model.vo.City;
import mainPage.model.vo.City_Img;
import mainPage.model.vo.Nation;

public class MainPageDAO {
private Properties prop = new Properties();
    
    public MainPageDAO() {
        String fileName = getClass()
                         .getResource("/sql/MainPage/mainPage.properties")
                         .getPath();
//        AjaxDAO의 클래스 객체로부터 getResource 메소드 호출. getPath는 절대경로를 호출하기 위해
        
        try {
            prop.load(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public List<Nation> searchNation(Connection conn, String srchNation) {
	List<Nation> list= null;
	PreparedStatement pstmt= null;
	ResultSet rset = null;
	Nation n =null;
	String sql = prop.getProperty("searchNation");
	
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, '%'+ srchNation+'%');
		rset= pstmt.executeQuery();
		list=new ArrayList<Nation>();
		while(rset.next()) {
			n = new Nation();
			n.setNational_name(rset.getString("kor_national_name"));
			list.add(n);
		}
	
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		close(rset);
		close(pstmt);
	}
	return list;
	}
	//해당 국가 도시 img 불러오기
	public List<City_Img> selectCities(Connection conn, String nid) {
		List<City_Img> cityList= null;
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		String sql = prop.getProperty("getCities");
		//System.out.println("nid @ DAO == " + nid);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nid);
			rset= pstmt.executeQuery();
			cityList=new ArrayList<City_Img>();
			while(rset.next()) {
				City_Img c = new City_Img();
				c.setCity_image(rset.getString("city_img"));
				cityList.add(c);
			}
			//System.out.println("cityList@DAO ==" + cityList );
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}		
		return cityList;
	}
	//이미지에 매칭될 도시 이름 가져오기
	public List<City> selectCityNames(Connection conn, String nid) {
		List<City> cityNameList= null;
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		String sql = prop.getProperty("getCityNames");
		//System.out.println("nid @ DAO == " + nid);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nid);
			rset= pstmt.executeQuery();
			cityNameList=new ArrayList<City>();
			while(rset.next()) {
				City c = new City();
				c.setCity_name(rset.getString("city_name"));
				cityNameList.add(c);
			}
			//System.out.println("cityNameList@DAO ==" + cityNameList );
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}		
		return cityNameList;
	}
	public List<Nation> getWea_Name(Connection conn, String nid) {
		List<Nation> weaList = null;
		PreparedStatement pstmt = null;
		System.out.println("nid " + nid);
		String sql = prop.getProperty("getWea_Name");
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nid);
			rset = pstmt.executeQuery();
			weaList = new ArrayList<Nation>();
			while(rset.next()) {
				Nation n = new Nation();
				n.setWea_name(rset.getString("wea_name"));
				weaList.add(n);
			}
			System.out.println("weaList"+weaList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return weaList;
	}
	public List<Nation> getCrc_List(Connection conn, String nid) {
		List<Nation> crcList = null;
		PreparedStatement pstmt = null;
		System.out.println("nid " + nid);
		String sql = prop.getProperty("getCrc_code");
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nid);
			rset = pstmt.executeQuery();
			crcList = new ArrayList<Nation>();
			while(rset.next()) {
				Nation n = new Nation();
				n.setCrc_code(rset.getString("crc_code"));
				n.setC_name(rset.getString("c_name"));
				crcList.add(n);
			}
			System.out.println("crcList@DAO"+crcList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return crcList;
	}
	public List<City_Img> selectcityListMore(Connection conn, int cPage, int numPerPage, String nid) {
		List<City_Img> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCityListMore");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, nid);
			pstmt.setInt(2, (cPage-1)*numPerPage +1);
			pstmt.setInt(3, cPage*numPerPage);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				City_Img c = new City_Img();
				c.setCity_image(rset.getString("city_img"));
				c.setCity_name(rset.getString("city_name"));
				list.add(c);}
			System.out.println("selectCityListMore@DAO=="+list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return list;
	}
	public int selectcityCount(Connection conn, String nid) {
		int totalContent =0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectcityCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nid);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				totalContent = rset.getInt("totalcontent");
			}
			System.out.println("totalContent@dao=" +totalContent);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);	
		}		
		return totalContent;
	}

}
