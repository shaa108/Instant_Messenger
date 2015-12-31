

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class  imServer   extends UnicastRemoteObject implements iServer{
	protected imServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	static int port=2225;
	private static final long serialVersionUID = 1L;
	static HashMap<String,String> userPassword=new HashMap<String,String>();
	static HashSet<String> listofuserNames=new HashSet<String>();
	static HashSet<String> listofGroups=new HashSet<String>();
	static HashMap<String,String> AdminGroup=new HashMap<String,String>();
	static HashMap<String,Integer>  userIp=new HashMap<String, Integer>();
	static HashMap<String,ArrayList<String>> friendList=new HashMap<String, ArrayList<String>>();
public static void main(String[] args) throws RemoteException, UnknownHostException, AlreadyBoundException, NotBoundException
{
	port=Integer.parseInt(args[0]);
	Registry reg = LocateRegistry.createRegistry(port);
	//i create a registry
	
	iServer server = new imServer();
	iPeer peer=new imPeer();

	
	reg.rebind("im",server);
	System.out.println("in glados");
	
	
	

}
@Override
public String getPassword(String userName) {
	
	return userPassword.get(userName);
}
@Override
public ArrayList<String> getGroupMembers(String group) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public HashSet<String> getGroups() {
	// TODO Auto-generated method stub
	return listofGroups;
}
@Override
public boolean containsUserName(String username) {
	if(!listofuserNames.contains(username))
	return false;
	else
		return true;
}
@Override
public boolean checkPassword(String userName, String password) {
if(userPassword.get(userName).equals(password))
{
	return true;
}
return false;
}
public boolean registerUser(String desireduserName,String password,String ip)
{
	userPassword.put(desireduserName, password);
	listofuserNames.add(desireduserName);
	return true;
}
@Override
public boolean sendRequest(String sender, String recieve) {

	return false;
}
@Override
public void addGroupAdmin(String user,String group)
{
	AdminGroup.put(group,user);
}
@Override
public HashMap<Integer,String> returnAdmins()
{
	return null;
	//return AdminGroup;
}
@Override
public ArrayList<String> getFriendList(String user) throws RemoteException {
	return friendList.get(user);
}

@Override
public void addUserIp(String user, int ip) throws RemoteException 
{
	
userIp.put(user, ip);
	
}

@Override
public int getUserIp(String user) throws RemoteException {
	int ip=userIp.get(user);
	return ip;
}
@Override
public String getGroupAdmin(String group) throws RemoteException {
	// TODO Auto-generated method stub
	return AdminGroup.get(group);
}
@Override
public void addFriendList(String user,ArrayList<String> friends) throws RemoteException {

}




}
