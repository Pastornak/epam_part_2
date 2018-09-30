package com.epam.lab.homework_6.users_emails;

import java.util.Deque;
import java.util.List;
import java.util.LinkedList;

import com.epam.lab.homework_6.readers.XMLParserDOM;
import com.epam.lab.homework_6.utils.Pair;

public class UserEmailPairs {

	private Deque<Pair<User, Email>> pairs;
	
	public UserEmailPairs(){
		pairs = new LinkedList<>();
		initializeValues();
	}
	
	private void initializeValues(){
		XMLParserDOM xml = new XMLParserDOM("resources/user.xml");
		List<String> logins = xml.getAttributes("login");
		List<String> passwords = xml.getAttributes("password");
		xml = new XMLParserDOM("resources/email.xml");
		List<String> tos = xml.getAttributes("to");
		List<String> subjects = xml.getAttributes("subject");
		List<String> texts = xml.getAttributes("text");
		int size = logins.size() <= tos.size()? logins.size() : tos.size();
		for(int i = 0; i < size; i++){
			User user = new User(logins.get(i), passwords.get(i));
			Email email = new Email(tos.get(i), subjects.get(i), texts.get(i));
			pairs.addLast(new Pair<User, Email>(user, email));
		}
	}
	
	synchronized public Pair<User, Email> getPair(){
		if(getSize() <= 0){
			initializeValues();
		}
		Pair<User, Email> result = pairs.poll();
		return result;
	}
	
	public int getSize(){
		return pairs.size();
	}
}
