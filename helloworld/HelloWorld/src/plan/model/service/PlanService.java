package plan.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import place.model.dao.PlaceDAO;
import plan.model.dao.PlanDAO;
import plan.model.vo.PlaceList;
import plan.model.vo.Plan;
import plan.model.vo.WishPlace;

public class PlanService {

	public List<Plan> selectAllPlan(String memberId) {
		Connection conn = getConnection();
		List<Plan> planList = new PlanDAO().selectAllPlan(conn, memberId);
		
		close(conn);
		
		return planList;
	}

	public List<WishPlace> selectAllWishList(String userId) {
		Connection conn = getConnection();
		List<WishPlace> wishPlaceList = new PlanDAO().selectAllWishList(conn, userId);
		close(conn);
		return wishPlaceList;
	}

	public int inputPlanOutputPlanNo(Plan plan) {
		Connection conn = getConnection();
		int planNo = new PlanDAO().inputPlanOutputPlanNo(conn, plan);
		if(planNo>0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return planNo;
	}

	public int inputPlaceList(int planNo, List<Object> list) {
		Connection conn = getConnection();
		int result = new PlanDAO().inputPlaceList(conn, planNo, list);
		
		if(planNo>0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return result;
	}

	public List<PlaceList> selectAllPlanPlaceList(List<Integer> planNoList) {
		Connection conn = getConnection();
		List<PlaceList> planPlaceList = new PlanDAO().selectAllPlanPlaceList(conn, planNoList);
		close(conn);
		return planPlaceList;
	}
	public JSONArray placeLatLngList(int planNo) {
		Connection conn = getConnection();
		JSONArray jsonArray = new PlanDAO().placeLatLngList(conn, planNo);
		close(conn);
		return jsonArray;
	}

	public JSONArray placeCommentList(int planNo) {
		Connection conn = getConnection();
		JSONArray jsonCommentArray = new PlanDAO().placeCommentList(conn, planNo);
		close(conn);
		return jsonCommentArray;
	}

	public Plan selectPlan(int planNo) {
		Connection conn = getConnection();
		Plan plan = new PlanDAO().selectPlan(conn, planNo);
		close(conn);
		return plan;
	}

	public List<PlaceList> selectPlaceList(int planNo) {
		Connection conn = getConnection();
		List<PlaceList> placeList = new PlanDAO().selectPlaceList(conn, planNo);
		close(conn);
		return placeList;
	}

	public int updatePlan(Plan plan) {
		Connection conn = getConnection();
		int result = new PlanDAO().updatePlan(conn, plan);
		
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return result;
	}

	public int deletePlan(int planNo) {
		Connection conn = getConnection();
		int result = new PlanDAO().deletePlan(conn, planNo);
		
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return result;
	}
}
