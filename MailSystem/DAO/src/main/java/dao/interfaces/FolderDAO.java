package dao.interfaces;

import java.util.TreeSet;

import shareableObjects.Email;
import shareableObjects.Folder;
import shareableObjects.Letter;

public interface FolderDAO {
	public boolean createFolder(Folder folder);
	public boolean updateFolder(Folder folder);
	public boolean deleteFolder(Folder folder);
	public boolean addLetterIntoFolder(Folder folder, Letter letter);
	public TreeSet<Folder> getAllFoldersForEmail(Email email);
}
