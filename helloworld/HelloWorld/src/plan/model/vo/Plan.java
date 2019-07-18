package plan.model.vo;

import java.sql.Date;

public class Plan {
	private int planNo;
	private String memberId;
	private String planTitle;
	private Date startDate;
	private Date endDate;
	private int planLevel;
	private int readCount;
	private int likeCount;
	
	public Plan() {}

	public Plan(int planNo, String memberId, String planTitle, Date startDate, Date endDate, int planLevel,
			int readCount, int likeCount) {	
		this.planNo = planNo;
		this.memberId = memberId;
		this.planTitle = planTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.planLevel = planLevel;
		this.readCount = readCount;
		this.likeCount = likeCount;
	}

	public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPlanTitle() {
		return planTitle;
	}

	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPlanLevel() {
		return planLevel;
	}

	public void setPlanLevel(int planLevel) {
		this.planLevel = planLevel;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "Plan [planNo=" + planNo + ", memberId=" + memberId + ", planTitle=" + planTitle + ", startDate="
				+ startDate + ", endDate=" + endDate + ", planLevel=" + planLevel + ", readCount=" + readCount
				+ ", likeCount=" + likeCount + "]";
	}
	
	
}
