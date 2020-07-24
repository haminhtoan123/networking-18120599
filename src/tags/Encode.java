package tags;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encode {


	public static void main(String args[])
	{

	}
	
	// for Server
	// login thanh cong 1  ko thanh cong 0
	// dang ki thanh cong 2 ko thanh cong 0
	public static String Noti(String status)// send back
	{
		return Tags.OPTION_OPEN_TAG + status + Tags.OPTION_CLOSE_TAG;
	}
	public static String UpdateUser(Set<String> keyset)// update User -> 4
	{
		return Noti("4")+OnlineUser(keyset);
	}
	public static String OnlineUser( Set<String> keyset)
	{
		String rt="";
		for(String key:keyset)
		{
			rt = rt +Tags.USER_OPEN_TAG +key+Tags.USER_CLOSE_TAG;
		}
		rt = Tags.ONLINE_USER_OPEN_TAG +rt +Tags.ONLINE_USER_CLOSE_TAG;
		return rt;
	
	}
	// for client
	public static String FileReceive(String filename)
	{
		return Tags.OPTION_OPEN_TAG + "7" +Tags.OPTION_CLOSE_TAG +Tags.CHAT_MSG_OPEN_TAG +filename +Tags.CHAT_MSG_CLOSE_TAG;
	}
	public static String SendIcon(String name,String nameDes, String icon) // icon -> number
	{
		return Tags.OPTION_OPEN_TAG + "6" +Tags.OPTION_CLOSE_TAG+ Tags.USER_OPEN_TAG + name + Tags.USER_CLOSE_TAG
				+ Tags.USER_OPEN_TAG + nameDes + Tags.USER_CLOSE_TAG+Tags.CHAT_MSG_OPEN_TAG +icon +Tags.CHAT_MSG_CLOSE_TAG;
	}
	public static String FileSendRequest(String name,String nameDes,String filename)
	{
		return Tags.OPTION_OPEN_TAG + "5" +Tags.OPTION_CLOSE_TAG+ Tags.USER_OPEN_TAG + name + Tags.USER_CLOSE_TAG
				+ Tags.USER_OPEN_TAG + nameDes + Tags.USER_CLOSE_TAG+Tags.CHAT_MSG_OPEN_TAG +filename +Tags.CHAT_MSG_CLOSE_TAG;
	}
	public static String Quit(String name)
	{
		return Tags.OPTION_OPEN_TAG + "4" +Tags.OPTION_CLOSE_TAG + Tags.USER_OPEN_TAG + name + Tags.USER_CLOSE_TAG;
	}
	
	public static String CreateAccount(String name,String password) {// 3 
		return Tags.OPTION_OPEN_TAG + "3" +Tags.OPTION_CLOSE_TAG + Tags.USER_OPEN_TAG + name + Tags.USER_CLOSE_TAG + Tags.PEER_PASSWORD_OPEN_TAG 
				+password +Tags.PEER_PASSWORD_CLOSE_TAG;
	}

	public static String SendMessage(String name,String nameDes, String msg)
	{
		return Tags.OPTION_OPEN_TAG + "2" +Tags.OPTION_CLOSE_TAG+ Tags.USER_OPEN_TAG + name + Tags.USER_CLOSE_TAG
				+ Tags.USER_OPEN_TAG + nameDes + Tags.USER_CLOSE_TAG+Tags.CHAT_MSG_OPEN_TAG +msg +Tags.CHAT_MSG_CLOSE_TAG;
	}

	
	public static String sendRequest(String name,String password) {
		return Tags.OPTION_OPEN_TAG + "1" +Tags.OPTION_CLOSE_TAG + Tags.USER_OPEN_TAG + name + Tags.USER_CLOSE_TAG + Tags.PEER_PASSWORD_OPEN_TAG 
				+password +Tags.PEER_PASSWORD_CLOSE_TAG;
	}






}

