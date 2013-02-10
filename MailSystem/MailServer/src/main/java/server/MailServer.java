package server;

import java.io.IOException;
import java.io.ObjectInputStream;

import shareableObjects.Letter;

public class MailServer {
	
	public Letter sendLetter(ObjectInputStream input) {
		try {
			Letter letter = (Letter) input.readObject();
			
			System.out.println(letter);
			return letter;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteLetter(ObjectInputStream input) {
		try {
			Letter letter = (Letter) input.readObject();
			System.out.println(letter);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}