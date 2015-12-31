



import java.net.InetAddress;
import java.net.MalformedURLException;
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
import java.util.Iterator;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.ImmutableDescriptor;

public  class imPeer extends UnicastRemoteObject implements iPeer
{/**
	 * 
	 */
	
	static ArrayList<String> myGroups=new ArrayList<String>();
	static int loginCount=0;
	static String password="";
	static String userName="";
	static String myName="";
	static ArrayList<String> friendlist=new ArrayList<String>();	
	static HashMap<String,Integer>  friendIp=new HashMap<String,Integer>();
	static ArrayList<String> grouplist=new ArrayList<String>();
	static HashMap<String,ArrayList<String>> friendgroup=new HashMap<String, ArrayList<String>>();
	static HashMap<String,ArrayList<String>> groupfriend=new HashMap<String, ArrayList<String>>();
	
	
private static final long serialVersionUID = 1L;
static int id=0;
static int port=43127;
int clientId=0;
String ip;
static int count=0;
static int sharedData=0;

ArrayList<iPeer> listofhigherids=new ArrayList<iPeer>();
private String bsip;
private int co_ordinatorid;
private ArrayList<iPeer> listofPeer=new ArrayList<iPeer>();
protected imPeer() throws RemoteException {
		super();
		count++;
	
	}
public	void setIp(String ip) throws RemoteException
	{
	this.ip=ip;	
	}
	public String getIp() throws RemoteException
	{
		return this.ip;
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, AlreadyBoundException, UnknownHostException
	{
		//registering myself with rmi
		Scanner s=new Scanner(System.in);
     	port=Integer.parseInt(args[0]);
		Registry reg;
		Registry reg1=LocateRegistry.createRegistry(port);
		//i create a registry
		iPeer peer=new imPeer();
		reg1.rebind("userPeer", peer);
		
		//getting server
		iServer imserver = new imServer();
        reg=LocateRegistry.getRegistry("127.0.0.1",1515);
	    imserver=(iServer)reg.lookup("im");
		System.out.println("server lookup done");
		
		
		
		System.out.println("Enter yes if you have an account and no to create an account");
		String account=s.nextLine();
		if(account.equals("no"))
		{
			System.out.println("Enter desired userName");
			String desiredUserName=s.nextLine();
			while(imserver.containsUserName(desiredUserName))
			{
				System.out.println("UserName"+desiredUserName+"Is already taken by someone else,"
						+ "enter another");
				desiredUserName=s.nextLine();
				
				
			}
			myName=desiredUserName;
			imserver.addUserIp(myName, port);
			System.out.println("Enter password");
			String password=s.nextLine();
			String ip=InetAddress.getLocalHost().getHostAddress();
			imserver.registerUser(desiredUserName,password,ip);
			imserver.setFriendList( myName);
			}
		else if(account.equals("yes"))
		{	boolean falg=false;
			while(falg==false)
		{	System.out.println("Whats is your  userName?");
		String userName=s.nextLine();
		if(imserver.containsUserName(userName))
		{
			System.out.println("Enter password");
			String password=s.nextLine();
			if(imserver.checkPassword(userName,password))
			{
				falg=true;
				
				//check case when no friends
				friendlist=new ArrayList(imserver.getFriendList(userName));
			
				myName=userName;
				
				break;
			}
			else
			{
				System.out.println("Wrong password.Try again.");
			}
		}
		else
		{
			System.out.println("UserName does not exist");
		}
			
		
		
		
		
		
		}
			
			friendlist=imserver.getFriendList(userName);
imserver.addUserIp(myName, port);
		
	/*	HashMap admins=	imserver.returnAdmins();
		Iterator i=admins.keySet().iterator();
		while(i.hasNext())
		{
			int adminport=(Integer) i.next();
			Registry regAdmin=LocateRegistry.getRegistry("127.0.0.1",adminport);
			iPeer admin=new imPeer();
			admin=(iPeer)regAdmin.lookup("userPeer");
			
			
		}
		*/	
			
			
			
			

			
		}
		
		
		
		
		
	    
	    
	while(true)
	{System.out.println("Enter 1 for adding someone");
	System.out.println("Enter 2 for sending a message");
	System.out.println("Enter 3 for forming a group");
	System.out.println("Enter 4 logout");
	System.out.println("Enter 5 for knowing all your friends");
    System.out.println("Enter 6 to send group message");
    System.out.println("Enter 7 to Read unread messages");
   
String optionString=s.nextLine();
int option=Integer.parseInt(optionString);
switch(option)
{//searching
case 1:

	System.out.println("enter userName to add to your friendlist");
	String userName=s.nextLine();
	if(!friendlist.contains(userName)&& imserver.containsUserName(userName))
	{
		friendlist.add(userName);
		int friendPort=imserver.getUserIp(userName);
		friendIp.put(userName, friendPort);
		imserver.addFriendList(myName, friendlist);

		ArrayList<String> aFriend=new ArrayList<String>();
		aFriend=imserver.getFriendList(userName);
		
			aFriend.add(myName);
			imserver.addFriendList(userName,aFriend);
			
		
		//server.sendRequest(myName,userName);
		System.out.println("Friend is added");
	}
	else if(!imserver.containsUserName(userName))
	{
		System.out.println("No such user");
	}
	else
	{
		System.out.println("Friend is already added");
	}
	
	break;
	
	




case 2:

    String userFriend;
    String message;
	System.out.println("enter user to send message");
	userFriend=s.nextLine();
	if(!imserver.getFriendList(myName).contains(userFriend))
	{
		System.out.println("User not your friend, cannot send message");
		
	}
	else
	{System.out.println("Enter message");
	message=s.nextLine();
	int ip=imserver.getUserIp(userFriend);

	try
	{Registry friendReg=LocateRegistry.getRegistry("127.0.0.1", ip);
	iPeer friend1=new imPeer();
	friend1=(iPeer) friendReg.lookup("userPeer");
	friend1.printToConsole(myName+": "+message);
	}
	catch(RemoteException e1)
	{
		imserver.addUnreadMessages(userFriend,myName+": "+message);
	}
	
	
	
	
	
	}
	
	break;
	
case 3:
	System.out.println("Enter name of the group");
	String group=s.nextLine();
	if(!grouplist.contains(group))
	{
		grouplist.add(group);
	}
	imserver.addGroupAdmin(myName,group);
	System.out.println("Add members to the group");
	System.out.println("enter Number of members");
	String nString=s.nextLine();
	int n=Integer.parseInt(nString);
    ArrayList<String> members=new ArrayList<String>();
    for(int k=0;k<n;k++)
	{System.out.println("Add memmber no"+(k+1));
    String addedMember=s.nextLine();
	if(imserver.getFriendList(myName).contains(addedMember))
	{groupfriend.put(group, members);
	members.add(addedMember);}
	else
	{
		System.out.println("Can't add a person who is not your friend");
	}
	}
break;
case 4:
	System.exit(0);
	break;
case 5:
	
	Iterator iterSh=imserver.getFriendList(myName).iterator();
	while(iterSh.hasNext())
	{
		System.out.println(iterSh.next());
	}
	break;

case 6:
	System.out.println("enter group name");
	String groupS=s.nextLine();
	String admin=imserver.getGroupAdmin(groupS);
	int adminIp=imserver.getUserIp(admin);

	Registry adminRn=LocateRegistry.getRegistry(adminIp);
	iPeer adminofGroup=new imPeer();
	adminofGroup=(iPeer)adminRn.lookup("userPeer");
	ArrayList<String> groupmemebers=adminofGroup.getGroupMembers(groupS);
	System.out.println("Enter message");
	String message1=s.nextLine();

	if(!myName.equals(admin))
	{adminofGroup.printToConsole(groupS+":"+message1);
	
	}
for(int i=0;i<groupmemebers.size();i++)
{if(!groupmemebers.get(i).equals(myName))
	{
	int FriendIp=imserver.getUserIp(groupmemebers.get(i));
	Registry FriendRn=LocateRegistry.getRegistry(FriendIp);
	iPeer friendofGroup=new imPeer();
	friendofGroup=(iPeer)FriendRn.lookup("userPeer");
	friendofGroup.printToConsole(groupS+":"+message1);
	}

}
	break;
case 7:
	try
	{Iterator<String> i=imserver.getUnreadMessages(myName).iterator();
	System.out.println(imserver.getUnreadMessages(myName).size()+" Unread Messages");
	while(i.hasNext())
	{
		System.out.println(i.next());
	}
	
	imserver.removeUnreadMessages(myName);
	}
	catch(NullPointerException e)
	{
		System.out.println("No unread messages");
	}

	



}




}	
	
	
		
		
		
	}
	
	
public boolean getMs(String s) throws RemoteException
	{
		System.out.println(s);
		return true;
		
	}
@Override
public boolean sendMessage(String user,String message) {
int ip=friendIp.get(user);

	return false;
}

@Override
public boolean printToConsole(String message) {
	System.out.println(message);
	return false;
}
@Override
public boolean sendMessage(String userIp) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void addFriendIp(String userName, int port) throws RemoteException {
	// TODO Auto-generated method stub
	
}
@Override
public ArrayList<String> getGroupMembers(String groupS) throws RemoteException {
ArrayList<String> groupMembers= groupfriend.get(groupS);
return groupMembers;
	
}
@Override
public void addFriend(String friend) throws RemoteException {

	friendlist.add(friend);
}



}
