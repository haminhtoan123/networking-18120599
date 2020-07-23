package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ChatGui extends JFrame  {
	private Socket socket;// this only for send
	private String Data;
	private String DesUser;
	
	private static String[] emojiDir = {"/image/like.png","/image/smile_big.png","/image/crying.png","/image/heart_eye.png"};
	private JPanel contentPane;
	private JTextField chatMess;
	private JTextPane MessPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui frame = new ChatGui("Ha Minh Toan","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	ChatGui(String DesUser,String Data){
		this.DesUser = DesUser;
		this.Data = Data;
		
		intialize();
		//(new ListenDataChange()).start();
		this.setVisible(true);
	}
	public void SetData(String Data1)
	{
		updateReceiveMess(Data1);
		
	}
	/**
	 * Create the frame.
	 */
	//RECEIVE
	public class ListenDataChange extends Thread {
		@Override
		public void run()
		{
			super.run();
			while(true)
			{
				System.out.println("aduma sai gon");
				if(!Data.equals(""))
				{
					updateReceiveMess(Data);
					Data="";
				}
			}
		}
	}
	
	public void updateReceiveMess(String text)
	{

		JLabel temp = new JLabel(DesUser + " : " +text);
		temp.setForeground(Color.red);
		MessPane.insertComponent(temp);
		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		try {
			doc.insertString(doc.getLength(), "\n", null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	// send
	// TO DO : SEND FILE -> create send socket -> server
	
	//
	public void updateSentText(String text)
	{

		JLabel temp = new JLabel("You : " +text);
		temp.setForeground(Color.red);
		MessPane.insertComponent(temp);
		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		try {
			doc.insertString(doc.getLength(), "\n", null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void updateSentIcon(int emoji)
	{
		JLabel temp = new JLabel("you: ");
		temp.setForeground(Color.red);
		MessPane.insertComponent(temp);
		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		try {
			
			Style style = doc.addStyle("StyleName", null);
			StyleConstants.setComponent(style, new JLabel(new javax.swing.ImageIcon(ChatGui.class.getResource(emojiDir[emoji]))));
			doc.insertString(doc.getLength(), "invisible text", style);
			doc.insertString(doc.getLength(), "\n", null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void intialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		 MessPane = new JTextPane();
		MessPane.setBounds(6, 59, 470, 191);
		//MessPane.setBounds(0, 0, 6, 22);
		contentPane.add(MessPane);
		
		JScrollPane scrollPane = new JScrollPane(MessPane);
		scrollPane.setBounds(6, 59, 449, 191);
		contentPane.add(scrollPane);
		
		
		JButton btnLike = new JButton();// emoji 0
		btnLike.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource(emojiDir[0])));
		btnLike.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLike.setContentAreaFilled(false);
		btnLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSentIcon(0);
			}
		});
		btnLike.setBounds(514, 29, 97, 35);
		contentPane.add(btnLike);
		
		JButton btnHaha = new JButton(); // emoji 1
		btnHaha.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource(emojiDir[1])));
		btnHaha.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHaha.setContentAreaFilled(false);
		btnHaha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSentIcon(1);
			}
		});
		btnHaha.setBounds(514, 67, 97, 35);
		contentPane.add(btnHaha);
		
		JButton btnCry = new JButton(); // emoji 2
		btnCry.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource(emojiDir[2])));
		btnCry.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnCry.setContentAreaFilled(false);
		btnCry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSentIcon(2);
			}
		});
		btnCry.setBounds(514, 105, 97, 35);
		contentPane.add(btnCry);
		
		JButton btnHeart = new JButton(); /// emoji 3
		btnHeart.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource(emojiDir[3])));
		btnHeart.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHeart.setContentAreaFilled(false);
		btnHeart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSentIcon(3);
			}
		});
		btnHeart.setBounds(514, 145, 97, 35);
		contentPane.add(btnHeart);
		
		JButton btnSend = new JButton();// send
		btnSend.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/send.png")));
		btnSend.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSend.setContentAreaFilled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!chatMess.getText().equals("")) {
					updateSentText(chatMess.getText());
					chatMess.setText("");
				}
				
			}
		});
		btnSend.setBounds(381, 276, 79, 45);
		contentPane.add(btnSend);
		
		chatMess = new JTextField();
		chatMess.setBounds(12, 276, 357, 65);
		contentPane.add(chatMess);
		chatMess.setColumns(10);
		
		JButton btnGetFile = new JButton();// get file 
		btnGetFile.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/attachment.png")));
		btnGetFile.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnGetFile.setContentAreaFilled(false);
		btnGetFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGetFile.setBounds(381, 333, 79, 45);
		contentPane.add(btnGetFile);
		
		JLabel lblNewLabel = new JLabel("New label");// file
		
		lblNewLabel.setBounds(43, 349, 241, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblDesUser = new JLabel(DesUser);//name des user
		lblDesUser.setBounds(12, 13, 200, 25);
		contentPane.add(lblDesUser);
	}
}
