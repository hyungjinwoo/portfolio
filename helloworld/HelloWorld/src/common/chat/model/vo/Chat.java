package common.chat.model.vo;

import java.util.Date;

public class Chat {
	private String sender;
	private String reciver;
	private String content;
	
	public Chat() {};

	public Chat(String sender, String reciver, String content) {
		this.sender = sender;
		this.reciver = reciver;
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Chat [sender=" + sender + ", reciver=" + reciver + ", content=" + content + "]";
	};
	
}
