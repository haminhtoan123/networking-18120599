package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class UserList {
	private HashMap<String,String> User = new HashMap<String,String>();
	private HashMap<String,String> UserOnline = new HashMap<String,String>();
	public static void main(String args[])
	{
		UserList a = new UserList();
		try {
			a.getUserFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.register("hahaa1132", "123");
		for(String i: a.User.keySet())
		{
			System.out.println(i);
		}
		
		try {
			a.WriteFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	UserList(){
		try {
			getUserFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean register(String ID,String Password)
	{
		if(User.get(ID)!=null)
		return false;
		
		User.put(ID, Password);
		
		return true;
	}
	public void LogOut(String ID)
	{
		UserOnline.remove(ID);
	}
	public boolean CertifyUser(String ID, String Password)
	{
		String pw;
	
		pw =User.get(ID);
		if(pw==null)  return false;
		if(!pw.equals(Password)) return false;
			
		UserOnline.put(ID,Password);
		return true;
		

	}
	public void getUserFromFile() throws IOException
	{
		BufferedReader br ;
		try 
		{
			 br = new BufferedReader(new FileReader("src/User.txt"));
		} 
		catch(FileNotFoundException exc) 
		{
			System.out.println("File Not Found");
			return;
		}

		int UserCount=0;
		try {
			UserCount = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(UserCount);
		for(int i=0;i<UserCount;i++)
		{
			String ID;
			String Password;
			ID = (String) br.readLine();
			Password = (String) br.readLine();
			User.put(ID, Password);
		}
	}
	public void WriteFile() throws IOException
	{

		FileOutputStream fout;
		
		fout = new FileOutputStream("src/User.txt");
	
		try 
		{
			
			String out= Integer.toString(User.size())+"\n";
			for(String i : User.keySet())
			{
				out+= i +"\n" + User.get(i) + "\n";
			}
			fout.write(out.getBytes());
			
		} 
		catch(IOException exc) 
		{
			System.out.println("Error occur when write to file");
		}
		fout.flush();
		fout.close();
	}
	public HashMap<String,String> getUsers()
	{
		return User;
	}
	public HashMap<String,String> getOnlineUsers()
	{
		return UserOnline;
	}
	
}
