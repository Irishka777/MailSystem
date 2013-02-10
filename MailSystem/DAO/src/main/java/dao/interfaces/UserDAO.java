package dao.interfaces;

import shareableObjects.User;

public interface UserDAO {
	public boolean createUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(User user);
}
