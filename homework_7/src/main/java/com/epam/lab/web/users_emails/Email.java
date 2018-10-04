package main.java.com.epam.lab.web.users_emails;

public class Email {

	private String to;
	private String subject;
	private String text;
	
	public Email(String to, String subject, String text) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}
	
	public String toString(){
		return to + ", " + subject + ", " + text;
	}
}
