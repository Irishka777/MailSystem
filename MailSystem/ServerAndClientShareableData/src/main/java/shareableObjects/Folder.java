package shareableObjects;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Folder implements Serializable {
	private String name;
	private Email email;
	private ArrayList<Letter> letters;
	
	public void setName(String folderName) {
		name = folderName;
	}
	public String getName() {
		return name;
	}
	
	public void setEmail(Email folderEmail){
		email = folderEmail;
	}
	public Email getEmail() {
		return email;
	}
	
	public void setLetters(ArrayList<Letter> listOfLetters) {
		letters = listOfLetters;
	}
	public ArrayList<Letter> getLetters() {
		return letters;
	}
}
