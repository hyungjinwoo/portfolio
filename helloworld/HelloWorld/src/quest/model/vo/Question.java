package quest.model.vo;

import java.sql.Date;

public class Question {
	private int questionNo;
	private String questionWriter;
	private String questionTitle;
	private String questionCotent;
	private String questionOriginalFileName;
	private String questionRenamedFileName;
	private Date questionDate;
	private int questionLevel;
	private int answerLevel;
	
	public Question() {}

	public Question(int questionNo, String questionWriter, String questionTitle, String questionCotent,
			String questionOriginalFileName, String questionRenamedFileName, Date questionDate, int questionLevel,
			int answerLevel) {
		this.questionNo = questionNo;
		this.questionWriter = questionWriter;
		this.questionTitle = questionTitle;
		this.questionCotent = questionCotent;
		this.questionOriginalFileName = questionOriginalFileName;
		this.questionRenamedFileName = questionRenamedFileName;
		this.questionDate = questionDate;
		this.questionLevel = questionLevel;
		this.answerLevel = answerLevel;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	public String getQuestionWriter() {
		return questionWriter;
	}

	public void setQuestionWriter(String questionWriter) {
		this.questionWriter = questionWriter;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionCotent() {
		return questionCotent;
	}

	public void setQuestionCotent(String questionCotent) {
		this.questionCotent = questionCotent;
	}

	public String getQuestionOriginalFileName() {
		return questionOriginalFileName;
	}

	public void setQuestionOriginalFileName(String questionOriginalFileName) {
		this.questionOriginalFileName = questionOriginalFileName;
	}

	public String getQuestionRenamedFileName() {
		return questionRenamedFileName;
	}

	public void setQuestionRenamedFileName(String questionRenamedFileName) {
		this.questionRenamedFileName = questionRenamedFileName;
	}

	public Date getQuestionDate() {
		return questionDate;
	}

	public void setQuestionDate(Date questionDate) {
		this.questionDate = questionDate;
	}

	public int getQuestionLevel() {
		return questionLevel;
	}

	public void setQuestionLevel(int questionLevel) {
		this.questionLevel = questionLevel;
	}

	public int getAnswerLevel() {
		return answerLevel;
	}

	public void setAnswerLevel(int answerLevel) {
		this.answerLevel = answerLevel;
	}

	@Override
	public String toString() {
		return "Question [questionNo=" + questionNo + ", questionWriter=" + questionWriter + ", questionTitle="
				+ questionTitle + ", questionCotent=" + questionCotent + ", questionOriginalFileName="
				+ questionOriginalFileName + ", questionRenamedFileName=" + questionRenamedFileName + ", questionDate="
				+ questionDate + ", questionLevel=" + questionLevel + ", answerLevel=" + answerLevel + "]";
	}
	
	
	
	
}
