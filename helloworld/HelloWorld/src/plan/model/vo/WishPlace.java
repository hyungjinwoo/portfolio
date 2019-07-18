package plan.model.vo;

public class WishPlace {
	private String memberId;
	private int placeNo;
	private String placeName;
	private float placeLat;
	private float placeLng;
	
	public WishPlace() {}

	public WishPlace(String memberId, int placeNo, String placeName, float placeLat, float placeLng) {
		super();
		this.memberId = memberId;
		this.placeNo = placeNo;
		this.placeName = placeName;
		this.placeLat = placeLat;
		this.placeLng = placeLng;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public float getPlaceLat() {
		return placeLat;
	}

	public void setPlaceLat(float placeLat) {
		this.placeLat = placeLat;
	}

	public float getPlaceLng() {
		return placeLng;
	}

	public void setPlaceLng(float placeLng) {
		this.placeLng = placeLng;
	}

	@Override
	public String toString() {
		return "WishPlace [memberId=" + memberId + ", placeNo=" + placeNo + ", placeName=" + placeName + ", placeLat="
				+ placeLat + ", placeLng=" + placeLng + "]";
	}

	

	
}
