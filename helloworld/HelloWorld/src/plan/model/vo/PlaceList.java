package plan.model.vo;

public class PlaceList {
	private int placeListNo;
	private int planNo;
	private int day;
	private String placeName;
	private float placeLat;
	private float placeLng;
	private String placeComment;
	private int placeListLevel;
	
	public PlaceList() {}
	
	public PlaceList(int placeListNo, int planNo, int day, String placeName, float placeLat, float placeLng,
			String placeComment, int placeListLevel) {
		super();
		this.placeListNo = placeListNo;
		this.planNo = planNo;
		this.day = day;
		this.placeName = placeName;
		this.placeLat = placeLat;
		this.placeLng = placeLng;
		this.placeComment = placeComment;
		this.placeListLevel = placeListLevel;
	}

	public int getPlaceListNo() {
		return placeListNo;
	}

	public void setPlaceListNo(int placeListNo) {
		this.placeListNo = placeListNo;
	}

	public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
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

	public String getPlaceComment() {
		return placeComment;
	}

	public void setPlaceComment(String placeComment) {
		this.placeComment = placeComment;
	}

	public int getPlaceListLevel() {
		return placeListLevel;
	}

	public void setPlaceListLevel(int placeListLevel) {
		this.placeListLevel = placeListLevel;
	}

	@Override
	public String toString() {
		return "PlaceList [placeListNo=" + placeListNo + ", planNo=" + planNo + ", day=" + day + ", placeName="
				+ placeName + ", placeLat=" + placeLat + ", placeLng=" + placeLng + ", placeComment=" + placeComment
				+ ", placeListLevel=" + placeListLevel + "]";
	}

	
	
}
