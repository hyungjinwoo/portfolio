package plan.model.vo;

public class WishList {
	private String memberId;
	private int placeNo;
	
	public WishList() {}

	public WishList(String memberId, int placeNo) {
		this.memberId = memberId;
		this.placeNo = placeNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(int placeNo) {
		this.placeNo = placeNo;
	}

	@Override
	public String toString() {
		return "WishList [memberId=" + memberId + ", placeNo=" + placeNo + "]";
	}
	
	
}
