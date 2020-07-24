package server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import File.FileInfo;

public class FileThread extends Thread {
	Socket ReceiveSock ;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	}
	public FileThread(Socket sv) {this.ReceiveSock = sv;}
	@Override 
	public void run()
	{
		super.run();
		System.out.println("server Listen");
        // Socket Receive = null;
         DataInputStream inFromClient = null;
         ObjectInputStream ois = null;
         ObjectOutputStream oos = null;
     	
         try {
         

             // get greeting from client
//             inFromClient = new DataInputStream( ReceiveSock.getInputStream());
//             System.out.println(inFromClient.readUTF());

             // receive file info
             ois = new ObjectInputStream( ReceiveSock.getInputStream());
             FileInfo fileInfo = (FileInfo) ois.readObject();
             if (fileInfo != null) {
                 createFile(fileInfo);
             }

             // confirm that file is received
             oos = new ObjectOutputStream( ReceiveSock.getOutputStream());
             fileInfo.setStatus("success");
             fileInfo.setDataBytes(null);
             oos.writeObject(fileInfo);
               
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } finally {
             // close all stream
             closeStream(ois);
             closeStream(oos);
             closeStream(inFromClient);
             // close session
             closeSocket(ReceiveSock);
         }
		super.stop();
	}
	 public static boolean createFile(FileInfo fileInfo)
	 {
		 
	        BufferedOutputStream bos = null;
	         
	        try {
	            if (fileInfo != null) {
	                File fileReceive = new File(fileInfo.getDestinationDirectory() 
	                        + fileInfo.getFilename());
	                bos = new BufferedOutputStream(
	                        new FileOutputStream(fileReceive));
	                // write file content
	                bos.write(fileInfo.getDataBytes());
	                bos.flush();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            closeStream(bos);
	        }
	        return true;
	    }
	     
	public static void closeSocket(Socket socket)
	{
	        try {
	            if (socket != null) {
	                socket.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}	 
	public static void closeStream(InputStream inputStream) 
	{
	       try {
	           if (inputStream != null) {
	               inputStream.close();
	           }
	       } catch (IOException ex) {
	           ex.printStackTrace();
	       }
	}
	 
	public static void closeStream(OutputStream outputStream)
	{
	       try {
	    	   
	           if (outputStream != null) {
	               outputStream.close();
	           }
	       } catch (IOException ex) {
	           ex.printStackTrace();
	       }
	}    
	
}
