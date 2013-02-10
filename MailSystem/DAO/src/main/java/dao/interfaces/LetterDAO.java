package dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import shareableObjects.Letter;
import shareableObjects.User;

public interface LetterDAO {
	public boolean deleteLetter(Letter letter) throws SQLException;
	public boolean createLetter(Letter letter) throws SQLException;
	public boolean sendLetter(Letter letter);
	public ArrayList<Letter> getUserIncomingLetters(User user);
	public ArrayList<Letter> getUserOutcomingLetters(User user);
}
