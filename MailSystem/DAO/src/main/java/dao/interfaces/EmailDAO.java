package dao.interfaces;

import shareableObjects.Email;

public interface EmailDAO {
	public boolean createEmail(Email email);
	public boolean updateEmail(Email email);
	public boolean deleteEmail(Email email);
	public Email findByEmailAddress();
}