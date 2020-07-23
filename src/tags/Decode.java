package tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.Peer;

public class Decode {
	public static void main(String args[])
	{
		System.out.println(getQuitUser("<OPTION>4</OPTION><USER>minhtoan123</USER>"));
	}
	private static Pattern getUsers = Pattern
			.compile(Tags.USER_OPEN_TAG + "(?<User>.*?)"+ Tags.USER_CLOSE_TAG);
			
	private static Pattern getOptions = Pattern
			.compile(Tags.OPTION_OPEN_TAG + ".*"+Tags.OPTION_CLOSE_TAG);



	//for client
	public static List<String> OnlineUser(String msg)
	{
		List<String> rt = new Vector<>();
		Matcher find = getUsers.matcher(msg);
		while(find.find())
		{
			//System.out.println(temp);
			rt.add(find.group("User"));
		}
		return rt;
	}
	
	//for server
	
	// quit
	public static String getQuitUser(String msg)
	{
		Matcher find = getUsers.matcher(msg);
		find.find();
		return find.group("User");
	}
	public static String[] Option2(String msg) // send message
	{
		String[] rt =new String[3];
		Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + ".*"
				+ Tags.PEER_NAME_CLOSE_TAG);
		Pattern findIP =  Pattern.compile(Tags.IP_OPEN_TAG + ".*" + Tags.IP_CLOSE_TAG);
		Pattern findMess = Pattern.compile(Tags.CHAT_MSG_OPEN_TAG + ".*"+ Tags.CHAT_MSG_CLOSE_TAG);
		Matcher find = findName.matcher(msg);
		find.find();
		rt[0] = find.group(0);
		find = findIP.matcher(msg);
		find.find();
		rt[1] = find.group(0);
		find = findMess.matcher(msg);
		rt[2] = find.group(0);
		return rt;
	}
	public static String[] Option1(String msg) // Login & register
	{
		String[] rt =new String[2];
		Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + ".*"
				+ Tags.PEER_NAME_CLOSE_TAG);
		Pattern findPass =  Pattern.compile(Tags.PEER_PASSWORD_OPEN_TAG + ".*"
				+ Tags.PEER_PASSWORD_CLOSE_TAG);
		Matcher find = findName.matcher(msg);
		find.find();
		String Name = find.group(0);
		rt[0] = Name.substring(11, Name.length()-12);
		find = findPass.matcher(msg);
		find.find();
		String Pass = find.group(0);
		rt[1] = Pass.substring(10, Pass.length()-11);
		return rt;
	}
	public static int getOption(String msg)
	{
		Matcher op = getOptions.matcher(msg);
		op.find();
		String temp =op.group(0);
		return Integer.parseInt(temp.substring(8, temp.length() - 9));
	}





}

