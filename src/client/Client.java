package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import File.FileInfo;
import server.FileThread;
import tags.Decode;
import tags.Encode;
import tags.Tags;

public class Client {
	private String userName;
	private java.net.Socket socket = null;
	private boolean isStop = false;
	private static int portClient = 10000; 
	private BufferedReader br;
	private BufferedWriter bw ;
	private receive rc;
	public static void main(String args[]) throws Exception
	{
		InetSocketAddress addressServer = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),8080);
		Socket temp = new Socket();
		temp.connect(addressServer);
		Client test = new Client("ay lmao",temp,1);
		test.SendFileRequest("hihi", "C:/Users/Toan/git/networking-18120599/networking-18120599/src/client/1.txt","");

	}
	public Client() {}
	public Client(String userName, java.net.Socket socket) throws IOException
	{
		this.userName = userName;
		this.socket = socket;
		InputStream is=socket.getInputStream();
		br=new BufferedReader(new InputStreamReader(is));
 
		OutputStream os=socket.getOutputStream();
		bw = new BufferedWriter(new OutputStreamWriter(os));
		rc = new receive();
		rc.start();
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
	//RECEIVE FILE
	public void ReceiveFile(String filename) throws IOException, ClassNotFoundException
	{
		//send to sv 
		bw.write(Encode.FileReceive(filename));
		bw.newLine();
		bw.flush();
		String Port =br.readLine();
		System.out.println("Receive Port : " +Port);
		Socket ReceiveLine = new Socket(socket.getLocalAddress(),Integer.parseInt(Port));
		ObjectInputStream  ois = new ObjectInputStream(ReceiveLine.getInputStream());
		FileInfo file = (FileInfo) ois.readObject();
		   if (file != null) {
               FileThread.createFile(file);
           }

	
	}
	
	//SEND FILE
	public void SendFileRequest(String nameDes,String sourceFile,String destinationDir) throws IOException
	{
		//rc.stop();
		FileInfo fileInfo = FileInfo.getFileInfo(sourceFile,destinationDir);
		String msg =Encode.FileSendRequest(this.userName, nameDes, fileInfo.getFilename());
		System.out.println(msg);	
		bw.write(msg);
		bw.newLine();
		bw.flush();
		String Port =br.readLine();
		System.out.println("Receive Port : " +Port);
		SendFile(fileInfo,socket.getLocalAddress(),Integer.parseInt(Port));
//		rc = new receive();
//		rc.start();
	}
	public void SendFile(FileInfo file,InetAddress SvIP,int Port) throws IOException 
	{
		Socket SendLine = new Socket(SvIP,Port);

	    ObjectOutputStream oos = null;
	    ObjectInputStream ois = null;
	    
	    try {
            

         
 
            // send file
            oos = new ObjectOutputStream(SendLine.getOutputStream()); 
            oos.writeObject(file);
 
            // get confirmation
            ois = new ObjectInputStream(SendLine.getInputStream());
            file = (FileInfo) ois.readObject();
            if (file != null) {
                System.out.println("Send complete");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // close all stream
            closeStream(oos);
            closeStream(ois);

        }
		SendLine.close();
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

	public void SendIcon(String nameDes,int Icon) throws IOException
	{
		String msg =Encode.SendIcon(this.userName, nameDes, Integer.toString(Icon));
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
			while (!isStop)
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
			
			
				System.out.println("Listening");
				msg =br.readLine();
				if(msg!= null)
				{
					// fix when server send port
					System.out.println(msg);
					if(!Decode.getOptions.matcher(msg).find()) 
					{
						Thread.sleep(10);
						return;
					}

					int op= Decode.getOption(msg);
					if(op==2) // receive message
					{
						String[] data = Decode.getMessage(msg);
						
						MainGui.updateMessage(data[0], data[1]);
					}
					if(op==4)
					{
						System.out.println("BroadCast Receive!!!");// user
						MainGui.updateFriendMainGui(msg);
					}
					if(op == 5)
					{
						System.out.println("adu ma thang nao send file");
						String[] data = Decode.getSendFileData(msg);
						MainGui.updateSendFile(data[0], data[2]);
						//MainGui.updateFileSent
					}
					if (op == 6)
					{
						System.out.println("Icon update receive");
						String[] data = Decode.getMessage(msg);
						MainGui.updateIcon(data[0], Integer.parseInt(data[1]));
					}
				}
				
			
			
		}
	}

	
	
	public void closeStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void closeStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
}