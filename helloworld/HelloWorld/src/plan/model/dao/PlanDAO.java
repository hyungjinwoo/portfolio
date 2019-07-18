package plan.model.dao;

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
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import plan.model.vo.PlaceList;
import plan.model.vo.Plan;
import plan.model.vo.WishPlace;

public class PlanDAO {
	
private Properties prop = new Properties();
	
	public PlanDAO() {
		String fileName = getClass().getResource("/sql/plan/plan-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Plan> selectAllPlan(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Plan> planList = new ArrayList<>();
		String query = prop.getProperty("selectAllPlan");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Plan plan = new Plan();
				plan.setPlanNo(rset.getInt("plan_no"));
				plan.setMemberId(memberId);
				plan.setPlanTitle(rset.getString("plan_title"));
				plan.setStartDate(rset.getDate("start_date"));
				plan.setEndDate(rset.getDate("end_date"));
				plan.setPlanLevel(rset.getInt("plan_level"));
				plan.setReadCount(rset.getInt("read_count"));
				plan.setLikeCount(rset.getInt("like_count"));
				
				planList.add(plan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		System.out.println("planList@PlanDAO="+planList);
		
		return planList;
	}

	public List<WishPlace> selectAllWishList(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<WishPlace> wishPlaceList = new ArrayList<>();
		String query = prop.getProperty("selectAllWishList");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				WishPlace wishPlace = new WishPlace();
				wishPlace.setMemberId(userId);
				wishPlace.setPlaceName(rset.getString("place_name"));
				wishPlace.setPlaceLat(rset.getFloat("place_x"));
				wishPlace.setPlaceLng(rset.getFloat("place_y"));
				
				wishPlaceList.add(wishPlace);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		System.out.println("planList@PlanDAO="+wishPlaceList);
		
		return wishPlaceList;
	}

	public int inputPlanOutputPlanNo(Connection conn, Plan plan) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmtPlanNo = null;
		ResultSet rset = null;
		int planNo = 0;
		int result = 0;
		String query = prop.getProperty("inputPlanOutputPlanNo");
		String queryPlanNo = prop.getProperty("selectPlanNo");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, plan.getMemberId());
			pstmt.setString(2, plan.getPlanTitle());
			pstmt.setDate(3, plan.getStartDate());
			pstmt.setDate(4, plan.getEndDate());
			
			result = pstmt.executeUpdate();
			//result가 0보다 크다면 => 테이블에 값이 들어갔다. 현재의 플랜 번호-> 시퀀스 번호를 가져온다
			if(result<=0) return result;
			
			pstmtPlanNo = conn.prepareStatement(queryPlanNo);
			rset = pstmtPlanNo.executeQuery();
			if(rset.next()) planNo = rset.getInt("plan_no");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(pstmtPlanNo);
			close(rset);
		}
		System.out.println("planNo@PlanDAO="+planNo);
		
		return planNo;
	}

	public int inputPlaceList(Connection conn, int planNo, List<Object> list) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("inputPlaceList");
		List<Object> placeListArr = null;
		PlaceList placeList = null;
		
		try {
		//*일차
			for(int i=0; i<list.size(); i++) {
				placeListArr = (List<Object>)list.get(i);
				System.out.println("placeListArr@DAO+"+placeListArr);
				//*일차의 *번째 장소
				for(int j=0; j<placeListArr.size(); j++) {
					Map<String, Object> placeListMap = (Map<String, Object>)placeListArr.get(j);
					System.out.println("map@DAO="+placeListMap);
					
					//object타입리스트에서 꺼내기
					placeList = new PlaceList();
					placeList.setPlaceName((String)placeListMap.get("placeName"));
					placeList.setPlaceLat(Float.parseFloat((String)placeListMap.get("placeLat")));
					placeList.setPlaceLng(Float.parseFloat((String)placeListMap.get("placeLng")));
					placeList.setPlaceComment((String)placeListMap.get("placeMemo"));
					
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, planNo);
					pstmt.setInt(2, i+1);
					pstmt.setString(3, placeList.getPlaceName());
					pstmt.setFloat(4, placeList.getPlaceLat());
					pstmt.setFloat(5, placeList.getPlaceLng());
					pstmt.setString(6, placeList.getPlaceComment());
					pstmt.setInt(7, j+1);
					
					result = pstmt.executeUpdate();
					
					if(result<=0) {
						return 0;
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public JSONArray placeLatLngList(Connection conn, int planNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("placeLatLngList");
		JSONArray jsonArray = new JSONArray();
		JSONObject latLng = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, planNo);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				latLng = new JSONObject();
				latLng.put("lat", rset.getFloat("lat")); 
				latLng.put("lng", rset.getFloat("lng"));
				
				jsonArray.add(latLng);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return jsonArray;
	}

	public JSONArray placeCommentList(Connection conn, int planNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("placeCommentList");
		JSONArray jsonCommentArray = new JSONArray();
		JSONObject comment = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, planNo);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				comment = new JSONObject();
				String check = rset.getString("place_comment");
				
				if(check == null) check = " ";
				
				comment.put("comment", check); 
				
				jsonCommentArray.add(comment);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return jsonCommentArray;
	}

	public Plan selectPlan(Connection conn, int planNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectPlan");
		Plan plan = new Plan();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, planNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				plan.setPlanNo(planNo);
				plan.setPlanTitle(rset.getString("plan_title"));
				plan.setStartDate(rset.getDate("start_date"));
				plan.setEndDate(rset.getDate("end_date"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		System.out.println("plan@PlanDAO="+plan);
		return plan;
	}
	
	public List<PlaceList> selectAllPlanPlaceList(Connection conn, List<Integer> planNoList) {
		List<PlaceList> planPlaceList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllPlanPlaceList");
		try {
			pstmt = conn.prepareStatement(sql);
				for(Integer no : planNoList) {
				pstmt.setInt(1, no);
				rset = pstmt.executeQuery();
				while(rset.next()) {
						PlaceList placeList = new PlaceList();
						
						placeList.setPlaceListNo(rset.getInt("place_list_no"));
						placeList.setPlanNo(no);
						placeList.setDay(rset.getInt("day"));
						placeList.setPlaceName(rset.getString("place_name"));
						placeList.setPlaceLat(rset.getFloat("place_x"));
						placeList.setPlaceLng(rset.getFloat("place_y"));
						placeList.setPlaceComment(rset.getString("place_comment"));
						placeList.setPlaceListLevel(rset.getInt("place_list_level"));
						
						planPlaceList.add(placeList);
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		System.out.println("planPlaceList@PlaceDAO="+planPlaceList);
		return planPlaceList;
	}

	public List<PlaceList> selectPlaceList(Connection conn, int planNo) {
		List<PlaceList> planPlaceList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllPlanPlaceList");
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, planNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				PlaceList placeList = new PlaceList();
				
				placeList.setPlaceListNo(rset.getInt("place_list_no"));
				placeList.setPlanNo(planNo);
				placeList.setDay(rset.getInt("day"));
				placeList.setPlaceName(rset.getString("place_name"));
				placeList.setPlaceLat(rset.getFloat("place_x"));
				placeList.setPlaceLng(rset.getFloat("place_y"));
				placeList.setPlaceComment(rset.getString("place_comment"));
				placeList.setPlaceListLevel(rset.getInt("place_list_level"));
				
				planPlaceList.add(placeList);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		System.out.println("planPlaceList@@="+planPlaceList);
		return planPlaceList;
	}

	public int updatePlan(Connection conn, Plan plan) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query1 = prop.getProperty("deletePlan");
		String query2 = prop.getProperty("insertPlan");
		
		try {
			//기존 plan을 드랍(placeList도 같이 없어진다.
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, plan.getPlanNo());
			
			result = pstmt.executeUpdate();
			//드랍실패라면 정지
			if(result<=0) {
				return 0;
			}
			pstmt = conn.prepareStatement(query2);
			pstmt.setInt(1, plan.getPlanNo());
			pstmt.setString(2, plan.getMemberId());
			pstmt.setString(3, plan.getPlanTitle());
			pstmt.setDate(4, plan.getStartDate());
			pstmt.setDate(5, plan.getEndDate());
			pstmt.setInt(6, plan.getPlanLevel());
			pstmt.setInt(7, plan.getLikeCount());
			pstmt.setInt(8, plan.getReadCount());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deletePlan(Connection conn, int planNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deletePlan");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, planNo);
				
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
}
