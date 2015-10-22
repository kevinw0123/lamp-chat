package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ChatServer {
	private Vector<ChatThread> ctVector;
	
	public ChatServer(int port) {
		ctVector = new Vector<ChatThread>();
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			ServerOptions so = new ServerOptions(this);
			so.start();
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
	
	public void closeRoom() {
		
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
	
	
	private class ServerOptions extends Thread {
		private ChatServer chatServer;
		public ServerOptions(ChatServer chatServer) {
			this.chatServer = chatServer;
		}
		@Override
		public void run() {
			Scanner scan = new Scanner(System.in);
			try {
				boolean cont = true;
				while (cont) {
					System.out.println("Options:");
					System.out.println("1. Close chat room");
					System.out.println("2. Kick user");
					System.out.print("Enter option:");
					String line = scan.nextLine();
					if (line.equals("1")) {
						System.out.println("Closing room!");
						cont = false;
					} else if (line.equals("2")) {
						System.out.print("Enter userID");
						if (line.matches("\\d+")) {
							
						}
					}
				}
			} finally {
				scan.close();
				chatServer.closeRoom();
			}
		}
	}
}


