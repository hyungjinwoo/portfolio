package mainPage.model.vo;

public class City_Img {
private int city_code;
private String city_image;
private String city_name;

public City_Img() {}

public int getCity_code() {
	return city_code;
}
public void setCity_code(int city_code) {
	this.city_code = city_code;
}
public String getCity_image() {
	return city_image;
}
public void setCity_image(String city_image) {
	this.city_image = city_image;
}
public String getCity_name() {
	return city_name;
}
public void setCity_name(String city_name) {
	this.city_name = city_name;
}

@Override
public String toString() {
	return "City_Img [city_code=" + city_code + ", city_image=" + city_image + city_name+"]";
}

}
