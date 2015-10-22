package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatThread extends Thread {
	private BufferedReader br;
	private PrintWriter pw;
	private ChatServer cs;
	private Socket socket;
	
	public ChatThread(Socket s, ChatServer cs) {
		this.socket = s;
		this.cs = cs;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
		} catch (IOException ioe) {
			System.out.println("Not able to start connection: " + ioe.getMessage());
		}
	}
	@Override
	public void run() {
		try {
			while (true) {
				String line = br.readLine();
				//check if line is valid
				if (line != null) { cs.sendMessageToAllClients(line, this); }
				else break;
			}
		} catch(IOException ioe) {
			System.out.println("Connection dropped");
		} finally {
			cs.removeChatThread(this);
		}
	}
	public void sendMessage(String message) {
		if (pw != null) {
			pw.println(message);
			pw.flush();
		}
	}
	public Socket getSocket() {
		return this.socket;
	}
}
