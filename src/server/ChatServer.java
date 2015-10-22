package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
	private Vector<ChatThread> ctVector;
	
	public ChatServer(int port) {
		ctVector = new Vector<ChatThread>();
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			while (true) {
				System.out.println("Listening for connections...");
				Socket s = ss.accept();
				ChatThread ct = new ChatThread(s, this);
				ctVector.add(ct);
				ct.sendMessage(Integer.toString(ctVector.size()));
				sendMessageToAllClients("User " + ctVector.size() + " connected from " + s.getInetAddress(), ct);
				ct.start();
			}
		} catch (IOException ioe) {
			System.out.println("Server is offline: " + ioe.getMessage());
		} finally {
			if (ss != null)  {
				try { ss.close(); }
				catch (IOException e) { System.out.println("Could not close server socket"); }
			}
		}
	}
	
	public void removeChatThread(ChatThread ct) {
		System.out.println("Client disconnected: " + ct.getSocket().getInetAddress());
		ctVector.remove(ct);
	}
	
	public void sendMessageToAllClients(String message, ChatThread sender) {
		System.out.println(message);
		for (ChatThread c : ctVector) {
			if (c != sender) { c.sendMessage(message); }
		}
	}
}
