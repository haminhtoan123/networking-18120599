package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import client.Client;
import data.Peer;
import tags.Decode;
import tags.Encode;
import tags.Tags;



public class ServerCore {
	private UserList users = new UserList();
	private HashMap<String, Client> clients = new HashMap<String,Client>();
	private ServerSocket server;						
	private Socket connection;			
	private ObjectOutputStream obOutputClient;		
	private ObjectInputStream obInputStream;			
	public boolean isStop = false, isExit = false;		
	
	public static void main(String[] args) throws Exception, IOException {
		ServerCore test = new ServerCore(8080);

	}
	public void ClientPrint()
	{
		
		for(String key: clients.keySet())
		{
			System.out.println(key + "  :  " + clients.get(key));
		}
	}
	public void OnlineUserPrint()
	{
		HashMap<String,String>  temp = users.getOnlineUsers();
		for(String key: temp.keySet())
		{
			System.out.println(key + "  :  " + temp.get(key));
		}
	}
	public void UserPrint()
	{
		HashMap<String,String>  temp = users.getUsers();
		for(String key: temp.keySet())
		{
			System.out.println(key + "  :  " + temp.get(key));
		}
	}
	public void BroadCast(String msg)
	{
		for(String i: clients.keySet())
		{
			try {
				System.out.println(i);
				sendToOneClient(i,msg);
			}
			catch (IOException e)
			{
				System.out.println(e.toString());
			}
		}
	}
	public void sendToOneClient (String userName,String message) throws IOException
	{
	    Client c = clients.get(userName);

	    java.net.Socket socket = c.getSocket();

	    // Sending the response back to the client.
	    // Note: Ideally you want all these in a try/catch/finally block
	    OutputStream os = socket.getOutputStream();
	    OutputStreamWriter osw = new OutputStreamWriter(os);
	    BufferedWriter bw = new BufferedWriter(osw);
	    bw.write(message);
	    bw.newLine();
	    bw.flush();
	    System.out.println("Send To One Client");
	}
	//Intial server socket
	public ServerCore(int port) throws Exception {
		server = new ServerSocket(port);		
	//	dataPeer = new ArrayList<Peer>();
		(new ClientHandle()).start();
	
	}
	//	close server
	public void stopserver() throws Exception {
		isStop = true;
		server.close();							
		connection.close();						
	}
	
	
	

	
	
	public class Receive extends Thread {
		private Socket cnc;
		public Receive(Socket cnc){this.cnc = cnc;}
		@Override
		public void run() {
			super.run();
			try {
				while (!isStop) {
					Listen();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void Quit(String msg)
		{
			String name = Decode.getQuitUser(msg);
			// get socket
			Client c = clients.get(name);
			try {
				c.getSocket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// remove from online client
			clients.remove(name);
			BroadCast(Encode.UpdateUser(clients.keySet()));
		}
		public boolean SendMess(String msg)
		{
			String Data[] = Decode.Option2(msg);
			System.out.println(Data[0]+ " : " + Data[1]+ " : " + Data[2]);
			try { 
				sendToOneClient(Data[0],Data[2]);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		public boolean Login(String msg)
		{

			String[] Data = Decode.Option1(msg);
			if(users.CertifyUser(Data[0], Data[1]))
			{
				if(clients.containsKey(Data[0])) return false;
				System.out.println("Login Succcedddddd");
				
				Client tmp = null;
				try {
					tmp = new Client(Data[0],cnc,0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("inet : " + cnc.getInetAddress().toString());
				clients.put(Data[0], tmp);
				BroadCast(Encode.UpdateUser(clients.keySet()));
				return true;
			}
			else
			{
				try {
					 OutputStream os = cnc.getOutputStream();
					 OutputStreamWriter osw = new OutputStreamWriter(os);
					 BufferedWriter bw = new BufferedWriter(osw);
					 String message = Encode.Noti("0");// login fail
					 bw.write(message);
					 bw.newLine();
					 bw.flush();
					 System.out.println("????");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			
		}
		public boolean Register(String msg)
		{
			String[] Data = Decode.Option1(msg);
		
		
			if(users.register(Data[0], Data[1])) 
			{
				try {
					 OutputStream os = cnc.getOutputStream();
					 OutputStreamWriter osw = new OutputStreamWriter(os);
					 BufferedWriter bw = new BufferedWriter(osw);
					 String outm = Encode.Noti("1");
					 bw.write(outm);
					 bw.newLine();
					 bw.flush();

						System.out.println("Succcedddddd");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					users.WriteFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			else
			{

				try {
					 OutputStream os = cnc.getOutputStream();
					 OutputStreamWriter osw = new OutputStreamWriter(os);
					 BufferedWriter bw = new BufferedWriter(osw);
					 String message = Encode.Noti("0");// reg fail
					 bw.write(message);
					 bw.newLine();
					 bw.flush();
					 System.out.println("????eree");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		}
		
		public void Listen() throws IOException//****
		{
			
			System.out.println(cnc.getPort());
			InputStream is=cnc.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String msg;
			while (true) {
				msg=br.readLine();
				if(msg!=null) {
					
					System.out.println("Received : " + msg);
					int op = Decode.getOption(msg);
					if(op == 3)
					{
						Register(msg);
					}
					else
					if(op == 1)
					{
						Login(msg);
						OnlineUserPrint();
						
					}
					else if(op ==2)
					{
						SendMess(msg);
					}
					else if(op == 4)
					{
						//Quit
						Quit(msg);
						
						System.out.println("Client QUit");
						this.stop();
					}
					
			
				}
				

			}
		}
	}
	public class Send extends Thread {
		Socket cnc;
		public Send(Socket cnc) { this.cnc = cnc;}
		@Override 
		public void run() {
			super.run();	
			try {
				while (!isStop) {
					Scanner aa =  new Scanner(System.in);
				
					//	sendToOneClient("minhtoan123",aa.nextLine(),"concac");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public class ClientHandle extends Thread{
		@Override
		public void run() {
			super.run();
			while(true)
			{
			Socket connection1 = null;
			try {
				connection1 = server.accept();
				(new Receive(connection1)).start();			
				//(new Send(connection1)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			}
	}


}
