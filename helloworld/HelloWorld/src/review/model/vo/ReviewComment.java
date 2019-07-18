package review.model.vo;

import java.sql.Date;

public class ReviewComment {
	
	private int reviewCommentNo;
	private int reviewRef;
	private String reviewCommentWriter;
	private int reviewCommentRef;
	private int reviewCommentLevel;
	private String reviewCommentContent;
	private Date reviewCommentDate;
	
	public ReviewComment() {}
	
	public ReviewComment(int reviewCommentNo, int reviewRef, String reviewCommentWriter, int reviewCommentRef,
			int reviewCommentLevel, String reviewCommentContent, Date reviewCommentDate) {
		this.reviewCommentNo = reviewCommentNo;
		this.reviewRef = reviewRef;
		this.reviewCommentWriter = reviewCommentWriter;
		this.reviewCommentRef = reviewCommentRef;
		this.reviewCommentLevel = reviewCommentLevel;
		this.reviewCommentContent = reviewCommentContent;
		this.reviewCommentDate = reviewCommentDate;
	}

	public int getReviewCommentNo() {
		return reviewCommentNo;
	}

	public void setReviewCommentNo(int reviewCommentNo) {
		this.reviewCommentNo = reviewCommentNo;
	}

	public int getReviewRef() {
		return reviewRef;
	}

	public void setReviewRef(int reviewRef) {
		this.reviewRef = reviewRef;
	}

	public String getReviewCommentWriter() {
		return reviewCommentWriter;
	}

	public void setReviewCommentWriter(String reviewCommentWriter) {
		this.reviewCommentWriter = reviewCommentWriter;
	}

	public int getReviewCommentRef() {
		return reviewCommentRef;
	}

	public void setReviewCommentRef(int reviewCommentRef) {
		this.reviewCommentRef = reviewCommentRef;
	}

	public int getReviewCommentLevel() {
		return reviewCommentLevel;
	}

	public void setReviewCommentLevel(int reviewCommentLevel) {
		this.reviewCommentLevel = reviewCommentLevel;
	}

	public String getReviewCommentContent() {
		return reviewCommentContent;
	}

	public void setReviewCommentContent(String reviewCommentContent) {
		this.reviewCommentContent = reviewCommentContent;
	}

	public Date getReviewCommentDate() {
		return reviewCommentDate;
	}

	public void setReviewCommentDate(Date reviewCommentDate) {
		this.reviewCommentDate = reviewCommentDate;
	}

	@Override
	public String toString() {
		return "ReviewComment [reviewCommentNo=" + reviewCommentNo + ", reviewRef=" + reviewRef
				+ ", reviewCommentWriter=" + reviewCommentWriter + ", reviewCommentRef=" + reviewCommentRef
				+ ", reviewCommentLevel=" + reviewCommentLevel + ", reviewCommentContent=" + reviewCommentContent
				+ ", reviewCommentDate=" + reviewCommentDate + "]";
	}
	
	
	
	

}
