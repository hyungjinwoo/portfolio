package quest.model.dao;

import static common.JDBCTemplate.*;

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

import quest.model.vo.Question;
import quest.model.vo.QuestionComment;

public class QuestionDAO {
	
	private Properties prop = new Properties();
	
	public QuestionDAO() {
		String fileName = getClass().getResource("/sql/question/question-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public List<Question> selectQuestionAll(Connection conn, int cPage, int numPerPage) {
		List<Question> list = new ArrayList<Question>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectQuestionAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startrownum = (cPage-1)*numPerPage+1;
			int endrownum = cPage * numPerPage;
			
			pstmt.setInt(1, startrownum);
			pstmt.setInt(2, endrownum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Question q = new Question();
				q.setQuestionNo(rset.getInt("question_no"));
				q.setQuestionWriter(rset.getString("question_writer"));
				q.setQuestionTitle(rset.getString("question_title"));
				q.setQuestionCotent(rset.getString("question_content"));
				q.setQuestionOriginalFileName(rset.getString("question_original_filename"));
				q.setQuestionRenamedFileName(rset.getString("question_renamed_filename"));
				q.setQuestionDate(rset.getDate("question_date"));
				q.setQuestionLevel(rset.getInt("question_level"));
				q.setAnswerLevel(rset.getInt("answer_level"));
				list.add(q);
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
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int insertQuestion(Connection conn, Question q) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertQuestion");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, q.getQuestionWriter());
			pstmt.setString(2, q.getQuestionTitle());
			pstmt.setString(3, q.getQuestionCotent());
			pstmt.setString(4, q.getQuestionOriginalFileName());
			pstmt.setString(5, q.getQuestionRenamedFileName());
			pstmt.setInt(6, q.getQuestionLevel());

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public Question selectQuestionOne(Connection conn, int questNo) {
		Question q = new Question();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectQuestionOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				q.setQuestionNo(rset.getInt("question_no"));
				q.setQuestionWriter(rset.getString("question_writer"));
				q.setQuestionTitle(rset.getString("question_title"));
				q.setQuestionCotent(rset.getString("question_content"));
				q.setQuestionOriginalFileName(rset.getString("question_original_filename"));
				q.setQuestionRenamedFileName(rset.getString("question_renamed_filename"));
				q.setQuestionDate(rset.getDate("question_date"));
				q.setQuestionLevel(rset.getInt("question_level"));
				q.setAnswerLevel(rset.getInt("answer_level"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return q;
	}

	public int deleteQuestion(Connection conn, int questNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteQuestion");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateQuestion(Connection conn, Question q) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateQuestion");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, q.getQuestionTitle());
			pstmt.setString(2, q.getQuestionCotent());
			pstmt.setString(3, q.getQuestionOriginalFileName());
			pstmt.setString(4, q.getQuestionRenamedFileName());
			pstmt.setInt(5, q.getQuestionLevel());
			pstmt.setInt(6, q.getQuestionNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<QuestionComment> selectQuestionComment(Connection conn, int questNo) {
		List<QuestionComment> list = new ArrayList<QuestionComment>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectQuestionComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				QuestionComment qc = new QuestionComment();
				qc.setQuestionCommentNo(rset.getInt("question_comment_no"));
				qc.setQuestionCommentContent(rset.getString("question_comment_content"));
				qc.setQuestionCommentDate(rset.getDate("question_comment_date"));
				list.add(qc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertQuestionComment(Connection conn, QuestionComment qc) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertQuestionComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, qc.getQuestionCommentContent());
			pstmt.setInt(2, qc.getQuestionRef());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteQuestionComment(Connection conn, int questionCommentNo, int questionNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteQuestionComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questionNo);
			pstmt.setInt(2, questionCommentNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int QuestionCommentClear(Connection conn, int questNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("QuestionCommentClear");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
