package team.dx.classroom.domain;

import java.io.Serializable;

public class TrueOrFalse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String answer;
	private String description;

	public TrueOrFalse() {
	}

	public TrueOrFalse(String title, String answer,
			String description) {
		super();
		this.title = title;
		this.answer = answer;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
