

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface iPeer extends Remote
{
	boolean sendMessage(String userIp) throws RemoteException;
	boolean printToConsole(String message) throws RemoteException;
	boolean sendMessage(String user, String message) throws RemoteException;
	void addFriendIp(String userName,int port) throws RemoteException;
	ArrayList<String> getGroupMembers(String groupS) throws RemoteException;
}
