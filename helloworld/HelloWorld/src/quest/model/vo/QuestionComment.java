package quest.model.vo;

import java.sql.Date;

public class QuestionComment {
	private int questionCommentNo;
	private String questionCommentContent;
	private int questionRef;
	private Date questionCommentDate;
	
	public QuestionComment() {}

	public QuestionComment(int questionCommentNo, String questionCommentContent, int questionRef,
			Date questionCommentDate) {
		this.questionCommentNo = questionCommentNo;
		this.questionCommentContent = questionCommentContent;
		this.questionRef = questionRef;
		this.questionCommentDate = questionCommentDate;
	}

	public int getQuestionCommentNo() {
		return questionCommentNo;
	}

	public void setQuestionCommentNo(int questionCommentNo) {
		this.questionCommentNo = questionCommentNo;
	}

	public String getQuestionCommentContent() {
		return questionCommentContent;
	}

	public void setQuestionCommentContent(String questionCommentContent) {
		this.questionCommentContent = questionCommentContent;
	}

	public int getQuestionRef() {
		return questionRef;
	}

	public void setQuestionRef(int questionRef) {
		this.questionRef = questionRef;
	}

	public Date getQuestionCommentDate() {
		return questionCommentDate;
	}

	public void setQuestionCommentDate(Date questionCommentDate) {
		this.questionCommentDate = questionCommentDate;
	}

	@Override
	public String toString() {
		return "QuestionComment [questionCommentNo=" + questionCommentNo + ", questionCommentContent="
				+ questionCommentContent + ", questionRef=" + questionRef + ", questionCommentDate="
				+ questionCommentDate + "]";
	}
	
	
	
	
}
