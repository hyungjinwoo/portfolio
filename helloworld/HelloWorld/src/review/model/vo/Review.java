package review.model.vo;

import java.util.Date;

public class Review {
	private int reviewNo;
	private String memberId;
	private String placeId;
	private String reviewTitle;
	private String reviewContent;
	private int placeRating;
	private int likeCount;
	private int readCount;
	private Date reviewDate;
	private String cityCode;
	
	public Review() {}

	public Review(int reviewNo, String memberId, String placeId, String reviewTitle, String reviewContent, int placeRating,
			int likeCount, int readCount, Date reviewDate, String cityCode) {
		this.reviewNo = reviewNo;
		this.memberId = memberId;
		this.placeId = placeId;
		this.reviewTitle = reviewTitle;
		this.reviewContent = reviewContent;
		this.placeRating = placeRating;
		this.likeCount = likeCount;
		this.readCount = readCount;
		this.reviewDate = reviewDate;
		this.cityCode = cityCode;
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public int getPlaceRating() {
		return placeRating;
	}

	public void setPlaceRating(int placeRating) {
		this.placeRating = placeRating;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public String toString() {
		return "Review [reviewNo=" + reviewNo + ", memberId=" + memberId + ", placeId=" + placeId + ", reviewTitle="
				+ reviewTitle + ", reviewContent=" + reviewContent + ", placeRating=" + placeRating + ", likeCount="
				+ likeCount + ", readCount=" + readCount + ", reviewDate=" + reviewDate + ", cityCode=" + cityCode
				+ "]";
	}

	


	
	
}
