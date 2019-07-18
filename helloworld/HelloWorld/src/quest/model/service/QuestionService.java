package quest.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import quest.model.dao.QuestionDAO;
import quest.model.vo.Question;
import quest.model.vo.QuestionComment;

public class QuestionService {

	public List<Question> selectQuestionAll(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Question> list = new QuestionDAO().selectQuestionAll(conn, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int countAll() {
		Connection conn = getConnection();
		int result = new QuestionDAO().countAll(conn);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int insertQuestion(Question q) {
		Connection conn = getConnection();
		int result = new QuestionDAO().insertQuestion(conn, q);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Question selectQuestionOne(int questNo) {
		Connection conn = getConnection();
		Question q = new QuestionDAO().selectQuestionOne(conn, questNo);
		close(conn);
		return q;
	}

	public int deleteQuestion(int questNo) {
		Connection conn = getConnection();
		int result = new QuestionDAO().deleteQuestion(conn, questNo);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updateQuestion(Question q) {
		Connection conn = getConnection();
		int result = new QuestionDAO().updateQuestion(conn, q);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public List<QuestionComment> selectQuestionComment(int questNo) {
		Connection conn = getConnection();
		List<QuestionComment> list = new QuestionDAO().selectQuestionComment(conn, questNo);
		close(conn);
		return list;
	}

	public int insertQuestionComment(QuestionComment qc) {
		Connection conn = getConnection();
		int result = new QuestionDAO().insertQuestionComment(conn, qc);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteQuestionComment(int questionCommentNo, int questionNo) {
		Connection conn = getConnection();
		int result = new QuestionDAO().deleteQuestionComment(conn, questionCommentNo, questionNo);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int QuestionCommentClear(int questNo) {
		Connection conn = getConnection();
		int result = new QuestionDAO().QuestionCommentClear(conn, questNo);
		if(result>0) {
			commit(conn);
		} 
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

}
