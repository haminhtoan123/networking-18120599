package client;

import java.awt.EventQueue;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import tags.Encode;
import tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.awt.Font;
import javax.swing.UIManager;

public class Login {


 private Socket con;

 private JFrame frameLoginForm;
 private JTextField txtPort;
 private JLabel lblError;
 private JLabel lblStatus;
 private String name = "", IP = "", pass ="";
 private JTextField txtIP;	
 private JTextField txtUsername;
 private JPasswordField txtPassword;
 private JButton btnLogin;
 private JButton btnRegis;
 private JButton btnConnect;

 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     Login window = new Login();
     window.frameLoginForm.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 public Login() {
  initialize();
 }

 private void initialize() {
  frameLoginForm = new JFrame();
  frameLoginForm.setTitle("Login Form");
  frameLoginForm.setResizable(false);
  frameLoginForm.setBounds(100, 100, 517, 343);
  frameLoginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frameLoginForm.getContentPane().setLayout(null);

  JLabel lblWelcome = new JLabel("Connect With Server\r\n");
  lblWelcome.setForeground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
  lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
  lblWelcome.setBounds(27, 13, 312, 48);
  frameLoginForm.getContentPane().add(lblWelcome);

  JLabel lblHostServer = new JLabel("IP Server");
  lblHostServer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblHostServer.setBounds(47, 74, 86, 20);
  frameLoginForm.getContentPane().add(lblHostServer);

  JLabel lblPortServer = new JLabel("Port Server");
  lblPortServer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblPortServer.setBounds(349, 77, 79, 14);
  frameLoginForm.getContentPane().add(lblPortServer);

  txtPort = new JTextField();
  txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtPort.setText("8080");
  txtPort.setEditable(false);
  txtPort.setColumns(10);
  txtPort.setBounds(429, 70, 65, 28);
  frameLoginForm.getContentPane().add(txtPort);

  JLabel lblUserName = new JLabel("User Name");
  lblUserName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblUserName.setBounds(10, 134, 106, 38);
  frameLoginForm.getContentPane().add(lblUserName);
  lblUserName.setIcon(new javax.swing.ImageIcon(Login.class.getResource("/image/user.png")));
  
  JLabel lblPassword= new JLabel("Password");
  lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblPassword.setBounds(10, 170, 106, 38);
  frameLoginForm.getContentPane().add(lblPassword);
    
  lblError = new JLabel();
  lblError.setBounds(66, 287, 399, 20);
  frameLoginForm.getContentPane().add(lblError);
  
  lblStatus = new JLabel("");
  lblStatus.setBounds(349, 100, 399, 20);
  frameLoginForm.getContentPane().add(lblStatus);
 
  txtIP = new JTextField();
  txtIP.setBounds(128, 70, 185, 28);
  frameLoginForm.getContentPane().add(txtIP);
  txtIP.setColumns(10);

  txtPassword = new JPasswordField();
  

  
  txtUsername = new JTextField();
  txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtUsername.setColumns(10);
  txtUsername.setBounds(128, 138, 366, 30);
  frameLoginForm.getContentPane().add(txtUsername);

  txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtPassword.setColumns(10);
  txtPassword.setBounds(128, 170, 366, 30);
  frameLoginForm.getContentPane().add(txtPassword);
  
  btnConnect = new JButton("Connect");
  btnConnect.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  btnConnect.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent arg0) {
		  IP = txtIP.getText();
		  SocketAddress addressServer;
		  con = new Socket();
		  try {
				addressServer = new InetSocketAddress(InetAddress.getByName(IP),8080);
				try {
					con.connect(addressServer);
					lblStatus.setText("Connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					lblStatus.setText("Kết nối không thành công");
					e.printStackTrace();
				}
		  } catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				lblStatus.setText("IP khong hop le");
				e.printStackTrace();
		  }
		
		  
	  }
  });
  btnConnect.setBounds(128, 100, 100, 30);
  frameLoginForm.getContentPane().add(btnConnect);
  
  btnRegis = new JButton("Register");
  btnRegis.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  btnRegis.setBounds(50, 217, 169, 63);
  btnRegis.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent arg0) {
		  name = txtUsername.getText();
		  pass = txtPassword.getText();
		  String msg = Encode.CreateAccount(name, pass);
		  System.out.println(msg);
			InputStream is;
			try {
				is = con.getInputStream();
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				 
				OutputStream os=con.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
				bw.write(msg);
				bw.newLine();
				bw.flush();
				String msg1 = br.readLine();
				System.out.println(msg1);
				if(msg1.equals(Tags.OPTION_OPEN_TAG + "1"+Tags.OPTION_CLOSE_TAG)  )
				{
					lblError.setText("Succeed");
				}
				else {
					lblError.setText("Name Exist");
				};
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	  }
  });
  frameLoginForm.getContentPane().add(btnRegis);
  
  
  btnLogin = new JButton("Login");
  btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  btnLogin.setIcon(new javax.swing.ImageIcon(Login.class.getResource("/image/login.png")));
  btnLogin.addActionListener(new ActionListener() {

   public void actionPerformed(ActionEvent arg0) {
	  name = txtUsername.getText();
	  pass = txtPassword.getText();
	  String msg = Encode.sendRequest(name, pass);
		System.out.println(msg);
		InputStream is;
		try {
			is = con.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			 
			OutputStream os=con.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(msg);
			bw.newLine();
			bw.flush();
			String msg1 = br.readLine();
			System.out.println( "Client Receive:" + msg1);
			if(msg1.equals(Tags.OPTION_OPEN_TAG + "0"+Tags.OPTION_CLOSE_TAG)  )
			{
				System.out.println("login fail");
			}
			else { 
				// new frame
			
				try {
					
					MainGui chat = new MainGui(con,name,msg1);
					frameLoginForm.dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

   }
  });
  
  btnLogin.setBounds(325, 217, 169, 63);
  frameLoginForm.getContentPane().add(btnLogin);



 }
}