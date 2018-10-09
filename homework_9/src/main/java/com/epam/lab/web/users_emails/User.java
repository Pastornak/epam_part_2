package main.java.com.epam.lab.web.users_emails;

public class User {

	private String username;
	private String login;
	private String password;
	
	public User(String username, String login, String password) {
		super();
		this.username = username;
		this.login = login;
		this.password = password;
	}

	public String getUsername(){ return username; }

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public String toString(){
		return username + ": " + login + ", " + password;
	}
}
