package tags;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Tags {

	public static int IN_VALID = -1;
	public static int MAX_MSG_SIZE = 1024000; // ~1MB
	public static String ONLINE_USER_OPEN_TAG = "<USERS>";
	public static String ONLINE_USER_CLOSE_TAG = "</USERS>";
	public static String USER_OPEN_TAG = "<USER>";
	public static String USER_CLOSE_TAG = "</USER>";
	public static String OPTION_OPEN_TAG = "<OPTION>";
	public static String OPTION_CLOSE_TAG = "</OPTION>";
	public static String PEER_PASSWORD_OPEN_TAG = "<PASSWORD>";
	public static String PEER_PASSWORD_CLOSE_TAG = "</PASSWORD>";




	public static String CHAT_MSG_OPEN_TAG = "<CHAT_MSG>";// 20
	public static String CHAT_MSG_CLOSE_TAG = "</CHAT_MSG>";// 21



	public static int show(JFrame frame, String msg, boolean type) {
		if (type)
			return JOptionPane.showConfirmDialog(frame, msg, null, JOptionPane.YES_NO_OPTION);
		JOptionPane.showMessageDialog(frame, msg);
		return IN_VALID;
	}
}

