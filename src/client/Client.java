package client;

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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import data.Peer;
import tags.Decode;
import tags.Encode;
import tags.Tags;

public class Client {
	private String userName;
	private java.net.Socket socket = null;
	private boolean isStop = false;
	private static int portClient = 10000; 
	private int timeOut = 10000;  //time to each request is 10 seconds.
	private BufferedReader br;
	private BufferedWriter bw ;
	public static void main(String args[]) throws Exception
	{
		
		Client test = new Client("minhtoan123",new Socket());
		test.exit();
	}
	public Client(String userName, java.net.Socket socket) throws IOException
	{
		this.userName = userName;
		this.socket = socket;
		InputStream is=socket.getInputStream();
		br=new BufferedReader(new InputStreamReader(is));
 
		OutputStream os=socket.getOutputStream();
		bw = new BufferedWriter(new OutputStreamWriter(os));

		(new receive()).start();
	}
	public Client(String userName, java.net.Socket socket,int mode) throws IOException// for server
	{
		this.userName = userName;
		this.socket = socket;
		
		InputStream is=socket.getInputStream();
		br=new BufferedReader(new InputStreamReader(is));
	
		OutputStream os=socket.getOutputStream();
		bw = new BufferedWriter(new OutputStreamWriter(os));
		
	}
	public java.net.Socket getSocket()
	{
	       return this.socket;
	}
	public void exit() throws IOException {
		
		System.out.println("Exit Check");
		this.isStop = true;
		String msg = Encode.Quit(this.userName);
		
		System.out.println("Client Send :  "+ msg);
		bw.write(msg);
		bw.newLine();
		bw.flush();
		//socket.close();
		
	}
	public void Request(String nameDes) throws IOException
	{
		String msg = Encode.requestConnect(this.userName, nameDes);
		System.out.println(msg);
		bw.write(msg);
		bw.newLine();
		bw.flush();
	}
	public void SendMess(String nameDes,String data) throws IOException
	{
		String msg =Encode.SendMessage(this.userName, nameDes, data);
		System.out.println(msg);
		bw.write(msg);
		bw.newLine();
		bw.flush();
	}
	public static int getPort() {
		return portClient;
	}


	
	
	
	public class receive extends Thread{
		@Override
		public void run() {
			super.run();
			while (true)
			{
				try {
					RececiveListen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public void RececiveListen() throws IOException, InterruptedException
		{
			String msg= null;
			while (!isStop)
			{
				Thread.sleep(1000);
				System.out.println("Listening");
				msg =br.readLine();
				if(msg!= null)
				{
					
					System.out.println(msg);
					int op= Decode.getOption(msg);
					if(op==4)
					{
						System.out.println("BroadCast Receive!!!");// user
						MainGui.updateFriendMainGui(msg);
					}
					
				}
				
			}
		}
	}

	

//	public void updateFriend(){
//		int size = clientarray.size();
//	//	MainGui.resetList(); **
//		//while loop
//		int i = 0;
//		while (i < size) {
//			if (!clientarray.get(i).getName().equals(nameUser))
//		//		MainGui.updateFriendMainGui(clientarray.get(i).getName()); **
//			i++;
//		}
//	}
}