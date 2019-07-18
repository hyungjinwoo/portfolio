package review.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import review.model.dao.ReviewDAO;
import review.model.vo.Review;
import review.model.vo.ReviewComment;
import review.model.vo.ReviewPhoto;

public class ReviewService {

	public List<Review> selectAll(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Review> list = new ReviewDAO().selectAll(conn, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int countAll() {
		Connection conn = getConnection();
		int totalContent = new ReviewDAO().countAll(conn);
		if(totalContent>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return totalContent;
	}

	public Review selectOne(int reviewNo) {
		Connection conn = getConnection();
		Review rv = new ReviewDAO().selectOne(conn, reviewNo);
		close(conn);
		return rv;
	}

	public int insertReview(Review rv) {
		Connection conn = getConnection();
		int result = new ReviewDAO().insertReview(conn, rv);
		if(result>0) {
			commit(conn);
			result = new ReviewDAO().selectLastSeq(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int insertReviewPhoto(ReviewPhoto rp) {
		Connection conn = getConnection();
		int result = new ReviewDAO().insertReviewPhoto(conn, rp);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public List<ReviewPhoto> selectPhoto(int reviewNo) {
		Connection conn = getConnection();
		List<ReviewPhoto> list = new ReviewDAO().selectPhoto(conn, reviewNo);
		close(conn);
		return list;
	}

	public int deleteReviewImg(String img) {
		Connection conn = getConnection();
		int result = new ReviewDAO().deleteReviewImg(conn, img);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updateReview(Review rv) {
		Connection conn = getConnection();
		int result = new ReviewDAO().updateReview(conn, rv);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteReviewPhoto(int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().deleteReviewPhoto(conn, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteReview(int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().deleteReview(conn, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public List<ReviewComment> selectReviewComment(int reviewNo) {
		Connection conn = getConnection();
		List<ReviewComment> commentList = new ReviewDAO().selectReviewComment(conn, reviewNo);
		close(conn);
		return commentList;
	}

	public int deleteReply(int reviewCommentNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().deleteReply(conn, reviewCommentNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int insertReviewComment(ReviewComment rc) {
		Connection conn = getConnection();
		int result = new ReviewDAO().insertReviewComment(conn, rc);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int[] avgRating(String placeId) {
		Connection conn = getConnection();
		int[] totalRating = new ReviewDAO().avgRating(conn, placeId);
		close(conn);
		return totalRating;
	}

	public String selectReviewListPhoto(int reviewNo) {
		Connection conn = getConnection();
		String oldRenamedPhoto= new ReviewDAO().selectReviewListPhoto(conn, reviewNo);
		close(conn);
		return oldRenamedPhoto;
	}

	public List<Review> selectReviewWithPlaceCode(String cityCode, int searchType, int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Review> list = new ReviewDAO().selectReviewWithPlaceCode(conn, cityCode, searchType, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int increaseReadCount(int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().increaseReadCount(conn, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
		
	}

	public int checkLike(String LoggedId, int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().checkLike(conn, LoggedId, reviewNo);
		if(result>1) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;

	}

	public void updateReviewLikeCount(int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().updateReviewLikeCount(conn, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		
	}

	public void insertLikeCount(String loggedId, int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().insertLikeCount(conn, loggedId, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		
	}

	public void deleteReviewLikeCount(int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().deleteReviewLikeCount(conn, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		
	}

	public void deleteLikeCount(String loggedId, int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().deleteLikeCount(conn, loggedId, reviewNo);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		
	}

	public List<Review> getMyList(int cPage, int numPerPage, String loggedId) {
		Connection conn = getConnection();
		List<Review> ReviewList = new ReviewDAO().getMyList(conn, cPage, numPerPage, loggedId);
		close(conn);
		return ReviewList;
	}

	public List<String> searchPlaceId(String cityName) {
		Connection conn = getConnection();
		List<String> str = new ReviewDAO().searchPlaceId(conn, cityName);
		close(conn);
		return str;
	}

	public Review searchReviewWithPlaceId(int cPage, int numPerPage, String placeId) {
		Connection conn = getConnection();
		Review r = new ReviewDAO().searchReviewWithPlaceId(conn, cPage, numPerPage, placeId);
		close(conn);
		return r;
	}

	public String selectCityCode(String cityName) {
		Connection conn = getConnection();
		String cityCode = new ReviewDAO().selectCityCode(conn, cityName);
		close(conn);
		return cityCode;
	}

	public List<Review> selectReviewWithCityCode(int cPage, int numPerPage, String cityCode) {
		Connection conn = getConnection();
		List<Review> ReviewList = new ReviewDAO().selectReviewWithCityCode(conn, cPage, numPerPage, cityCode);
		close(conn);
		return ReviewList;
	}

	public int countCityCodeAll(String cityCode) {
		Connection conn = getConnection();
		int totalContent = new ReviewDAO().countCityCodeAll(conn, cityCode);
		if(totalContent>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return totalContent;
	}

	public int countSelectUserAll(String loggedId) {
		Connection conn = getConnection();
		int totalContent = new ReviewDAO().countSelectUserAll(conn, loggedId);
		if (totalContent > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return totalContent;
	}

	public int countLikeCount(int reviewNo) {
		Connection conn = getConnection();
		int result = new ReviewDAO().countLikeCount(conn, reviewNo);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}






}
