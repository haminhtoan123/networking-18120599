package tags;


import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decode {
	public static void main(String args[])
	{
		String temp[] =getSendFileData("<OPTION>5</OPTION><USER>ay lmao</USER><USER>hihi</USER><CHAT_MSG>C:/Users/Toan/git/networking-18120599/networking-18120599/src/client/1.txt</CHAT_MSG>\r\n");
		System.out.println(temp[0]);
	}
	private static Pattern getMessage = Pattern
			.compile(Tags.CHAT_MSG_OPEN_TAG + "(?<Mess>.*?)"+ Tags.CHAT_MSG_CLOSE_TAG);
	private static Pattern getUsers = Pattern
			.compile(Tags.USER_OPEN_TAG + "(?<User>.*?)"+ Tags.USER_CLOSE_TAG);
			
	public static Pattern getOptions = Pattern
			.compile(Tags.OPTION_OPEN_TAG + ".*"+Tags.OPTION_CLOSE_TAG);



	//for client
	public static String[] getMessage(String msg)
	{
		String[] rt = new String[2];// 0 = from User
		Matcher findDesUser = getUsers.matcher(msg);
		findDesUser.find();
		rt[0] = findDesUser.group("User");
		Matcher findMessage = getMessage.matcher(msg);
		findMessage.find();
		rt[1] = findMessage.group("Mess");
		return rt;
	}
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
	public static String getFileName(String msg)
	{
		Matcher find= getMessage.matcher(msg);
		find.find();
		return find.group("Mess");
	}
	public static String[] getSendFileData(String msg)// [0] = send user [1] = des user [2] = filename
	{		
		System.out.println(msg);
		Matcher find = getUsers.matcher(msg);
		find.find();

		String rt[]= new String[3];
		rt[0] = find.group("User");
		find.find();
		rt[1] =find.group("User");
		find = getMessage.matcher(msg);
		find.find();
		rt[2] =	find.group("Mess");
		return rt;
	}
	public static String getQuitUser(String msg)
	{
		Matcher find = getUsers.matcher(msg);
		find.find();
		return find.group("User");
	}
	public static String[] Option2(String msg) // send message
	{
		String[] rt =new String[2];
		Matcher findDes =	getUsers.matcher(msg);
		findDes.find();
		findDes.find();
		rt[0] = findDes.group("User");
		Matcher findM = getMessage.matcher(msg);
		findM.find();
		rt[1] = findM.group(0);
		return rt;
	}
	public static String[] Option1(String msg) // Login & register
	{
		String[] rt =new String[2];
		Pattern findName = Pattern.compile(Tags.USER_OPEN_TAG + "(?<User>.*?)"
				+ Tags.USER_CLOSE_TAG);
		Pattern findPass =  Pattern.compile(Tags.PEER_PASSWORD_OPEN_TAG + "(?<Pass>.*?)"
				+ Tags.PEER_PASSWORD_CLOSE_TAG);
		Matcher find = findName.matcher(msg);
		find.find();
		String Name = find.group("User");
		rt[0] = Name;
		find = findPass.matcher(msg);
		find.find();
		String Pass = find.group("Pass");
		rt[1] = Pass;
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

