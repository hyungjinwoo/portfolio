package review.model.vo;

public class LikeCount {
	private String LoggedId;
	private int LikeReviewNo;
	
	public LikeCount() {}

	public LikeCount(String loggedId, int likeReviewNo) {
		LoggedId = loggedId;
		LikeReviewNo = likeReviewNo;
	}

	public String getLoggedId() {
		return LoggedId;
	}

	public void setLoggedId(String loggedId) {
		LoggedId = loggedId;
	}

	public int getLikeReviewNo() {
		return LikeReviewNo;
	}

	public void setLikeReviewNo(int likeReviewNo) {
		LikeReviewNo = likeReviewNo;
	}

	@Override
	public String toString() {
		return "LikeCount [LoggedId=" + LoggedId + ", LikeReviewNo=" + LikeReviewNo + "]";
	}
	
	
	
}
