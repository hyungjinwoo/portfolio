package event.model.vo;

public class EventPhoto {
	private int eventNo;
	private String originalFileName;
	private String renamedFileName;
	
	public EventPhoto() {}

	public EventPhoto(int eventNo, String originalFileName, String renamedFileName) {
		this.eventNo = eventNo;
		this.originalFileName = originalFileName;
		this.renamedFileName = renamedFileName;
	}

	public int getEventNo() {
		return eventNo;
	}

	public void setEventNo(int eventNo) {
		this.eventNo = eventNo;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getRenamedFileName() {
		return renamedFileName;
	}

	public void setRenamedFileName(String renamedFileName) {
		this.renamedFileName = renamedFileName;
	}

	@Override
	public String toString() {
		return "EventPhoto [eventNo=" + eventNo + ", originalFileName=" + originalFileName + ", renamedFileName="
				+ renamedFileName + "]";
	}
	
	
}
