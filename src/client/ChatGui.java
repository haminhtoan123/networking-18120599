package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ChatGui  {
	private Client client;// this only for send
	private String DesUser;
	private JFrame frame;
	private static String[] emojiDir = {"/image/like.png","/image/smile_big.png","/image/crying.png","/image/heart_eye.png"};
	private JPanel contentPane;
	private JTextField chatMess;
	private JTextPane MessPane;
	private JLabel filePath;
	private String filename;
	private int fileSelected=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui frame = new ChatGui("Ha Minh Toan",null);
					frame.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	ChatGui(String DesUser,Client client){
		this.DesUser = DesUser;
		this.client = client;
		intialize();
		//(new ListenDataChange()).start();
		this.getFrame().setVisible(true);
	}
	public void SetData(String Data1)
	{
		updateReceiveMess(Data1);
		
	}
	/**
	 * Create the frame.
	 */

	public void updateReceiveFile(String filename)
	{
		updateReceiveMess("");
		JButton File = new JButton(filename);
		// Download 
		File.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					client.ReceiveFile(filename);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				filePath.setText("You Received File: " + filename);
			}
		});
		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		try {
			
			Style style = doc.addStyle("StyleName", null);
			StyleConstants.setComponent(style,File);
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void updateReceiveMess(String text)
	{
		JLabel temp = new JLabel(DesUser + " : " +text);
		temp.setForeground(Color.red);
		

		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		Style style = doc.addStyle("StyleName", null);
		StyleConstants.setComponent(style, temp);
		try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public void updateReceiveIcon(int emoji)
	{
		updateReceiveMess("");
		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		try {
			
			Style style = doc.addStyle("StyleName", null);
			StyleConstants.setComponent(style, new JLabel(new javax.swing.ImageIcon(ChatGui.class.getResource(emojiDir[emoji]))));
			doc.insertString(doc.getLength(), "\n", style);
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
		

		StyledDocument doc = (StyledDocument) MessPane.getDocument();
		Style style = doc.addStyle("StyleName", null);
		StyleConstants.setComponent(style, temp);
		try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void updateSentIcon(int emoji)
	{
		updateSentText("");
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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame = new JFrame();
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        frame.setVisible(false);
		    }
		});
		frame.setBounds(100, 100, 713, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
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
				try {
					client.SendIcon(DesUser, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				try {
					client.SendIcon(DesUser, 1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				try {
					client.SendIcon(DesUser, 2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				try {
					client.SendIcon(DesUser, 3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
					try {
						client.SendMess(DesUser, chatMess.getText());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					chatMess.setText("");

				}
				if(fileSelected==1)
				{
					fileSelected =0;
					// client send file
					try {
						client.SendFileRequest(DesUser, filePath.getText(), "");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					// update text
					updateSentText(filename);
					filePath.setText("");
					// send 
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
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					fileSelected = 1;
					String path_send = (fileChooser.getSelectedFile()
							.getAbsolutePath()) ;
					filename = fileChooser.getSelectedFile().getName();
					System.out.println(path_send);
					filePath.setText(path_send);
				}
			}
		});
		btnGetFile.setBounds(381, 333, 79, 45);
		contentPane.add(btnGetFile);
		
		filePath= new JLabel();// file
		
		filePath.setBounds(43, 349, 241, 16);
		contentPane.add(filePath);
		
		JLabel lblDesUser = new JLabel(DesUser);//name des user
		lblDesUser.setBounds(12, 13, 200, 25);
		contentPane.add(lblDesUser);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
