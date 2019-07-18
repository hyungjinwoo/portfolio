package mainPage.model.vo;

public class Nation {
	private int national_code;
	private String national_name;
	private String wea_name;
	private String crc_code;
	private String c_name;
	
	public Nation() {}
	
	public int getNational_code() {
		return national_code;
	}
	public void setNational_code(int national_code) {
		this.national_code = national_code;
	}
	public String getNational_name() {
		return national_name;
	}
	public void setNational_name(String national_name) {
		this.national_name = national_name;
	}
	public String getWea_name() {
		return wea_name;
	}
	public void setWea_name(String wea_name) {
		this.wea_name = wea_name;
	}
	public String getCrc_code() {
		return crc_code;
	}
	public void setCrc_code(String crc_code) {
		this.crc_code = crc_code;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	@Override
	public String toString() {
		return national_name;
	}
	
	
}
