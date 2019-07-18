package review.model.vo;

public class ReviewPhoto {
	
	private int photoNo;
	private int reviewNo;
	private int photoLevel;
	private String originalPhotoName;
	private String renamedPhotoName;
	
	public ReviewPhoto() {}
	
	public ReviewPhoto(int photoNo, int reviewNo, int photoLevel, String originalPhotoName, String renamedPhotoName) {
		this.photoNo = photoNo;
		this.reviewNo = reviewNo;
		this.photoLevel = photoLevel;
		this.originalPhotoName = originalPhotoName;
		this.renamedPhotoName = renamedPhotoName;
	}

	public int getPhotoNo() {
		return photoNo;
	}

	public void setPhotoNo(int photoNo) {
		this.photoNo = photoNo;
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public int getPhotoLevel() {
		return photoLevel;
	}

	public void setPhotoLevel(int photoLevel) {
		this.photoLevel = photoLevel;
	}

	public String getOriginalPhotoName() {
		return originalPhotoName;
	}

	public void setOriginalPhotoName(String originalPhotoName) {
		this.originalPhotoName = originalPhotoName;
	}

	public String getRenamedPhotoName() {
		return renamedPhotoName;
	}

	public void setRenamedPhotoName(String renamedPhotoName) {
		this.renamedPhotoName = renamedPhotoName;
	}

	@Override
	public String toString() {
		return "ReviewPhoto [photoNo=" + photoNo + ", reviewNo=" + reviewNo + ", photoLevel=" + photoLevel
				+ ", originalPhotoName=" + originalPhotoName + ", renamedPhotoName=" + renamedPhotoName + "]";
	}
	
	

}
