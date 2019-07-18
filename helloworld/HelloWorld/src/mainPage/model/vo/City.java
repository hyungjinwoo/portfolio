package mainPage.model.vo;

public class City {
	private int city_code;
	private int national_code;
	private String city_name;
	
	public int getCity_code() {
		return city_code;
	}
	public void setCity_code(int city_code) {
		this.city_code = city_code;
	}
	public int getNational_code() {
		return national_code;
	}
	public void setNational_code(int national_code) {
		this.national_code = national_code;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	@Override
	public String toString() {
		return "City [city_code=" + city_code + ", national_code=" + national_code + ", city_name=" + city_name + "]";
	}
	
	
	
	
}
