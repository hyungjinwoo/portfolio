package place.model.vo;

public class Place {
	private String placeId;
	private String cityCode;
	private String placeCode;
	private String placeName;
	private Double placeX;
	private Double placeY;
	private int placeRating;
	
	public Place() {}

	public Place(String placeId, String cityCode, String placeCode, String placeName, Double placeX, Double placeY,
			int placeRating) {
		this.placeId = placeId;
		this.cityCode = cityCode;
		this.placeCode = placeCode;
		this.placeName = placeName;
		this.placeX = placeX;
		this.placeY = placeY;
		this.placeRating = placeRating;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Double getPlaceX() {
		return placeX;
	}

	public void setPlaceX(Double placeX) {
		this.placeX = placeX;
	}

	public Double getPlaceY() {
		return placeY;
	}

	public void setPlaceY(Double placeY) {
		this.placeY = placeY;
	}

	public int getPlaceRating() {
		return placeRating;
	}

	public void setPlaceRating(int placeRating) {
		this.placeRating = placeRating;
	}

	@Override
	public String toString() {
		return "Place [placeId=" + placeId + ", cityCode=" + cityCode + ", placeCode=" + placeCode + ", placeName="
				+ placeName + ", placeX=" + placeX + ", placeY=" + placeY + ", placeRating=" + placeRating + "]";
	}

	
	
	
	
}
