

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface iServer extends Remote {
	
	
	
	String getPassword(String userName) throws RemoteException;
	ArrayList<String> getGroupMembers(String group) throws RemoteException;
	HashSet<String> getGroups() throws RemoteException;
	boolean containsUserName(String userName) throws RemoteException;
	boolean checkPassword(String userName,String password) throws RemoteException;
	boolean registerUser(String userName,String password,String ip) throws RemoteException;
	boolean sendRequest(String sender,String recieve) throws RemoteException;
	void addGroupAdmin(String user,String group) throws RemoteException;
	HashMap<Integer,String> returnAdmins() throws RemoteException;
	ArrayList<String> getFriendList(String user ) throws RemoteException;
	void addUserIp(String user,int ip) throws RemoteException;
	int getUserIp(String user) throws RemoteException;
	String getGroupAdmin(String group) throws RemoteException;
	
	void addFriendList(String user, ArrayList<String> friends)
			throws RemoteException;

}
