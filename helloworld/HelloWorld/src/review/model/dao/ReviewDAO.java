package review.model.dao;

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

import review.model.vo.Review;
import review.model.vo.ReviewComment;
import review.model.vo.ReviewPhoto;

public class ReviewDAO {
	
	private Properties prop = new Properties();
	
	public ReviewDAO() {
		String fileName = getClass().getResource("/sql/review/review-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Review> selectAll(Connection conn, int cPage, int numPerPage) {
		List<Review> list = new ArrayList<Review>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");

		try {
			pstmt = conn.prepareStatement(sql);
			
			int startrownum = (cPage-1)*numPerPage+1;
			int endrownum = cPage * numPerPage;
			
			pstmt.setInt(1, startrownum);
			pstmt.setInt(2, endrownum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Review rv = new Review();
				rv.setReviewNo(rset.getInt("review_no"));
				rv.setMemberId(rset.getString("member_id"));
				rv.setPlaceId(rset.getString("place_id"));
				rv.setReviewTitle(rset.getString("review_title"));
				rv.setReviewContent(rset.getString("review_content"));
				rv.setPlaceRating(rset.getInt("place_rating"));
				rv.setLikeCount(rset.getInt("like_count"));
				rv.setReadCount(rset.getInt("read_count"));
				rv.setReviewDate(rset.getDate("review_date"));
				rv.setCityCode(rset.getString("city_code"));
				list.add(rv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int countAll(Connection conn) {
		int totalContent = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countAll");
		
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

	public Review selectOne(Connection conn, int reviewNo) {
		Review rv = new Review();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				rv.setReviewNo(rset.getInt("review_no"));
				rv.setMemberId(rset.getString("member_id"));
				rv.setPlaceId(rset.getString("place_Id"));
				rv.setReviewTitle(rset.getString("review_title"));
				rv.setReviewContent(rset.getString("review_content"));
				rv.setPlaceRating(rset.getInt("place_rating"));
				rv.setLikeCount(rset.getInt("like_count"));
				rv.setReadCount(rset.getInt("read_count"));
				rv.setReviewDate(rset.getDate("review_date"));
				rv.setCityCode(rset.getString("city_code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return rv;
	}

	public int insertReview(Connection conn, Review rv) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, rv.getMemberId());
			pstmt.setString(2, rv.getPlaceId());
			pstmt.setString(3, rv.getReviewTitle());
			pstmt.setString(4, rv.getReviewContent());
			pstmt.setInt(5, rv.getPlaceRating());
			pstmt.setString(6, rv.getCityCode());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int selectLastSeq(Connection conn) {
		int reviewNo = 0;
		Statement stmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectLastSeq");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				reviewNo = rset.getInt("reviewNo");
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return reviewNo;
	}

	public int insertReviewPhoto(Connection conn, ReviewPhoto rp) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReviewPhoto");
		
		try {
			String[] Oname = rp.getOriginalPhotoName().split("&");
			String[] Rname = rp.getRenamedPhotoName().split("&");

			pstmt = conn.prepareStatement(sql);
			
			for(int i=0; i<Oname.length; i++) {
				
				if(i == 0) {
					pstmt.setInt(1, rp.getReviewNo());
					pstmt.setInt(2, 1);
					pstmt.setString(3, Oname[i]);
					pstmt.setString(4, Rname[i]);
				
					result += pstmt.executeUpdate();
					continue;
				}
				
				if(!Oname[i].equals("없음")) {
					pstmt.setInt(1, rp.getReviewNo());
					pstmt.setInt(2, 0);
					pstmt.setString(3, Oname[i]);
					pstmt.setString(4, Rname[i]);
				
					result += pstmt.executeUpdate();
				}
				
				else {
					continue;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<ReviewPhoto> selectPhoto(Connection conn, int reviewNo) {
		List<ReviewPhoto> list = new ArrayList<ReviewPhoto>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ReviewPhoto rp = new ReviewPhoto();
				rp.setPhotoNo(rset.getInt("photo_no"));
				rp.setReviewNo(rset.getInt("review_no"));
				rp.setPhotoLevel(rset.getInt("photo_level"));
				rp.setOriginalPhotoName(rset.getString("original_photo_name"));
				rp.setRenamedPhotoName(rset.getString("renamded_photo_name"));
				list.add(rp);
			}
			System.out.println("리뷰사진확인="+list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int deleteReviewImg(Connection conn, String img) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteReviewImg");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, img);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateReview(Connection conn, Review rv) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, rv.getPlaceId());
			pstmt.setString(2, rv.getReviewTitle());
			pstmt.setString(3, rv.getReviewContent());
			pstmt.setInt(4, rv.getPlaceRating());
			pstmt.setString(5, rv.getCityCode());
			pstmt.setInt(6, rv.getReviewNo());
			
			result = pstmt.executeUpdate();
			
			System.out.println("리뷰게시판 테이블 결과:"+result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteReviewPhoto(Connection conn, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteReviewPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
			System.out.println("리뷰포토테이블 사진파일 삭제@DAO:"+result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteReview(Connection conn, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<ReviewComment> selectReviewComment(Connection conn, int reviewNo) {
		List<ReviewComment> commentList = new ArrayList<ReviewComment>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ReviewComment rc = new ReviewComment();
				rc.setReviewCommentContent(rset.getString("review_comment_content"));
				rc.setReviewCommentDate(rset.getDate("review_comment_date"));
				rc.setReviewCommentLevel(rset.getInt("review_comment_level"));
				rc.setReviewCommentNo(rset.getInt("review_comment_no"));
				rc.setReviewCommentRef(rset.getInt("review_comment_ref"));
				rc.setReviewCommentWriter(rset.getString("review_comment_writer"));
				rc.setReviewRef(rset.getInt("review_ref"));
				commentList.add(rc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return commentList;
	}

	public int deleteReply(Connection conn, int reviewCommentNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewCommentNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertReviewComment(Connection conn, ReviewComment rc) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReviewComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rc.getReviewRef());
			pstmt.setString(2, rc.getReviewCommentWriter());
			pstmt.setString(3, rc.getReviewCommentRef()==0?null:String.valueOf(rc.getReviewCommentRef()));
			pstmt.setInt(4, rc.getReviewCommentLevel());
			pstmt.setString(5, rc.getReviewCommentContent());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int[] avgRating(Connection conn, String placeId) {
		int[] totalRating = new int[2];
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("avgRating");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, placeId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				totalRating[0] = rset.getInt("total_rating");
				totalRating[1] = rset.getInt("cnt");
			}
			System.out.println("해당장소의 평점 총합@DAO:"+totalRating[0]);
			System.out.println("해당장소에 다녀간 총 사람수@DAO:"+totalRating[1]);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalRating;
	}

	public String selectReviewListPhoto(Connection conn, int reviewNo) {
		String oldRenamedPhoto = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewListPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				oldRenamedPhoto = rset.getString("renamded_photo_name");
			}
			System.out.println("해당 글번호의 사진들확인@DAO:"+oldRenamedPhoto);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return oldRenamedPhoto;
	}

	public List<Review> selectReviewWithPlaceCode(Connection conn, String cityCode, int searchType, int cPage, int numPerPage) {
		List<Review> list = new ArrayList<Review>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewWithPlaceCode");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cityCode);
			pstmt.setInt(2, searchType);
			pstmt.setInt(3, (cPage-1)*numPerPage+1);
			pstmt.setInt(4, cPage * numPerPage);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Review r = new Review();
				r.setReviewNo(rset.getInt("review_no"));
				r.setMemberId(rset.getString("member_id"));
				r.setPlaceId(rset.getString("place_id"));
				r.setReviewTitle(rset.getString("review_title"));
				r.setReviewContent(rset.getString("review_content"));
				r.setPlaceRating(rset.getInt("place_rating"));
				r.setLikeCount(rset.getInt("like_count"));
				r.setReadCount(rset.getInt("read_count"));
				r.setReviewDate(rset.getDate("review_date"));
				r.setCityCode(rset.getString("city_code"));
				list.add(r);
			}
			System.out.println("장소분류별 검색결과 확인@DAO:"+list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
	
		return list;
	}

	public int increaseReadCount(Connection conn, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int checkLike(Connection conn, String loggedId, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, loggedId);
			pstmt.setInt(2, reviewNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("cnt");
			}
			System.out.println("좋아요테이블에서의 검색결과@DAO:"+result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int updateReviewLikeCount(Connection conn, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateReviewLikeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
			System.out.println("리뷰좋아요 업데이트 결과@DAO:"+result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertLikeCount(Connection conn, String loggedId, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertLikeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, loggedId);
			pstmt.setInt(2, reviewNo);
			
			result = pstmt.executeUpdate();
			System.out.println("좋아요테이블 신규데이터넣기@DAO:"+result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteReviewLikeCount(Connection conn, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteReviewLikeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
			System.out.println("리뷰좋아요 하나삭제하기 작업@DAO:"+result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteLikeCount(Connection conn, String loggedId, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteLikeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, loggedId);
			pstmt.setInt(2, reviewNo);
			
			result = pstmt.executeUpdate();
			System.out.println("좋아요테이블 삭제@DAO:"+result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<Review> getMyList(Connection conn, int cPage, int numPerPage, String loggedId) {
		List<Review> ReviewList = new ArrayList<Review>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getMyList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startrownum = (cPage-1)*numPerPage+1;
			int endrownum = cPage * numPerPage;
			
			pstmt.setString(1, loggedId);
			pstmt.setInt(2, startrownum);
			pstmt.setInt(3, endrownum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Review r = new Review();
				r.setReviewNo(rset.getInt("review_no"));
				r.setMemberId(rset.getString("member_id"));
				r.setPlaceId(rset.getString("place_id"));
				r.setReviewTitle(rset.getString("review_title"));
				r.setReviewContent(rset.getString("review_content"));
				r.setPlaceRating(rset.getInt("place_rating"));
				r.setLikeCount(rset.getInt("like_count"));
				r.setReadCount(rset.getInt("read_count"));
				r.setReviewDate(rset.getDate("review_date"));
				r.setCityCode(rset.getString("city_code"));
				ReviewList.add(r);
			}
			System.out.println("내가 쓴 리뷰글만 가져오기@DAO:"+ReviewList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return ReviewList;
	}

	public List<String> searchPlaceId(Connection conn, String cityName) {
		List<String> str = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("searchPlaceId");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cityName);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				String placeid = rset.getString("place_id");
				str.add(placeid);
			}
			System.out.println("해당 도시코드로 DB에 존재하는 장소아이디@DAO:"+str);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return str;
	}

	public Review searchReviewWithPlaceId(Connection conn, int cPage, int numPerPage, String placeId) {
		Review r = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("searchReviewWithPlaceId");
	
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startrownum = (cPage-1)*numPerPage+1;
			int endrownum = cPage * numPerPage;
			
			pstmt.setString(1, placeId);
			pstmt.setInt(2, startrownum);
			pstmt.setInt(3, endrownum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				r = new Review();
				r.setReviewNo(rset.getInt("review_no"));
				r.setMemberId(rset.getString("member_id"));
				r.setPlaceId(rset.getString("place_id"));
				r.setReviewTitle(rset.getString("review_title"));
				r.setReviewContent(rset.getString("review_content"));
				r.setPlaceRating(rset.getInt("place_rating"));
				r.setLikeCount(rset.getInt("like_count"));
				r.setReadCount(rset.getInt("read_count"));
				r.setReviewDate(rset.getDate("review_date"));
				r.setCityCode(rset.getString("city_code"));
			}
			System.out.println("검색한 도시에 해당하는 장소아이디로 검색한 리뷰테이블@DAO:"+r);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return r;
	}

	public String selectCityCode(Connection conn, String cityName) {
		String cityCode = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCityCode");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cityName);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				cityCode = rset.getString("city_code_1");
			}
			System.out.println("해당도시의 도시코드@DAO:"+cityCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
			
		return cityCode;
	}

	public List<Review> selectReviewWithCityCode(Connection conn, int cPage, int numPerPage, String cityCode) {
		List<Review> ReviewList = new ArrayList<Review>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewWithCityCode");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startrownum = (cPage-1)*numPerPage+1;
			int endrownum = cPage * numPerPage;
			
			pstmt.setString(1, cityCode);
			pstmt.setInt(2, startrownum);
			pstmt.setInt(3, endrownum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Review r = new Review();
				r.setReviewNo(rset.getInt("review_no"));
				r.setMemberId(rset.getString("member_id"));
				r.setPlaceId(rset.getString("place_id"));
				r.setReviewTitle(rset.getString("review_title"));
				r.setReviewContent(rset.getString("review_content"));
				r.setPlaceRating(rset.getInt("place_rating"));
				r.setLikeCount(rset.getInt("like_count"));
				r.setReadCount(rset.getInt("read_count"));
				r.setReviewDate(rset.getDate("review_date"));
				r.setCityCode(rset.getString("city_code"));
				ReviewList.add(r);
			}
			System.out.println("도시코드로 검색한 리뷰리스트@DAO:"+ReviewList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return ReviewList;
	}

	public int countCityCodeAll(Connection conn, String cityCode) {
		int totalContent = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countCityCodeAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cityCode);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				totalContent = rset.getInt("cnt");
			}
			System.out.println("도시코드로 검색했을때 총 게시물 수@DAO:"+totalContent);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContent;
	}

	public int countSelectUserAll(Connection conn, String loggedId) {
		int totalContent = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countSelectUserAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, loggedId);
			
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

	public int countLikeCount(Connection conn, int reviewNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countLikeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("like_count");
			}
			System.out.println("현재글의 좋아요 수@DAO:"+result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
	
		return result;
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
