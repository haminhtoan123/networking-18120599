package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import tags.Decode;
import tags.Tags;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.awt.Color;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class MainGui {

	private static Client client;
	private static String  nameUser = "", dataUser = "";
	private static HashMap<String,ChatGui> chatRoom = new HashMap<String, ChatGui>();// key = DesUsername
	//private ChatGui test ;// Map Of  Gui
	private JFrame frameMainGui;
	private JButton btnExit;
	private JLabel lblLogo;
	private JLabel lblActiveNow;
	private static JList<String> listActive;
	
	static DefaultListModel<String> model = new DefaultListModel<>();
	private JLabel lblUsername;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui(null,"hihi",null);
				//	window.frameMainGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGui(Socket con, String name, String msg) throws Exception {
		
		nameUser = name;
		dataUser = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
					MainGui window = new MainGui(name,con);
					// some listen things heare
					window.frameMainGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MainGui(String name,Socket con) throws Exception {
		initialize();
		client =new Client(name,con);	
	}
	
	
	public static void updateSendFile(String nameDes,String filename)
	{
		if(!chatRoom.containsKey(nameDes))
		{
			ChatGui temp = new ChatGui(nameDes,client);
			temp.updateReceiveFile(filename);
			chatRoom.put(nameDes, temp);
			
		}
		else
		{
			chatRoom.get(nameDes).updateReceiveFile(filename);
		}
	}
	public static void updateIcon(String nameDes,int Icon)
	{
		if(!chatRoom.containsKey(nameDes))
		{
			ChatGui temp = new ChatGui(nameDes,client);
			temp.updateReceiveIcon(Icon);
			chatRoom.put(nameDes, temp);
			
		}
		else
		{
			chatRoom.get(nameDes).updateReceiveIcon(Icon);
		}
	}
	public static void updateMessage(String nameDes,String msg)
	{
		if(!chatRoom.containsKey(nameDes))
		{
			ChatGui temp = new ChatGui(nameDes,client);
			temp.SetData(msg);
			chatRoom.put(nameDes, temp);
			
		}
		else
		{
			chatRoom.get(nameDes).SetData(msg);
		}
	}
	
	public static void getUser(String msg)
	{
		List<String> User = Decode.OnlineUser(msg);
		for(String user:User)
		{
			model.addElement(user);
		}
	}
	
	public static void updateFriendMainGui(String msg) {
		resetList();
		getUser(msg);
	}

	public static void resetList() {
		model.clear();
	} 
	
	private void initialize() {
		frameMainGui = new JFrame();
		frameMainGui.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		       // frame.setVisible(false);
		    	int result = Tags.show(frameMainGui, "Are you sure ?", true);
				if (result == 0) {
					try {
						System.out.println("GUI QUIT");
						
						client.exit();
						frameMainGui.dispose();
					} catch (Exception e) {
						e.printStackTrace();
						frameMainGui.dispose();
					}
				}
		    }
		});
		frameMainGui.setTitle("Menu Chat");
		frameMainGui.setResizable(false);
		frameMainGui.setBounds(100, 100, 500, 560);
		frameMainGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMainGui.getContentPane().setLayout(null);

		JLabel lblHello = new JLabel("Welcome");
		lblHello.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblHello.setBounds(12, 60, 90, 30);
		frameMainGui.getContentPane().add(lblHello);



		
		
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Tags.show(frameMainGui, "Are you sure ?", true);
				if (result == 0) {
					try {
						System.out.println("GUI QUIT");
						
						client.exit();
						frameMainGui.dispose();
					} catch (Exception e) {
						e.printStackTrace();
						frameMainGui.dispose();
					}
				}
			}
		});
		btnExit.setBounds(353, 465, 129, 44);
		btnExit.setIcon(new javax.swing.ImageIcon(MainGui.class.getResource("/image/stop.png")));
		frameMainGui.getContentPane().add(btnExit);
		
		lblLogo = new JLabel("CHAT APP");
		lblLogo.setForeground(new Color(0, 0, 205));
	
		lblLogo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLogo.setBounds(51, 13, 413, 38);
		frameMainGui.getContentPane().add(lblLogo);
		
		lblActiveNow = new JLabel("ONLINE USERS");
		lblActiveNow.setForeground(new Color(100, 149, 237));
		lblActiveNow.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblActiveNow.setBounds(10, 110, 156, 30);
		frameMainGui.getContentPane().add(lblActiveNow);
		
		listActive = new JList<>(model);
		listActive.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		getUser(dataUser);
		listActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String value = (String)listActive.getModel().getElementAt(listActive.locationToIndex(arg0.getPoint()));
				String nameDes = value;
				if (nameDes.equals("")){
					Tags.show(frameMainGui, "Invaild username", false);
					return;
				}
				if (nameDes.equals(nameUser)) { 
					Tags.show(frameMainGui, "This software doesn't support chat yourself function", false);
					return;
				}
				if(chatRoom.containsKey(nameDes))
				{
					if(chatRoom.get(nameDes).getFrame().isVisible())
					Tags.show(frameMainGui, "U are already chat with this guy", false);
					else
					{
						chatRoom.get(nameDes).getFrame().setVisible(true);
					}
					return;
				}
				
				chatRoom.put(nameDes, new ChatGui(nameDes,client));
				return;
			}
		});
		listActive.setBounds(12, 152, 472, 251);
		frameMainGui.getContentPane().add(listActive);
		
		lblUsername = new JLabel(nameUser);
		lblUsername.setForeground(Color.RED);
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblUsername.setBounds(75, 76, 156, 28);
		frameMainGui.getContentPane().add(lblUsername);
	
			
	}
		

	public static int request(String msg, boolean type) {
		JFrame frameMessage = new JFrame();
		return Tags.show(frameMessage, msg, type);
	}
	
}
